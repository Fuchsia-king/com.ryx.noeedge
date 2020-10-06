// 首页js


$(function () {
    // $('#footer').load('./footer.html');
    var mySwiper = new Swiper('#swiper-teacher', {
        autoplay: {
            delay: 3000
        },
        loop : true,
        slidesPerView: 5,
        navigation: {
            nextEl: '#swiper-teacher .swiper-button-next',
            prevEl: '#swiper-teacher .swiper-button-prev',
        },
        spaceBetween: 30,
    })
    $('#swiper-teacher').hover(function(){
        mySwiper.autoplay.stop();
    },function(){
        mySwiper.autoplay.start();
    });
    //鼠标移出隐藏按钮，移入显示按钮
    mySwiper.el.onmouseover=function(){
        mySwiper.navigation.$nextEl.addClass('hides');
        mySwiper.navigation.$prevEl.addClass('hides');
    }
    mySwiper.el.onmouseout=function(){
        mySwiper.navigation.$nextEl.removeClass('hides');
        mySwiper.navigation.$prevEl.removeClass('hides');
    }
    var banner = new Swiper('#swiper-banner', {
        autoplay: {
            delay: 3000
        },
        loop : true,
        navigation: {
            nextEl: '#swiper-banner .swiper-button-next',
            prevEl: '#swiper-banner .swiper-button-prev',
        },

        pagination: {
            el: '#swiper-banner .swiper-pagination',
            clickable :true,
        },
        
    })
    //鼠标移出隐藏按钮，移入显示按钮
    banner.el.onmouseover=function(){
        banner.navigation.$nextEl.addClass('hides');
        banner.navigation.$prevEl.addClass('hides');
    }
    banner.el.onmouseout=function(){
        banner.navigation.$nextEl.removeClass('hides');
        banner.navigation.$prevEl.removeClass('hides');
    }
    var exclusive = new Swiper('#swiper-exclusive', {
        // autoplay: true,//可选选项，自动滑动
        navigation: {
            nextEl: '#swiper-exclusive .swiper-button-next',
            prevEl: '#swiper-exclusive .swiper-button-prev',
        },
        pagination: {
            el: '#swiper-exclusive .swiper-pagination',
            clickable :true,
        }
    })

    // $('.division-Posts .nav .nav-item').click(function () {
    //     $(this).addClass("active")
    //     $(this).siblings().removeClass("active")
    // })
    // $('.recommend .nav .item').click(function () {
    //     $(this).addClass("active")
    //     $(this).siblings().removeClass("active")
    // })
    // $('.header .nav .item').click(function () {
    //     $(this).addClass("active")
    //     $(this).siblings().removeClass("active")
    // })
    var pro_swiper =  new Swiper('#swiper-item',{
        observer: true,
        observeParents: true,
        slidesPerView : 1,
        swipeHandler:'#swiper-item',
    })
    $('#division-nav .item').click(function(){
        var item_index = $(this).index();
        pro_swiper.slideTo(item_index);
    })
    var rec_swiper =  new Swiper('#swiper-recommend',{
        observer: true,
        observeParents: true,
        slidesPerView : 1,
        spaceBetween : 30,
        swipeHandler:'#swiper-recommend',
    })
    $('#recommend-nav .item').click(function(){
        var item_index = $(this).index();
        rec_swiper.slideTo(item_index);
    })
});

$("#division-nav .line").css({
    'left': $("#division-nav li").eq(0).position().left,
    'width': $("#division-nav li").eq(0).outerWidth()
});
$("#division-nav li").click(function() {
    $(this).addClass("active").siblings().removeClass("active");
    $("#division-nav .line").stop().animate({
        left: $(this).position().left,
        width: $(this).outerWidth()
    },100);
})
$("#recommend-nav .line").css({
    'left': $("#recommend-nav li").eq(0).position().left,
    'width': $("#recommend-nav li").eq(0).outerWidth()
});
$("#recommend-nav li").click(function() {
    $(this).addClass("active").siblings().removeClass("active");
    $("#recommend-nav .line").stop().animate({
        left: $(this).position().left,
        width: $(this).outerWidth()
    },100);
})
// }

// function() {
//     $("#division-nav .line").stop().animate({
//         left: $("#division-nav li").eq(0).position().left,
//         width: $("#division-nav li").eq(0).outerWidth()
//     });
// })

$("#feedback-list .item").each(function () {
    $(this).mouseenter(function () {
      var img = $(this).data("img");
      var name = $(this).data("name");
      var position = $(this).data("position");
      var describe = $(this).data("describe");
      $("#note-pic").attr("src",img);
      $("#note-name").text(name);
      $("#note-position").text(position);
      $("#note-desc").text(describe);
    })
})
