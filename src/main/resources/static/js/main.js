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
    
    
    var data = new FormData();
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
            $('#newsCreateImg').val(null);
            newsSearch();
        },
        error: function() {  
            $("#submitNewNews").prop("disabled", false);
 
        }
    });
 
}

function previewFile() {
    var preview = document.querySelector('#newsCreateImg');
    var file    = document.querySelector('input[type=file]').files[0];
    var reader  = new FileReader();
  
    reader.onloadend = function () {
      preview.src = reader.result;
    }
  
    if (file) {
      reader.readAsDataURL(file);
    } else {
      preview.src = "";
    }
  }

$(document).ready(function() {
    $('input[type=file]').change(function() {
    	previewFile();
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

function fillTable(data) {
	var mainDIV0 = document.querySelector('#pressetNews').parentNode;
	cleanNews();
    data.forEach(function(elem) {
    		var mainDIV = mainDIV0.children[0].cloneNode(true);
    		mainDIV.id = elem.id;
    		var redLine = mainDIV.children[0];
    		var nameLog = redLine.children[0];
    		nameLog.children[0].textContent = elem.author.lastName + ' ' + elem.author.firstName;
    		nameLog.children[1].textContent = '@' +  elem.author.login;
    		mainDIV.children[1].children[0].textContent = elem.text;
    		mainDIV.children[1].children[1].src = '/news/image/' + elem.imageToken;
    		var bot = mainDIV.children[2].children[0].children[0];
    		bot.children[0].textContent = elem.date;
    		if (!elem.forFriends) {
    			bot.children[1].remove();
    		}
    		var ull = document.getElementById('newsList');
    		var newLi = document.createElement('li');
    		var ulComment = document.createElement('ul');
    		var comment = mainDIV0.children[1];
    		var pressetComment = comment.children[0].cloneNode(true);
    		ulComment.appendChild(pressetComment);
    		elem.comments.forEach(function(elemCom) {
    			var divCom = comment.children[1].children[0].cloneNode(true);
    			var topCom = divCom.children[0];
    			var botCom = divCom.children[1];
    			
    			topCom.children[0].textContent = elemCom.text;
    			botCom.children[0].textContent = elemCom.date;
    			botCom.children[1].children[0].textContent = '@' + elemCom.commentator.login;
    			
    			var newLiCom = document.createElement('li');
    			newLiCom.appendChild(divCom);
    			ulComment.appendChild(newLiCom);
    		})
    		
    		newLi.appendChild(mainDIV);
    		newLi.appendChild(ulComment);
    		ull.appendChild(newLi);            
    });

}

//document.getElementById('newPost').addEventListener('click', openMenu);

$( '#newsList' ).on( 'click', '.butEdit', function( event ) {
	var divNews = $( event.target ).parent().parent().parent().get(0);
	var botCreate = $('.buttomsNews').get(0).cloneNode(true);
	$(divNews).find('.bot').remove();
	if ($(divNews).find('.buttomsNews').get(0) == null)
		$(divNews).append(botCreate); 
	  
	  /*var text = $(divNews).children('.newsContent').children('pre').html();
	  $('#textNews').val(text);
	  var src = $(divNews).children('.newsContent').children('img').attr('src');
	  if($(divNews).children('.bot').children('.fieldInfo').children('.leftField').children('#levelView').get(0)){
		  $("#radio2").prop("checked", true);
	  } else {
		  $("#radio1").prop("checked", true);
	  }
	  
	  
	  $('#newsCreateImg').attr('src', src);*/
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
    		console.log(xhr.status + ': ' + xhr.statusText);
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
	$(pree).val($(this).parent().parent().find('.comCont').text());
	$(this).parent().parent().find('.comCont').replaceWith(pree);
});

$('#newsList').on('click', '.saveOldComment', function(event) {
	var data = new FormData();
    var idComment = $(this).parent().parent().attr('id');
    data.append("idComment", idComment);
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
        url: '/news/comment/update',
        data : data,
        contentType: false,
        processData: false,
        cache: false,
        timeout: 1000000,
        success: function() {
            newsSearch();
        },
        error: function() {   
        }
    });
});

$('#newsList').on('click', '.butComCreate', function(event) {
    var data = new FormData();
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
            newsSearch();
        },
        error: function() {   
        }
    });
 
});

function sendNewCommentForm(event) {
    var data = new FormData();
    var idNews = $(this).parent().parent().parent().attr('id');
    data.append("id", $(this).parent().parent().parent().parent().children().attr('id'));
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
            newsSearch();
        },
        error: function() {   
        }
    });
 
}
