$(document).ready(function() {
	
	var butFriend = true;
    $('.butFriendReq').click(function() {
    	if (butFriend) {
    		getRequestFriends();
    		butFriend = false;
    	} else {
    		butFriend = true;
    		getTrueFriends();
    	}
    });
    
    $('#friendList').on('click', '.delete', function(event) {
    	var id = $(this).parent().parent().attr('id');
    	var element = $(this).parent().parent().parent();
        var token = document.head.querySelector("meta[name='_csrf']").content;
        var header = document.head.querySelector("meta[name='_csrf_header']").content;
        
        var xhr = new XMLHttpRequest();
        xhr.open('POST', '/friendList/delete', true);
        xhr.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
        xhr.setRequestHeader(header, token);

        xhr.onreadystatechange = function() {
        	if (xhr.readyState != 4) 
        		return;
        	if (xhr.status != 200) {
        		alert(xhr.status + ': ' + xhr.statusText);
        	} else {
        		console.log($(element).parent().parent());
        		$(element).remove();
        	}
        }
        var param = 'id='+id;
        xhr.send(param); 
    });
    
    $('#friendList').on('click', '.submit', function(event) {
    	var tokenFriend = $(this).parent().parent().find('.friendToken').text();
    	var element = $(this).parent().parent().parent();
        var token = document.head.querySelector("meta[name='_csrf']").content;
        var header = document.head.querySelector("meta[name='_csrf_header']").content;
        
        var xhr = new XMLHttpRequest();
        xhr.open('POST', '/friendList/addFriend', true);
        xhr.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
        xhr.setRequestHeader(header, token);

        xhr.onreadystatechange = function() {
        	if (xhr.readyState != 4) 
        		return;
        	if (xhr.status != 200) {
        		alert(xhr.status + ': ' + xhr.statusText);
        	} else {
        		console.log($(element).parent().parent());
        		$(element).remove();
        	}
        }
        var param = 'token='+tokenFriend;
        xhr.send(param); 
    });
});



function getTrueFriends() {
	var id = document.getElementById('idUser').textContent;
    var token = document.head.querySelector("meta[name='_csrf']").content;
    var header = document.head.querySelector("meta[name='_csrf_header']").content;
    
    var xhr = new XMLHttpRequest();
    xhr.open('POST', '/friendList/active', true);
    xhr.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
    xhr.setRequestHeader(header, token);

    xhr.onreadystatechange = function() {
    	if (xhr.readyState != 4) 
    		return;
    	if (xhr.status != 200) {
    		alert(xhr.status + ': ' + xhr.statusText);
    	} else {
    		if (xhr.responseText != '') fillTable( JSON.parse(xhr.responseText), true );
    	}
    }
    var param = 'id='+id;
    xhr.send(param);    
}

function getRequestFriends() {
	var id = document.getElementById('idUser').innerHTML;
    var token = document.head.querySelector("meta[name='_csrf']").content;
    var header = document.head.querySelector("meta[name='_csrf_header']").content;
    
    var xhr = new XMLHttpRequest();
    xhr.open('POST', '/friendList/request', true);
    xhr.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
    xhr.setRequestHeader(header, token);

    xhr.onreadystatechange = function() {
    	if (xhr.readyState != 4) 
    		return;
    	if (xhr.status != 200) {
    		alert(xhr.status + ': ' + xhr.statusText);
    	} else {
    		if (xhr.responseText != '') fillTable( JSON.parse(xhr.responseText), false );
    	}
    }
    var param = 'id='+id;
    xhr.send(param);    
}

function cleanFriends(){
	var myNode = document.getElementById('friendList');
	while (myNode.children[0]) {
	    myNode.removeChild(myNode.children[0]);
	}
}

function fillTable(data, check) {
	var mainDIV0 = document.querySelector('#pressetFriend');
	cleanFriends();
    data.forEach(function(elem) {
    	var mainDIV = mainDIV0.children[0].cloneNode(true);
    	var nameBlock = mainDIV.children[0].children[0];
    	mainDIV.id = elem.friend.id;
    	mainDIV.children[0].href = '/u/' + elem.friend.id;
    	nameBlock.children[0].textContent = elem.token;
    	nameBlock.children[1].children[0].textContent = elem.friend.firstName;
    	nameBlock.children[2].children[0].textContent = elem.friend.lastName;
    	mainDIV.children[0].children[1].children[0].textContent = '@' + elem.friend.login;
    	if (check) {
    		mainDIV.children[1].children[0].remove();
    	} else {
    		mainDIV.children[1].children[0].style.display = 'block';
    	}
    	var newLi = document.createElement('li');
    	newLi.appendChild(mainDIV)
    	document.getElementById('friendList').appendChild(newLi);
    });
}
