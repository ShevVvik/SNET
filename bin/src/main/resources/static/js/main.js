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
    		fillTable( JSON.parse(xhr.responseText) );
    	}
    }
    var param = 'q='+pattern+'&id='+id;
    xhr.send(param);    
}

function fillTable(data) {
	var myNode = document.getElementById('newsList');
	while (myNode.children[0]) {
	    myNode.removeChild(myNode.children[0]);
	}
	
    data.forEach(function(elem) {
            var newUl = document.createElement('ul');
            newUl.id = elem.id;

            var newLi = document.createElement('li');
            var p = document.createElement('p');
            p.innerHTML = elem.text;
            
            var comDiv = document.createElement('div');
            var comUl = document.createElement('ul');
            elem.comments.forEach(function(com) {
            	var li = document.createElement('li');
            	li.innerHTML = com.text;
            	comUl.appendChild(li);
            });
            comDiv.appendChild(comUl);
            newLi.appendChild(p);
            newLi.appendChild(comDiv);
            
            newUl.appendChild(newLi);
            console.log(newUl);
            document.getElementById('newsList').appendChild(newUl);
    });

}

function openMenu(){
	var div = document.getElementById('newsList');
	var formPost = document.createElement('div');
	formPost.id = 'formPost';
	var textAreaPost = document.createElement('textarea');
	textAreaPost.id = 'newsText';
	var submit = document.createElement('a');
	submit.id = 'submitPost';
	submit.innerHTML = 'Send';
	
	formPost.appendChild(textAreaPost);
	formPost.appendChild(submit);
	
	div.appendChild(formPost);
	
	submit.addEventListener('click', sendNews);
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
    	}
    }
    xhr.send('newsText=' + text + '&id=' + id); 
}

document.getElementById('newPost').addEventListener('click', openMenu);

function editNews(){
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
    	}
    }
    xhr.send('newsText=' + text + '&idNews=' + idNews + '&id=' + id); 
}
