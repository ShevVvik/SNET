/**
 * Анонимная самовызывающаяся функция-обертка
 * @param {document} d - получает документ
 */
!function(d) {

  "use strict";

  /**
   * Полифилл для Object.assign()
   */
  Object.assign||Object.defineProperty(Object,"assign",{enumerable:!1,configurable:!0,writable:!0,value:function(e,r){"use strict";if(null==e)throw new TypeError("Cannot convert first argument to object");for(var t=Object(e),n=1;n<arguments.length;n++){var o=arguments[n];if(null!=o)for(var a=Object.keys(Object(o)),c=0,b=a.length;c<b;c++){var i=a[c],l=Object.getOwnPropertyDescriptor(o,i);void 0!==l&&l.enumerable&&(t[i]=o[i])}}return t}});

  /**
   * Переменная необходима для правильной работы форматирования в IE
   * @type {boolean}
   */
  var _onPaste_Strip = false;

  /**
   * Главный объект
   * @type {Object}
   */
  var einput = {};

  /**
   * Shortcut для preventDefault()
   * @param {Event} e - получает событие
   */
  var prevent = function(e) {
    e.preventDefault();
  };

  /**
   * Функция форматирования контента при вставке в поле
   * @param {Event} e - событие
   * @param {Number} maxlength - максимальная длина контента
   */
  einput.format = function(e, maxlength) {
    if (e.originalEvent && e.originalEvent.clipboardData && e.originalEvent.clipboardData.getData) {
      e.preventDefault();
      var text = e.originalEvent.originalEvent.clipboardData.getData("text/plain");
      if (text.length > maxlength) text = text.substring(0, maxlength);
      d.execCommand("insertText", false, text);
    } else if (e.clipboardData && e.clipboardData.getData) {
      e.preventDefault();
      var text = e.clipboardData.getData("text/plain");
      if (text.length > maxlength) text = text.substring(0, maxlength);
      d.execCommand("insertText", false, text);
    } else if (window.clipboardData && window.clipboardData.getData) {
      if (!_onPaste_Strip) {
        _onPaste_Strip = true;
        e.preventDefault();
        d.execCommand("ms-pasteTextOnly", false);
      }
      _onPaste_Strip = false;
    }
  };

  /**
   * Функция установки каретки в необходимую позицию
   * @param {Object} el - необходимый элемент
   * @param {String} [place] - положение каретки
   */
  einput.caret = function(el,place) {
    el = el || einput.els.field;
    var range = d.createRange();
    var sel = function() {
      var s = window.getSelection();
      s.removeAllRanges();
      s.addRange(range);
    };
    switch(place) {
      case "after": // после указанного элемента
        range.setStartAfter(el);
        range.setEndAfter(el);
        if(d.getSelection) sel();
        break;
      case "select": // выделение всего контента в элементе
        range.selectNodeContents(el);
        sel();
        break;
      default: // вставка в элемент после контента
        el.focus();
        range.selectNodeContents(el);
        range.collapse(false);
        sel();
    }
  };

  /**
   * Функция очистки поля
   * @param {String} tag - удаляемый тег
   */
  einput.clear = function(tag) {
    var el = einput.els.field;
    if(tag) {
      var tags = el.getElementsByTagName(tag);
      while (tags[0]) tags[0].parentNode.removeChild(tags[0]);
    } else while (el.firstChild) el.removeChild(el.firstChild);
    einput.caret();
  };

  /**
   * Функция получения контента поля
   * @returns {String} - возвращает введеный текст
   */
  einput.get = function() {
    einput.clear("br");
    return einput.els.field.innerHTML.replace(/<img.*?class="emoji e(.*?)".*?>/g, "__**$1**__").replace(/&nbsp;/g, " ").replace(/\s\s+/g, " ").trim();
  };

  /**
   * Функция вставки смайлика в строку
   * @param {Event} e - событие, возникающее при клике по смайлику из панели
   */
  einput.insert = function(e) {
    var el = einput.els.field;

    // пробелы до и после смайла
    var spaceBefore = d.createTextNode("\u00A0");
    var spaceAfter = d.createTextNode("\u00A0");

    // генерация смайла
    var img = d.createElement("img");
    img.classList.add("emoji");
    img.classList.add(e.target.classList[1]);
    img.src = "data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAABAAAAAQAQMAAAAlPW0iAAAAA1BMVEUAAACnej3aAAAAAXRSTlMAQObYZgAAAAtJREFUCNdjIBEAAAAwAAFletZ8AAAAAElFTkSuQmCC";
    img.width = 16;
    img.height = 16;
    img.setAttribute("onresizestart", "return false");
    img.setAttribute("oncontrolselect", "return false");

    // вставка смайлика в опредленное кареткой место (если поддерживается браузером)
    if (window.getSelection) {
      var sel = window.getSelection();
      if (sel.getRangeAt && sel.rangeCount) {
        var currentEl = sel.focusNode.tagName ? sel.focusNode : sel.focusNode.parentNode;
        if (currentEl === el || currentEl === el.parentNode) {
          var range = window.getSelection().getRangeAt(0);
          range.insertNode(spaceBefore);
          range.insertNode(img);
          if (el.innerHTML.length > 0) range.insertNode(spaceAfter);
          einput.caret(spaceBefore,"after");
          return true;
        }
      }
    }

    // Стандартная вставка смайла, если положение каретки не задано
    el.appendChild(spaceBefore);
    el.appendChild(img);
    el.appendChild(spaceAfter);
    einput.caret();
  };

  /**
   * Функция инициализации поля и панели смайлов
   * @param {Object} [settings] - принимает объект с настройками
   */
  einput.init = function(settings) {

    // предварительные настройки
    settings = Object.assign({},{
      inputId:      "input-box",         // id родительского элемента, в который должна вставляться строка
      placeholder:  "Type something...", // placeholder для пустой строки
      autocomplete: false,               // наличие атрибута autocomplete
      spellcheck:   true,                // наличие атрибута spellcheck
      autofocus:    true,                // наличие атрибута autofocus
      typefocus:    true,                // автофокус в строку при печатании
      pastecheck:   true,                // форматирование при вставке контента в строку
      tabindex:     "-1",                // значение атрибута tabindex
      maxlength:    1000,                // максимальная длина контента в строке
      callback:     function(text) {     // функция, выполяемая после отправки сообщения
        console.log(text);
        einput.clear();
      },
      panelId:           "emoji-box",   // id родительского элемента, в который должна вставляться панель смайлов
      emojiCount:        207            // максимальное количество смайлов в спрайте
    }, settings);

    // подготовка атрибутов autocomplete и spellcheck
    settings.autocomplete = (settings.autocomplete) ? "on" : "off";
    settings.spellcheck = (settings.spellcheck) ? "true" : "false";

    // поиск начальных элементов
    var inputBox = d.getElementById(settings.inputId);
    var emojiBox = d.getElementById(settings.panelId);

    // генерация поля ввода
    inputBox.setAttribute("contenteditable","false");
    inputBox.insertAdjacentHTML('afterbegin', '<div id="' + settings.inputId + '-field" tabindex="' + settings.tabindex + '" autocomplete="' + settings.autocomplete + '" spellcheck="' + settings.spellcheck + '" _moz_resizing="false" contenteditable></div><div id="' + settings.inputId + '-placeholder" data-ph="' + settings.placeholder + '" contenteditable="false"></div>');

    // генерация панели смайлов
    emojiBox.insertAdjacentHTML("afterbegin", "<div></div>");
    var frag = d.createDocumentFragment();
    var emoji = {};
    for (var k = 0; k < settings.emojiCount; k++) {
      emoji["*" + k] = k;
      var smile = d.createElement("span");
      smile.classList.add("emoji");
      smile.classList.add("e" + k);
      frag.appendChild(smile);
    }
    emojiBox.firstChild.appendChild(frag);

    // вывод элементов для глобального использования
    einput.els = {
      fieldBox: inputBox,
      field: inputBox.firstChild,
      fieldPh: inputBox.lastChild,
      panelBox: emojiBox,
      panel: emojiBox.firstChild
    };

    // добавление обработчиков
    var input = einput.els.field;
    input.addEventListener("drag", prevent);
    input.addEventListener("drop", prevent);
    input.addEventListener("resize", prevent);
    input.addEventListener("click", function(e) {// вставка каретки после добавленного смайла
      if (e.target.classList.contains("emoji")) einput.caret(input,"after");
    });
    input.addEventListener("keypress", function(e) {
      var key = e.which || e.keyCode;
      var l = this.textContent.length;

      // ...
      if (l > settings.maxlength) (key === 8 || key === 46 || key === 39 || key === 37) ? null : prevent(e);

      if (key === 13) { // переопредление действия клавиши Enter

        // изменение стандартного браузерного форматирования contenteditable
        if(d.queryCommandSupported("defaultParagraphSeparator")) d.execCommand("defaultParagraphSeparator", false, "");
        if(d.queryCommandSupported("insertHTML")) d.execCommand("insertHTML", false, "");
        if(d.queryCommandSupported("insertBrOnReturn")) d.execCommand("insertBrOnReturn", false, "");

        // вызов callback, в который передается текст
        settings.callback(einput.get());
        prevent(e);
      }
    });
    if (settings.autofocus) einput.caret();
    if (settings.pastecheck) input.addEventListener("paste", function(e) {
      einput.format(e, settings.maxlength);
    });
    if (settings.typefocus) {
      d.addEventListener("keydown", function(e) {
        if (d.activeElement !== input) einput.caret();
        e.stopPropagation();
      });
    }

    // отключение возможности resize для строки
    if(d.queryCommandSupported("enableObjectResizing")) d.execCommand("enableObjectResizing", false, false);

    // удаление лишних дочерних <br>, которые добавляет в строку Firefox
    if ("MozAppearance" in d.documentElement.style) {
      input.addEventListener("keyup", function(e) {
        if (input.textContent.length < 2 && e.key === "Backspace") {
          einput.clear("br");
          input.innerHTML = input.innerHTML.trim();
          einput.caret(input);
        }
        e.stopPropagation();
      });
    }

    // добавление событий для панели
    var panel = einput.els.panel;
    panel.addEventListener("click", function(e) {
      if (e.target.classList.contains("emoji")) einput.insert(e);
    });
    panel.addEventListener("mousedown", prevent);
  };

  window.einput = einput;

}(document);

// добавление события, обрабатывающего отображение или скрытие панели смайлов
document.getElementById("btn").addEventListener("click", function(e) {
  this.classList.toggle("active");
  einput.els.panelBox.classList.toggle("show");
});

// запуск основной функции
einput.init();