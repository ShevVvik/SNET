
function openInfo() {
	var block = document.getElementById('blockInfo');
	var but = document.getElementById('openInfo');
	if (block.style.display === 'none') {
		block.style.display = 'block';
		but.textContent = 'Hide info';
	} else {
		block.style.display = 'none';
		but.textContent = 'More info';
	}
}

document.getElementById('openInfo').addEventListener('click', openInfo);

function ajaxEmployerListUpdate(elem) {
    var pattern = document.getElementById(elem).innerHTML;
    var token = document.head.querySelector("meta[name='_csrf']").content;
    var header = document.head.querySelector("meta[name='_csrf_header']").content;
    var xhr = new XMLHttpRequest();
    xhr.open('POST', '/ajax/addFriend', true);
    xhr.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
    xhr.setRequestHeader(header, token);
    
    xhr.onreadystatechange = function() {
    	if (xhr.readyState != 4) 
    		return;
    	if (xhr.status != 200) {
    		alert(xhr.status + ': ' + xhr.statusText);
    	} else {
    		alert("Success!");
    	}
    }
    xhr.send("q=" + pattern);    
}

document.getElementById('test').addEventListener('click', newsSearch);
$(document).ready(function() {
	 
    $("#submitNewNews").click(function(event) {
        event.preventDefault();
        ajaxSubmitForm();
 
    });
 
});


function ajaxSubmitForm() {
    var formData = {
            idAuthor : $("#idAuthor").val(),
            newNewsText :  $("#textNews").val()
          }
    var token = document.head.querySelector("meta[name='_csrf']").content;
    var header = document.head.querySelector("meta[name='_csrf_header']").content;
    $("#submitNewNews").prop("disabled", true);
 
    $.ajax({
    	headers: { 
    		"X-CSRF-TOKEN": token
        },
        type: 'POST',
        url: '/news/add',
        data : JSON.stringify(formData),
        dataType : 'json',
        contentType: "application/json",
        timeout: 1000000,
        success: function() {
            $("#submitNewNews").prop("disabled", false);
            $('#newNews')[0].reset();
            newsSearch();
        },
        error: function() {  
            $("#submitNewNews").prop("disabled", false);
 
        }
    });
 
}

function newsSearch() {
    var pattern = document.getElementById('userFilter').value;
    var id = document.getElementById('id').innerHTML;
    var token = document.head.querySelector("meta[name='_csrf']").content;
    var header = document.head.querySelector("meta[name='_csrf_header']").content;
    
    var xhr = new XMLHttpRequest();
    xhr.open('POST', '/news/filter', true);
    xhr.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
    xhr.setRequestHeader(header, token);

    xhr.onreadystatechange = function() {
    	if (xhr.readyState != 4) 
    		return;
    	if (xhr.status != 200) {
    		alert(xhr.status + ': ' + xhr.statusText);
    	} else {
    		cleanNews();
    		if (xhr.responseText != '') fillTable( JSON.parse(xhr.responseText) );
    	}
    }
    var param = 'q='+pattern+'&id='+id;
    xhr.send(param);    
}

function cleanNews(){
	var myNode = document.getElementById('newsList');
	while (myNode.children[0]) {
	    myNode.removeChild(myNode.children[0]);
	}
	
}

function getNewsBlock(){
	$.ajax({
		   url: '/utils/getNewsBlock',
		   type:'GET',
		   success: function(data){
		       $('#newsList').html(data);
		   }
		});
}

function fillTable(data) {
	getNewsBlock(); alert("fix me");
	var mainDIV0 = document.querySelector('.newsnews').parentNode;
	cleanNews();
    data.forEach(function(elem) {
    		var mainDIV = mainDIV0.children[0].cloneNode(true);
    		mainDIV.id = elem.id;
    		var redLine = mainDIV.children[0];
    		var nameLog = redLine.children[0];
    		nameLog.children[0].textContent = elem.author.firstName + elem.author.lastName;
    		nameLog.children[1].textContent = '@' +  elem.author.login;
    		console.log(nameLog.children[0]);
    		nameLog.children[1].textContent = elem.login;
    		var text = mainDIV.children[1].textContent = elem.text;
    		var bot = mainDIV.children[2].children[0].children[0];
    		bot.children[0].textContent = elem.date;
    		if (!elem.forFriends) {
    			bot.children[1].remove();
    		}
    		var ull = document.getElementById('newsList');
    		var newLi = document.createElement('li');
    		var ulComment = document.createElement('ul');
    		var comment = mainDIV0.children[1];
    		elem.comments.forEach(function(elemCom) {
    			var divCom = comment.children[0].children[0].cloneNode(true);
    			var topCom = divCom.children[0];
    			var botCom = divCom.children[1];
    			
    			topCom.children[0].textContent = elemCom.text;
    			botCom.children[1].children[0].textContent = 'olololo';
    			
    			var newLiCom = document.createElement('li');
    			newLiCom.appendChild(divCom);
    			ulComment.appendChild(newLiCom);
    		})
    		
    		newLi.appendChild(mainDIV);
    		ull.appendChild(newLi);
    		ull.appendChild(ulComment);
            
    });

}

function sendNews(){
	var id = document.getElementById('id').innerHTML;
	var text = document.getElementById('newsText').value;
    var token = document.head.querySelector("meta[name='_csrf']").content;
    var header = document.head.querySelector("meta[name='_csrf_header']").content;
    var xhr = new XMLHttpRequest();
    xhr.open('POST', '/news/add', true);
    xhr.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
    xhr.setRequestHeader(header, token);
    
    xhr.onreadystatechange = function() {
    	if (xhr.readyState != 4) 
    		return;
    	if (xhr.status != 200) {
    		alert(xhr.status + ': ' + xhr.statusText);
    	} else {
    		document.getElementById('submitPost').removeEventListener('click', sendNews);
    		document.getElementById('formPost').remove();
    		document.getElementById('newPost').addEventListener('click', openMenu);
    	}
    }
    xhr.send('newsText=' + text + '&id=' + id); 
}

//document.getElementById('newPost').addEventListener('click', openMenu);

function editNews(){
	
}



$('.deleteNews').on('click', function() {
	alert();
    var id = $(this).parent().parent().parent().attr('id');
    console.log(id);
    var token = document.head.querySelector("meta[name='_csrf']").content;
    var header = document.head.querySelector("meta[name='_csrf_header']").content;
    var xhr = new XMLHttpRequest();
    xhr.open('POST', '/ajax/deleteNews', true);
    xhr.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
    xhr.setRequestHeader(header, token);
    
    xhr.onreadystatechange = function() {
    	if (xhr.readyState != 4) 
    		return;
    	if (xhr.status != 200) {
    		alert(xhr.status + ': ' + xhr.statusText);
    	} else {
    		alert("Success!");
    		
    		newsSearch(true);
    	}
    }
    xhr.send("id=" + id); 
});
