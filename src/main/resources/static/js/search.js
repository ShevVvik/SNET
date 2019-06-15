document.getElementById('butSearch').addEventListener('click', userSearch);

function userSearch() {
    var pattern = document.getElementById('userFilter').value;
    var parametr = document.querySelector('input[name="option1"]:checked').value;
    var token = document.head.querySelector("meta[name='_csrf']").content;
    var header = document.head.querySelector("meta[name='_csrf_header']").content;
    
    var xhr = new XMLHttpRequest();
    xhr.open('POST', '/search', true);
    xhr.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
    xhr.setRequestHeader(header, token);

    xhr.onreadystatechange = function() {
    	if (xhr.readyState != 4) 
    		return;
    	if (xhr.status != 200) {
    		alert(xhr.status + ': ' + xhr.statusText);
    	} else {
    		if(xhr.responseText != '') fillTable( JSON.parse(xhr.responseText) );
    	}
    }
    var param = 'q='+pattern+'&parametr='+parametr;
    xhr.send(param);    
}

function fillTable(data) {
	var myNode = document.getElementById('resultList');
	while (myNode.children[0]) {
	    myNode.removeChild(myNode.children[0]);
	}
	var mainDIV0 = document.querySelector('#pressetMatch');
    data.forEach(function(elem) {
    		var mainDIV = mainDIV0.children[0].cloneNode(true);
    		$(mainDIV).find('#firstNamePerson0').text(elem.firstName);
    		$(mainDIV).find('#lastNamePerson0').text(elem.lastName);
    		$(mainDIV).find('#loginPerson0').text('@' + elem.login);
            var newLi = document.createElement('li');
            newLi.appendChild(mainDIV);
            document.getElementById('resultList').appendChild(newLi);
    });

}