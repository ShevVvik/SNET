function previewFile() {
    var preview = document.querySelector('img');
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
    $('.input_file input[type=file]').change(function() {
        var t = $(this).val();
        if (t.indexOf('C:\\fakepath\\') + 1) t = t.substr(12);
        var e = $(this).next().find('.fake_file_input');
        e.val(t);
    });
});

document.getElementById('subret').addEventListener('click', () => {
    document.getElementById('myForm').submit(); 
    return false;
})

function DropDown(el) {
	this.dd = el;
	this.opts = this.dd.find('ul.dropdown > li');
	this.val = [];
	this.index = [];
	this.initEvents();
}

DropDown.prototype = {
	initEvents : function() {
		var obj = this;

		obj.dd.on('click', function(event){
			$(this).toggleClass('active');
			event.stopPropagation();
		});

		obj.opts.children('label').on('click',function(event){
			var opt = $(this).parent(),
						chbox = opt.children('input'),
                        val = chbox.val(),
                        idx = opt.index();

			($.inArray(val, obj.val) !== -1) ? obj.val.splice( $.inArray(val, obj.val), 1 ) : obj.val.push( val );
			($.inArray(idx, obj.index) !== -1) ? obj.index.splice( $.inArray(idx, obj.index), 1 ) : obj.index.push( idx );
		});
	},
	getValue : function() {
		return this.val;
	},
	getIndex : function() {
		return this.index;
	}
	}

	$(function() {

		var dd = new DropDown( $('#dd') );

		$(document).click(function() {
			// all dropdowns
		$('.wrapper-dropdown-4').removeClass('active');
	});

});