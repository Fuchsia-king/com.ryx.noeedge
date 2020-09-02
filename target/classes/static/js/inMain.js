// 内页js

$(function () {
  $("#screen-ul .tab-li .arrows-r").each(function () {
    $(this).click(function () {
      $(this).toggleClass("active");
      if ($(this).siblings(".tabs-list").children().length > 8) {
        $(this).siblings(".tabs-list").toggleClass("active");
      }
      // console.log(this);
    });
  });
  // 线下课程超出
  if ($("#city-list").height() > 80) {
    $("#city-list").addClass("height48").removeClass("autoHeight");
  } else {
    $("#icon-down").css("display", "none");
  }
  $("#icon-down").click(function () {
    $(this).toggleClass("active");
    $("#city-list").toggleClass("height48").toggleClass("autoHeight");
  });

  $("#tabs-list .item").click(function () {
    $(this).addClass("active").siblings().removeClass("active");
    var i = $(this).index();
    $("#tab-box >div")
      .eq(i)
      .addClass("active")
      .siblings()
      .removeClass("active");
  });

  $("#catalog-lists .level-two .tit-stair .flex-r").each(function () {
    $(this).click(function () {
      $(this).toggleClass("active");
      if ($(this).parent().siblings().children().length != 0) {
        $(this).parents(".level-two").toggleClass("active");
      }
    });
  });
  $("#chapter-lists .level-two .tit-stair .flex-r").each(function () {
    $(this).click(function () {
      $(this).toggleClass("active");
      if ($(this).parent().siblings().children().length != 0) {
        $(this).parents(".level-two").toggleClass("active");
      }
    });
  });


  $("#star-btn .star-item").click(function () {
    var num = $(this).data("num");
    $("#grade-num").val(num);
    switch (num) {
      case 1:
        $("#star-btn >div")
          .eq(0)
          .find("img")
          .attr("src", "./images/insidepage/star_actvie.png");
        $(this)
          .siblings()
          .find("img")
          .attr("src", "./images/insidepage/star.png");
        break;
      case 2:
        $("#star-btn >div:eq(0),#star-btn >div:eq(1)")
          .find("img")
          .attr("src", "./images/insidepage/star_actvie.png");
        $("#star-btn >div:eq(2),#star-btn >div:eq(3),#star-btn >div:eq(4)")
          .find("img")
          .attr("src", "./images/insidepage/star.png");
        break;
      case 3:
        $("#star-btn >div:eq(0),#star-btn >div:eq(1),#star-btn >div:eq(2)")
          .find("img")
          .attr("src", "./images/insidepage/star_actvie.png");
        $("#star-btn >div:eq(3),#star-btn >div:eq(4)")
          .find("img")
          .attr("src", "./images/insidepage/star.png");
        break;
      case 4:
        $(
          "#star-btn >div:eq(0),#star-btn >div:eq(1),#star-btn >div:eq(2),#star-btn >div:eq(3)"
        )
          .find("img")
          .attr("src", "./images/insidepage/star_actvie.png");
        $("#star-btn >div:eq(4)")
          .find("img")
          .attr("src", "./images/insidepage/star.png");
        break;
      case 5:
        $(
          "#star-btn >div:eq(0),#star-btn >div:eq(1),#star-btn >div:eq(2),#star-btn >div:eq(3),#star-btn >div:eq(4)"
        )
          .find("img")
          .attr("src", "./images/insidepage/star_actvie.png");
        break;
      default:
        x = "404";
        console.log(x);
    }
  });

  $("#star-btn .star-item").mouseenter(function () {
    var num = $(this).data("num");
    switch (num) {
      case 1:
        $("#star-btn >div")
          .eq(0)
          .find("img")
          .attr("src", "./images/insidepage/star_actvie.png");
        $(this)
          .siblings()
          .find("img")
          .attr("src", "./images/insidepage/star.png");
        break;
      case 2:
        $("#star-btn >div:eq(0),#star-btn >div:eq(1)")
          .find("img")
          .attr("src", "./images/insidepage/star_actvie.png");
        $("#star-btn >div:eq(2),#star-btn >div:eq(3),#star-btn >div:eq(4)")
          .find("img")
          .attr("src", "./images/insidepage/star.png");
        break;
      case 3:
        $("#star-btn >div:eq(0),#star-btn >div:eq(1),#star-btn >div:eq(2)")
          .find("img")
          .attr("src", "./images/insidepage/star_actvie.png");
        $("#star-btn >div:eq(3),#star-btn >div:eq(4)")
          .find("img")
          .attr("src", "./images/insidepage/star.png");
        break;
      case 4:
        $(
          "#star-btn >div:eq(0),#star-btn >div:eq(1),#star-btn >div:eq(2),#star-btn >div:eq(3)"
        )
          .find("img")
          .attr("src", "./images/insidepage/star_actvie.png");
        $("#star-btn >div:eq(4)")
          .find("img")
          .attr("src", "./images/insidepage/star.png");
        break;
      case 5:
        $(
          "#star-btn >div:eq(0),#star-btn >div:eq(1),#star-btn >div:eq(2),#star-btn >div:eq(3),#star-btn >div:eq(4)"
        )
          .find("img")
          .attr("src", "./images/insidepage/star_actvie.png");
        break;
      default:
        x = "404";
        console.log(x);
    }
  });

  var collect = $("#icon-collect").val();
  if (collect == 1) {
    $("#collect-img").attr("src", "./images/insidepage/icon_collect.png");
  } else {
    $("#collect-img").attr("src", "./images/insidepage/icon_collects.png");
  }
  $("#collect-click").click(function () {
    let val = $(this).find("img").toggleClass("active");
    console.log(val);
    if ($(this).find("img").hasClass("active")) {
      $("#collect-click .text").text("已收藏");
      $("#collect-img").attr("src", "./images/insidepage/icon_collects.png");
    } else {
      $("#collect-click .text").text("收藏");
      $("#collect-img").attr("src", "./images/insidepage/icon_collect.png");
    }
    // $(this).find("img")
  });

  $("#collects-click").click(function () {
    let val = $(this).find("img").toggleClass("active");
    console.log(val);
    if ($(this).find("img").hasClass("active")) {
      $("#collects-click .text").text("已收藏");
      $("#collect-img").attr("src", "./images/insidepage/icon_collects.png");
    } else {
      $("#collects-click .text").text("收藏");
      $("#collect-img").attr("src", "./images/insidepage/icon_collect.png");
    }
    // $(this).find("img")
  });
  new Swiper("#feedback-swiper", {
    slidesPerView: 4,
    spaceBetween: 48,
    loop: true,
    observer: true,
    observeParents: true,
    navigation: {
      nextEl: ".feedback-main .swiper-button-next",
      prevEl: ".feedback-main .swiper-button-prev",
    },
  });

  new Swiper("#serve-swiper", {
    // loop : true,
    observer: true,
    observeParents: true,
    navigation: {
      nextEl: ".serve-main .swiper-button-next",
      prevEl: ".serve-main .swiper-button-prev",
    },
  });

  var bodySwiper = new Swiper('#bodySwiper', {
    direction: 'vertical',
    speed:1000,
    slideClass : 'my-slide',
    pagination: {
      el: '.swiper-pagination',
      clickable :true,
    },
    observer:true,
    // mousewheel: true,
    slidesPerView : 'auto',
  })
  //为了防止不触发用了下面的原生监听滚动
  var bl = false
  var scrollFunc = function (e) {  
    e = e || window.event;  
    if(bl) return true
    if (e.wheelDelta) {  //判断浏览器IE，谷歌滑轮事件               
      if (e.wheelDelta > 0) { //当滑轮向上滚动时  
        bodySwiper.slidePrev();
        bl = true
      }  
      if (e.wheelDelta < 0) { //当滑轮向下滚动时  
        bodySwiper.slideNext();
        bl = true
      }
    } else if (e.detail) {  //Firefox滑轮事件
      if (e.detail> 0) { //当滑轮向上滚动时
        bodySwiper.slidePrev();
        bl = true 
      }  
      if (e.detail< 0) { //当滑轮向下滚动时
        bodySwiper.slideNext();
        bl = true 
      }  
    }
    setTimeout(() => {
      bl = false
    },1000)
  }  
  //给页面绑定滑轮滚动事件  
  if (document.addEventListener) {//firefox  
    document.addEventListener('DOMMouseScroll', scrollFunc, false);  
  }  
  //滚动滑轮触发scrollFunc方法  //ie 谷歌  
  window.onmousewheel = document.onmousewheel = scrollFunc;

  $("#financing-list >.item").mouseenter(function () {
    $(this).addClass("active").siblings().removeClass("active");
  });
});

$(".err-click").click(function () {
  $("#modal-box").fadeOut();
});

$("#payment-list .item").click(function () {
  $(this).addClass("active").siblings().removeClass("active");
});

$("#couponClick .list-box").each(function () {
  $(this).click(function () {
    var data = $(this).data("val");
    $(this).parent().siblings("input").attr("value", data);
    $(".parent select option").each(function () {
      if ($(this).val() == data) {
        $(this).attr("selected", "selected").siblings().removeAttr("selected");
      }
    });
  });
});

$("#topteacher-tabs .item").each(function () {
  $(this).click(function () {
    $(this).addClass("active").siblings().removeClass("active");
    var i = $(this).index();
    $("#switch-list >div")
      .eq(i)
      .addClass("active")
      .siblings()
      .removeClass("active");
  });
});

$("#search_menu .item").each(function(){
  let index=$(this).index();
  $(this).click(function(){
    $(this).addClass("active");
    $(this).siblings().removeClass("active");
    $("#search_list .search-list").eq(index).css('display','block');
    $("#search_list .search-list").eq(index).siblings('.search-list').css('display','none');
  })
})
