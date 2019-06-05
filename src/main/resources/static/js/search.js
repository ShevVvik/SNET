document.getElementById('butSearch').addEventListener('click', userSearch);

function userSearch() {
    var pattern = document.getElementById('userFilter').value;
    var parametr = document.querySelector('input[name="parametr"]:checked').value;
    var token = document.head.querySelector("meta[name='_csrf']").content;
    var header = document.head.querySelector("meta[name='_csrf_header']").content;
    
    var xhr = new XMLHttpRequest();
    xhr.open('POST', '/searchTest', true);
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
    var param = 'q='+pattern+'&parametr='+parametr;
    xhr.send(param);    
}

function fillTable(data) {
	var myNode = document.getElementById('userList');
	while (myNode.children[0]) {
	    myNode.removeChild(myNode.children[0]);
	}
	
    data.forEach(function(elem) {
            var newUl = document.createElement('ul');
            newUl.id = elem.id;

            var newLi = document.createElement('li');
            var p = document.createElement('p');
            p.textContent = elem.email;

            newLi.appendChild(p);
            
            newUl.appendChild(newLi);
            document.getElementById('userList').appendChild(newUl);
    });

}