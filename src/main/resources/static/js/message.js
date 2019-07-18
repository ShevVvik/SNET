var stompClient = null;
var username = null;
var channelUuid = null;
var subscription = null;
var titleProfile = '<div class="titleProfile"><div id="fullName"></div></div>'
var inputMessage = '<div class="newMessage">New message...</div><div id="newMessage" class="createMassage" >'    
                         + '<button class="picker">Pick An Emoji</button>'
                         + '<div name="newNewsText" id="textNews" class="newNewsTextArea" placeholder="Type something..." contenteditable></div>'
                         + '</div></div>'
var myMessage = '<div>'
				+ '<img class="avatarLast"></div>'
				+ '<div><p class="messageName">Шевяков Дмитрий</p><div class="message"></div></div>'
var myProfile;
var friendProfile;
var userOneId;
var userTwoId;

function connect() {
    var socket = new SockJS('/ws');
    stompClient = Stomp.over(socket);
 
    stompClient.connect({}, onConnected, onError);
    //stompClient.debug = null
}
 
function disconnect() {
	stompClient.disconnect();
	stompClient = null;
};

// Connect to WebSocket Server.
connect();

function onConnected() {
    // Subscribe to the Public Topic
}

function onMessageReceived(payload) {
	var data = JSON.parse(payload.body)
	var element = document.createElement('div');
	element.innerHTML = myMessage;
	if (userTwo.id == data.fromUserId) {
		$(element).addClass('friendMessage');
		$(element).find('.messageName').text(userTwo.lastName + ' ' + userTwo.firstName);
		$(element).find('.avatarLast').attr('src', '/avatar/big/' + userTwo.login);
	} else {
		$(element).addClass('myMessage');
		$(element).find('.messageName').text(userOne.lastName + ' ' + userOne.firstName);
		$(element).find('.avatarLast').attr('src', '/avatar/big/' + userOne.login);
	}
	$(element).find('.message').text(data.contents);
	$('.areaMessage').append(element);
	$(window).scrollTop(9999);
}

function sendMessage() {
    if(stompClient) {
        var chatMessage = {
    		fromUserId: userOne.id,
            toUserId: userTwo.id,
            contents: correctString(document.querySelector('#textNews').innerHTML)
        }
        document.querySelector('#textNews').innerHTML = '';
    }
    stompClient.send("/app/message." + channelUuid, {}, JSON.stringify(chatMessage));
} 

function establishChannel (channelDetailsPayload) {
    channelUuid = channelDetailsPayload.channelUuid;
    console.log(channelDetailsPayload);
    userOne = channelDetailsPayload.userOne;
    userTwo = channelDetailsPayload.userTwo;
    subscriptions = stompClient.subscribe('/topic/message.' + channelUuid, onMessageReceived);
    getExistingChatMessages(channelUuid);
};

function establishChatSession(userIdOne, userIdTwo) {
	var token = document.head.querySelector("meta[name='_csrf']").content;
    var header = document.head.querySelector("meta[name='_csrf_header']").content; 
    $.ajax({
    	headers: { 
    		"X-CSRF-TOKEN": token,
            'Content-Type': 'application/json'
        },
    	type: "PUT",
    	url: '/message',
    	data: JSON.stringify({
            userIdOne: userIdOne,
            userIdTwo: userIdTwo
        }),
    	success: establishChannel,
    	dataType: "JSON"
    });
    
};

function getExistingChatMessages() {
	getExistingChatSessionMessages(channelUuid);
};

function getExistingChatSessionMessages(channelUuid) {
	var token = document.head.querySelector("meta[name='_csrf']").content;
    var header = document.head.querySelector("meta[name='_csrf_header']").content; 
	$.ajax({
		headers: { 
    		"X-CSRF-TOKEN": token,
            'Content-Type': 'application/json'
        },
		url: '/message/' + channelUuid,
		success: function(data) {
			var areaMes = document.createElement('div'); 
			$(areaMes).addClass('areaMessage');
			data.forEach(function(message) {
				var element = document.createElement('div');
				element.innerHTML = myMessage;
				if (userTwo.id == message.fromUserId) {
					$(element).addClass('friendMessage');
					$(element).find('.messageName').text(userTwo.lastName + ' ' + userTwo.firstName);
					$(element).find('img').attr('src', '/avatar/big/' + userTwo.login)
				} else {
					$(element).addClass('myMessage');
					$(element).find('.messageName').text(userOne.lastName + ' ' + userOne.firstName);
					$(element).find('.avatarLast').attr('src', '/avatar/big/' + userOne.login);
				}
				$(element).find('.message').text(message.contents);
				$(areaMes).append(element);
	          });
			var element = document.createElement('div');
			element.innerHTML = inputMessage;
			$('.container').append(areaMes);
			$('.container').append(element);
			$(window).scrollTop(9999);
			$('.picker').lsxEmojiPicker({
				  twemoji: false,
				  onSelect: function(emoji){
					  document.getElementById('textNews').innerHTML += emoji.value;
				  }
			});
		},
		dataType: "JSON"
	});
};

$('.container').on('click', '#crt', sendMessage);

//document.getElementById('Connector').addEventListener('click', construct);
//document.getElementById('DisConnector').addEventListener('click', disconnector);

$('a').click(function(event) {
	var id = $(this).attr('id');
	userOneId = document.querySelector('p').id;
	userTwoId = id;
	changeModule();
	establishChatSession(userOneId, userTwoId)
});

$('.container').on('keyup', "#textNews", function(event){
    if(event.keyCode == 13){
        event.preventDefault();
        sendMessage();
    }
});

function changeModule() {
	$('#dialogList').remove();
}

function disconnector() {
	subscriptions.unsubscribe('/topic/message.' + channelUuid);
}

function onError(error) {
	alert(error);
}

function correctString(str) {
	str.replace('<div>', '');
	str.replace('</div>', '');
	str.replace('<br>', '');
	return str;
}