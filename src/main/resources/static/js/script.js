document.getElementById("subret").addEventListener('click', () => {
    document.getElementById('myform').submit(); 
    return false;
})

document.getElementById('forgot').addEventListener('click', () => {
    document.getElementById('myform').style.display='none';
    document.getElementById('forgotPassword').style.display='block';
    return false;
})

document.getElementById('requestPassword').addEventListener('click', () => {
	alert();
	var login = document.getElementById('loginPass').value;
    var token = document.head.querySelector("meta[name='_csrf']").content;
    var header = document.head.querySelector("meta[name='_csrf_header']").content;
    
    var xhr = new XMLHttpRequest();
    xhr.open('POST', '/ajax/forgotPassword', true);
    xhr.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
    xhr.setRequestHeader(header, token);

    xhr.onreadystatechange = function() {
    	if (xhr.readyState != 4) 
    		return;
    	if (xhr.status != 200) {
    		alert(xhr.status + ': ' + xhr.statusText);
    	} else {
    		document.getElementById('myform').style.display='block';
    	    document.getElementById('forgotPassword').style.display='none';
    	}
    }
    var param = 'login='+login;
    xhr.send(param); 
	
    return false;
})

