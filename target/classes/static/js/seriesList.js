//滚动定位
// $("#carte-list").fadeIn("400");
$(window).scroll(function () {
  if ($("#cut-list .cut-list>.item").length > 0) {
    if (
      $(window).scrollTop() >=
        $("#cut-list .cut-list>.item").eq(0).offset().top - 200 &&
      $(window).scrollTop() <=
        $("#cut-list .cut-list>.item")
          .eq($("#cut-list .cut-list>.item").length - 1)
          .offset().top +
          $("#cut-list .cut-list>.item")
            .eq($("#cut-list .cut-list>.item").length - 1)
            .outerHeight()
    ) {
      $("#carte-list").fadeIn("400");
      for (var i = 0; i <= $("#cut-list .cut-list>.item").length - 1; i++) {
        if (
          $(window).scrollTop() >=
            $("#cut-list .cut-list>.item").eq(i).offset().top - 50 &&
          $(window).scrollTop() <
            $("#cut-list .cut-list>.item").eq(i).offset().top +
              $("#cut-list .cut-list>.item").eq(i).outerHeight()
        ) {
          $("#carte-list .item").eq(i).addClass("active");
          $("#carte-list .item").eq(i).siblings().removeClass("active");
        }
      }
    } else {
      $("#carte-list").hide();
    }
  }
});
//点击定位
$("#carte-list .item").click(function () {
  var _index = $(this).index();
  console.log(_index);
  console.log($("#cut-list .cut-list>.item"));
  var _height = $("#cut-list .cut-list>.item").eq(_index).offset().top;

  $("html,body").animate(
    {
      scrollTop: _height,
    },
    400
  );
  // $(this).addClass("active").siblings().removeClass("active");
});
