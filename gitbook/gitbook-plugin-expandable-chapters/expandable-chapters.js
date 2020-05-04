require(['gitbook', 'jQuery'], function(gitbook, $) {

  //创建网易云音乐外链
  let music = document.createElement("iframe");
  music.src="https://music.163.com/outchain/player?type=2&id=1491585&auto=0&height=66";
  music.setAttribute("style","width:250px;height: 86px;position: fixed; bottom:25px; left: 300px;z-index:99;");
  music.setAttribute("frameborder","no");
  music.setAttribute("border","0");
  music.setAttribute("marginwidth","0");
  music.setAttribute("marginheight","0");
  document.body.appendChild(music)

  var TOGGLE_CLASSNAME = 'expanded',
      CHAPTER = '.chapter',
      ARTICLES = '.articles',
      TRIGGER_TEMPLATE = '<i class="exc-trigger fa"></i>',
      LS_NAMESPACE = 'expChapters';
  var init = function () {
    // adding the trigger element to each ARTICLES parent and binding the event
    $(ARTICLES)
      .parent(CHAPTER)
      .children('a,span')
      .append(TRIGGER_TEMPLATE)
      .on('click', function(e) {
        if (!$(e.target).is('a')) {
          e.preventDefault();
          e.stopPropagation();
          toggle($(e.target).closest(CHAPTER));
        }
      });

    expand(lsItem());
    //expand current selected chapter with it's parents
    var activeChapter = $(CHAPTER + '.active');
    expand(activeChapter);
    expand(activeChapter.parents(CHAPTER));


  } 
  var toggle = function ($chapter) {
    if ($chapter.hasClass('expanded')) {
      collapse($chapter);
    } else {
      expand($chapter);
    }
  }
  var collapse = function ($chapter) {
    if ($chapter.length && $chapter.hasClass(TOGGLE_CLASSNAME)) {
      $chapter.removeClass(TOGGLE_CLASSNAME);
      lsItem($chapter);
    }
  }
  var expand = function ($chapter) {
    if ($chapter.length && !$chapter.hasClass(TOGGLE_CLASSNAME)) {
      $chapter.addClass(TOGGLE_CLASSNAME);
      lsItem($chapter);
    }
  }
  var lsItem = function () {
    var map = JSON.parse(localStorage.getItem(LS_NAMESPACE)) || {}
    if (arguments.length) {
      var $chapters = arguments[0];
      $chapters.each(function (index, element) {
        var level = $(this).data('level');
        var value = $(this).hasClass(TOGGLE_CLASSNAME);
        map[level] = value;
      })
      localStorage.setItem(LS_NAMESPACE, JSON.stringify(map));
    } else {
      return $(CHAPTER).map(function(index, element){
        if (map[$(this).data('level')]) {
          return this;
        }
      })
    }
  }
  gitbook.events.bind('page.change', function() {
    init()
  }); 
});
