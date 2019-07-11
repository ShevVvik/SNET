var stompClient = null;
var username = null;
var channelUuid = null;
var subscription = null;

function connect() {
    username = document.querySelector('#username').innerText.trim();
      
    var socket = new SockJS('/ws');
    stompClient = Stomp.over(socket);
 
    stompClient.connect({}, onConnected, onError);
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
    alert(payload);
}

function sendMessage(event) {
	event.preventDefault();
    if(stompClient) {
        var chatMessage = {
    		fromUserId: 1,
            toUserId: 31,
            contents: document.querySelector('#message').value
        }
    }
    stompClient.send("/app/message." + channelUuid, {}, JSON.stringify(chatMessage));
} 

function establishChannel (channelDetailsPayload) {
    channelUuid = channelDetailsPayload.channelUuid;
    /*
    self.userOneFullName = channelDetailsPayload.data.userOneFullName;
    self.userTwoFullName = channelDetailsPayload.data.userTwoFullName;*/
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
			data.forEach(function(message) {
	            alert(message.contents);
	          });
		},
		dataType: "JSON"
	});
};

messageForm.addEventListener('submit', sendMessage, true);
document.getElementById('Connector').addEventListener('click', construct);
document.getElementById('DisConnector').addEventListener('click', disconnector);

function disconnector() {
	subscriptions.unsubscribe('/topic/message.' + channelUuid);
}

function construct() {
	alert();
	establishChatSession(1, 31)
}

function onError(error) {
	alert(error);
}