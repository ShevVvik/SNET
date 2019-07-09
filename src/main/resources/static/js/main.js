$(document).ready(function($) {
    $('#iw-modal-btn').click(function() {
        $('.iw-modal').css("opacity","1");
        $('.iw-modal').css("pointer-events","auto");
        return false;
    });    
    
    $('#cls').click(function() {
        $('.iw-modal').css("opacity","0");
        $('.iw-modal').css("pointer-events","none");
        return false;
    });        

    $(document).keydown(function(e) {
        if (e.keyCode === 27) {
            e.stopPropagation();
            $('.iw-modal').css("opacity","0");
            $('.iw-modal').css("pointer-events","none");
        }
    });
});

$(document).ready(function() { 
    $(".butSentMes").click(function(event) {
        event.preventDefault();
        sendMessage();
    });
});


function sendMessage() {
    var data = new FormData();
    data.append("idTo", $("#id").text());
    data.append("subject", $(".subject").val());
    data.append("text", $("#textMes").val());
    var token = document.head.querySelector("meta[name='_csrf']").content;
    var header = document.head.querySelector("meta[name='_csrf_header']").content; 
    $.ajax({
    	headers: { 
    		"X-CSRF-TOKEN": token
        },
        type: 'POST',
        enctype: 'multipart/form-data',
        url: '/ajax/sendMessage',
        data : data,
        contentType: false,
        processData: false,
        cache: false,
        timeout: 1000000,
        success: function() {
            $('#newMessage')[0].reset();
        },
        error: function() {  
        	$('#errorsMessage').text('Invalid data');
        }
    });
 
}

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

document.getElementById('test').addEventListener('click', newsSearch);

if (document.getElementById('addFriend') != null) document.getElementById('addFriend').addEventListener('click', addFriend);

function addFriend(elem) {
    var pattern = document.getElementById('id').innerHTML;
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

$(document).ready(function() {
    $("#submitNewNews").click(function(event) {
        event.preventDefault();
        ajaxSubmitForm();
    });
});


function ajaxSubmitForm() {
    var data = new FormData();
    var errorSpan = $('#errorSpan').get(0);
    data.append("idAuthor", $("#idAuthor").val());
    data.append("newNewsText", $("#textNews").val());
    data.append("forFriends", ($('input[name=radname1]:checked').val() == 'friends') ? true : false);
    if ($('input[type=file]')[0].files[0] != undefined)
    	data.append("file", $('input[type=file]')[0].files[0]);
    var arrayTag = $('#tagsNewNews').val().split(', ');
    data.append("tags", arrayTag);
    var token = document.head.querySelector("meta[name='_csrf']").content;
    var header = document.head.querySelector("meta[name='_csrf_header']").content;
    $("#submitNewNews").prop("disabled", true);
    $.ajax({
    	headers: { 
    		"X-CSRF-TOKEN": token
        },
        type: 'POST',
        enctype: 'multipart/form-data',
        url: '/news/add',
        data : data,
        contentType: false,
        processData: false,
        cache: false,
        timeout: 1000000,
        success: function() {
            $("#submitNewNews").prop("disabled", false);
            $('#newNews')[0].reset();
            $('#newsCreateImg').attr("src", "");
            newsSearch();
        },
        error: function(xhr) {
        	errorSpan.textContent = xhr.responseText;
            $("#submitNewNews").prop("disabled", false);
        }
    });
 
}

function previewFile(file, preview) {
    var reader  = new FileReader();
    reader.onloadend = function () {
    	preview.visibility = "visible";
      preview.src = reader.result;
      
    }
    if (file) {
      reader.readAsDataURL(file);
    } else {
      preview.src = "";
    }
  }

$(document).ready(function() {
    $(document).on('change', 'input[type=file]', function(event) {
    	var preview = $(event.target).parent().parent().parent().parent().find('.newsImg').get(0);
        var file    = $(event.target).get(0).files[0];
    	previewFile(file, preview);
    });
});

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

function fillTable(data) {
	cleanNews();
    data.forEach(function(elem) {
    		var mainDIV = $('#pressetNews').parent().get(0).cloneNode(true);
    		$(mainDIV).css('display', 'block');
    		$(mainDIV).find('#pressetNews').attr('id', elem.id);
    		$(mainDIV).find('#nameAuthor').text(elem.author.lastName + ' ' + elem.author.firstName); 
    		$(mainDIV).find('#loginAuthor').text('@' +  elem.author.login);
    		$(mainDIV).find('pre').text(elem.text);
    		$(mainDIV).find('.newsImg').attr('src', '/news/image/' + elem.imageToken);
    		$(mainDIV).find('#date').text(elem.date);
    		if (!elem.forFriends) {
    			$(mainDIV).find('#levelView').remove();
    		}
    		elem.tags.forEach(function(tag) {
    			var tagName = document.createElement('a');
    			tagName.textContent = '#' + tag;
    			$(mainDIV).find('#tags').get(0).append(tagName);
    		});
    		elem.comments.forEach(function(elemCom) {
    			var pressetComment = $(mainDIV).find('#pressetComment').get(0).cloneNode(true);
    			$(pressetComment).attr('id', elemCom.id);
    			$(pressetComment).children().attr('id', elemCom.id);
    			$(pressetComment).find('.comCont').text(elemCom.text);
    			$(pressetComment).find('#dateCom').text(elemCom.date);
    			$(pressetComment).find('#loginCom').text('@' + elemCom.commentator.login);
    			if ($(''))
    			$(mainDIV).find('#commentList').get(0).appendChild(pressetComment);
    		});
    		$(mainDIV).find('#pressetComment').remove();
    		$('#newsList').append(mainDIV);          
    });
}

$( '#newsList' ).on( 'click', '.butEdit', function( event ) {
	var divNews = $( event.target ).parent().parent().parent().get(0);
	var txt = document.createElement('textArea');
	$(txt).addClass('newNewsTextArea');
	$(txt).val($(divNews).find('pre').text());
	$(divNews).find('pre').replaceWith(txt);
	var botCreate = $('.buttomsNews').get(0).cloneNode(true);
	$(botCreate).find('.radio-container').remove();
	$(botCreate).find('.tags').remove();
	$(divNews).find('.bot').remove();
	$(botCreate).find('#submitNewNews').addClass('sendEditNews');
	if ($(divNews).find('.buttomsNews').get(0) == null)
		$(divNews).append(botCreate);
});

$('#newsList').on('click', '.sendEditNews', function(event) {
	var element = $(this).parent().parent().parent().get(0);
	var data = new FormData();
    data.append("idNews", $(this).parent().parent().parent().attr('id'));
    data.append("newNewsText", $(this).parent().parent().parent().find('.newNewsTextArea').val());
    if ($(this).parent().parent().parent().parent().find('input[type=file]')[0].files[0] != undefined)
    	data.append("file", $(this).parent().parent().parent().parent().find('input[type=file]')[0].files[0]);
    var token = document.head.querySelector("meta[name='_csrf']").content;
    var header = document.head.querySelector("meta[name='_csrf_header']").content; 
    $.ajax({
    	headers: { 
    		"X-CSRF-TOKEN": token
        },
        type: 'POST',
        enctype: 'multipart/form-data',
        url: '/news/update',
        data : data,
        contentType: false,
        processData: false,
        cache: false,
        timeout: 1000000,
        success: function() {
        	$(element).find("#errorSpan").text('');
            newsSearch();
        },
        error: function(xhr) {
        	$(element).find("#errorSpan").text(xhr.responseText);
        }
    });
});

$('#newsList').on('click', '.deleteNews', function() {
	var elem = $(this).parent().parent().parent().parent();
    var id = $(this).parent().parent().parent().attr('id');
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
    		alert(xhr.responseText);
    	} else {    		
    		$(elem).remove();
    	}
    }
    xhr.send("id=" + id); 
});

$('#newsList').on('click', '.buttomComment', function(event) {
	$(this).parent().parent().parent().parent().find('#createNewComments').parent().css('display', 'block');
	
});

$('#newsList').on('click', '.editOldComment', function(event) {
	$(this).css('display', 'none');
	$(this).parent().find('.saveOldComment').css('display', 'block');
	var pree = document.createElement('textarea');
	$(pree).addClass('textAreaComCont');
	$(pree).val($(this).parent().parent().parent().find('.comCont').text());
	$(this).parent().parent().parent().find('.comCont').replaceWith(pree);
});

$('#newsList').on('click', '.saveOldComment', function(event) {
	var data = new FormData();
	var element = $(this).parent().parent().parent().get(0);
    var idComment = $(element).attr('id');
    data.append("idComment", idComment);
    data.append("idNews", $(this).parent().parent().parent().parent().parent().parent().children().attr('id'));
    data.append("text", $(this).parent().parent().parent().find('.textAreaComCont').val());
    var token = document.head.querySelector("meta[name='_csrf']").content;
    var header = document.head.querySelector("meta[name='_csrf_header']").content; 
    $.ajax({
    	headers: { 
    		"X-CSRF-TOKEN": token
        },
        type: 'POST',
        enctype: 'multipart/form-data',
        url: '/news/comment/update',
        data : data,
        contentType: false,
        processData: false,
        cache: false,
        timeout: 1000000,
        success: function() {
        	$(element).find('#errorSpan').text('');
            newsSearch();
        },
        error: function(xhr) {  
        	$(element).find('#errorSpan').text(xhr.responseText);
        }
    });
});

$('#newsList').on('click', '.butComCreate', function(event) {
    var data = new FormData();
    var element = $(this).parent().parent().parent().get(0);
    var idNews = $(this).parent().parent().parent().attr('id');
    data.append("idNews", $(this).parent().parent().parent().parent().parent().children().attr('id'));
    data.append("text", $(this).parent().parent().find('.textAreaComCont').val());
    var token = document.head.querySelector("meta[name='_csrf']").content;
    var header = document.head.querySelector("meta[name='_csrf_header']").content; 
    $.ajax({
    	headers: { 
    		"X-CSRF-TOKEN": token
        },
        type: 'POST',
        enctype: 'multipart/form-data',
        url: '/news/comment/add',
        data : data,
        contentType: false,
        processData: false,
        cache: false,
        timeout: 1000000,
        success: function() {
        	$(element).find('#errorSpan').text('');
            newsSearch();
        },
        error: function(xhr) {   
        	$(element).find('#errorSpan').text(xhr.responseText);
        }
    });
 
});
