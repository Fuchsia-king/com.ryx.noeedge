// Back to top
$("#goTop").click(function () {
  $("html,body").animate(
    {
      scrollTop: 0,
    },
    1000
  );
  console.log(1);
});

$(window).scroll(function () {
  if ($(document).scrollTop() >= 300) {
    $("#goTop").addClass("show");
  } else {
    $("#goTop").removeClass("show");
  }
});

$("#home-search").focus(function () {
  // var data = localStorage.getItem('historyList');
  $("#history-box").show();
  // if (data) {
  //     historydata(JSON.parse(data));
  // }
});

// $("#search-btn").click(function () {
//     console.log(2);
// var val = $("#home-search").val();
// var data = localStorage.getItem('historyList');
// gethistoryList.push(val);
// console.log(gethistoryList);
// var historyVal = "<div class='item'>"+val+"</div>"
// $("#history-box .history-list").html('');
// $("#history-box .history-list").append(historyVal);
// if (val != '') {
//   localStorage.setItem('historyList', JSON.stringify([]));
// }
// if (data) {
//     var arr = JSON.parse(data);
// } else {
//     var arr = []; //如果没有数据，则新增一条
// }
// arr.push(val);
// removalDuplicate(arr);
// localStorage.setItem('historyList', JSON.stringify(arr));
// });

$("#home-search").blur(function () {
  $("#history-box").hide();
  // setTimeout(function () {
  // }, 100)
});

var exclusive = new Swiper("#swiper-organization", {
  loop: true,
  navigation: {
    nextEl: "#swiper-organization .swiper-button-next",
    prevEl: "#swiper-organization .swiper-button-prev",
  },
  slidesPerView: 6,
  spaceBetween: 40,
});

$(".down-content .main .left .max-navlist >li").mouseenter(function () {
  $(this).addClass("active").siblings().removeClass("active");
  var i = $(this).index();
  $("#menus-list >div")
    .eq(i)
    .addClass("active")
    .siblings()
    .removeClass("active");
});

$("#study-list >div").each(function () {
  let numData = $(this).find(".schedule-num");
  numData
    .parent()
    .next()
    .find(".schedule-line")
    .css("width", numData.data("num") + "%");
});

$("#courses-list >div").click(function () {
  $(this).addClass("active").siblings().removeClass("active");
  var i = $(this).index();
  $("#switchwrap-list >div")
    .eq(i)
    .addClass("active")
    .siblings()
    .removeClass("active");
});

$("#myOrder-table .tr").each(function () {
  $(this).click(function () {
    $(this).toggleClass("active");
  });
});

$("#money-list >div").click(function () {
  $(this).addClass("active").siblings().removeClass("active");
  var i = $(this).index();
  $(".money-wrap .money-list >div")
    .eq(i)
    .addClass("active")
    .siblings()
    .removeClass("active");
});

$(".radio-list >div").click(function () {
  $(this).addClass("active").siblings().removeClass("active");
});

$("#enterprise-click").click(function () {
  $(".attestation-middle").hide();
  $(".enterprise-box").fadeIn();
});

$(".enterprise-box .get-back").click(function () {
  $(".enterprise-box").hide();
  $(".attestation-middle").fadeIn();
});

$("#lecturer-click").click(function () {
  $(".attestation-middle").hide();
  $(".certification-box").fadeIn();
});

$(".certification-box .get-back").click(function () {
  $(".attestation-middle").fadeIn();
  $(".certification-box").hide();
});

$("#industry-btn").click(function () {
  $("#onlistClick").toggle();
});
$("#sex-btn").click(function () {
  $("#onSex-btn").toggle();
});

$("#onlistClick .list-box").each(function () {
  $(this).click(function () {
    var data = $(this).data("val");
    $("#industry-btn").find("input").attr("value", data);
    $("#onlistClick").hide();
    $(".ind2 select option").each(function () {
      if ($(this).val() == data) {
        $(this).attr("selected", "selected").siblings().removeAttr("selected");
      }
    });
  });
});
$("#onSex-btn .list-box").each(function () {
  $(this).click(function () {
    var data = $(this).data("val");
    $("#sex-btn").find("input").attr("value", data);
    $("#onSex-btn").hide();
    $(".sex select option").each(function () {
      if ($(this).val() == data) {
        $(this).attr("selected", "selected").siblings().removeAttr("selected");
      }
    });
  });
});

$("#figure-box-slect#figure-box-slect").click(function () {
  $(this).children(".option-list").toggle();
});
$("#figure-box-slect .option-list li").each(function () {
  $(this).click(function () {
    $(this).parent().siblings(".state-input").val($(this).text());
    console.log($(this).text());
    console.log($(this).parent().siblings(".state-input"));
  });
});

$("#myBasicinfo-list .item").each(function () {
  $(this).click(function () {
    $(this).addClass("active").siblings().removeClass("active");
    var i = $(this).index();
    $(".myBasicinfo-list .wrap-item")
      .eq(i)
      .addClass("active")
      .siblings()
      .removeClass("active");
  });
});

$(".reset-btn").click(function () {
  $("#reset-middle").fadeIn();
});

$(".reset-cancel").click(function () {
  $("#reset-middle").fadeOut();
});

$(".member-middle .member-box .member-head .flex-r").click(function () {
  $("#member-middle").fadeOut();
});

$("#add-btn").click(function () {
  $("#member-middle").fadeIn();
});

$("#up-click").click(function () {
  var nowVal = $("#figure-input").val();
  $("#figure-input").val(parseInt(nowVal) + 1);
  console.log(nowVal);
});
$("#down-click").click(function (event) {
  var nowVal = $("#figure-input").val();
  $("#figure-input").val(nowVal - 1);
  if (nowVal <= 0) {
    $("#figure-input").val(parseInt(nowVal) + 0);
  }
});

$("#enterprise-wrap .item").each(function () {
  $(this).click(function () {
    $(this).addClass("active").siblings().removeClass("active");
    var i = $(this).index();
    $(".enterprise-list .wrap-item")
      .eq(i)
      .addClass("active")
      .siblings()
      .removeClass("active");
  });
});

if ($("#dadyInput").length != 0) {
  laydate.render({
    elem: "#dadyInput", //指定元素
  });
}

$(".nav-main .nav-list .item").each(function () {
  $(this).find("i").parent().addClass("noClass");
});

$("#sidebar-icon").click(function () {
  $("#sidebar-main").animate({ width: "toggle" });
  // $("#sidebar-main").css("display","none");
  // $("#my_video").css("height","calc(100% - 91px");
  $(".curriculum-tit").hide();
  setTimeout(function () {
    $(".curriculum-tit").show();
  }, 400);
  $(".curriculum-list").hide();
  setTimeout(function () {
    $(".curriculum-list").show();
  }, 400);
  $(".toggle-list").hide();
  setTimeout(function () {
    $(".toggle-list").show();
  }, 400);
  $(this).toggleClass('active');
});

$("#video-toggle >.item .lable-box").each(function () {
  $(this).click(function () {
    console.log($(this));
    $(this).parent().toggleClass("active");
  });
});
$("#saveVideo").click(function () {
  $(this).toggleClass("active");
});
// 视频时间计算
function durationTrans(a) {
  var b = "";
  var h = parseInt(a / 3600),
    m = parseInt((a % 3600) / 60),
    s = parseInt((a % 3600) % 60);
  if (h > 0) {
    h = h < 10 ? "0" + h : h;
    b += h + ":";
  }
  m = m < 10 ? "0" + m : m;
  s = s < 10 ? "0" + s : s;
  b += m + ":" + s;
  return b;
}
if ($("#my_video").length == 1) {
  var video = document.querySelector("#my_video");
  var progressFlag;
  // 视频总时间
  video.oncanplay = function () {
    $("#video-total").text(durationTrans(video.duration));
  };
  // 视频当前时间
  video.ontimeupdate = function () {
    $("#video-now").text(durationTrans(video.currentTime));
  };
  // 视频播放完毕
  video.onended = function () {
    $("#player_btn").addClass("active");
    video.currentTime = 0;
    $("#video-end").addClass("active");
  };

  // 视频点击播放暂停
  $("#player_btn").click(function () {
    // $(this).toggleClass("active");
    $("#video-end").removeClass("active");
    if (video.paused) {
      if (video.ended) {
        video.currentTime = 0;
      }
      video.play();
      $(this).removeClass("active");
      progressFlag = setInterval(getProgress, 60);
    } else {
      video.pause();
      $(this).addClass("active");
      clearInterval(progressFlag);
    }
  });

  // 视频当前进度条
  function getProgress() {
    var percent = video.currentTime / video.duration;
    $("#bar").css("width", (percent * 100).toFixed(1) + "%");
  }

  // 视频当前进度条点击
  $("#play-line").click(function (e) {
    $("#video-end").removeClass("active");
    if (video.paused || video.ended) {
      video.play();
      $("#player_btn").removeClass("active");
      enhanceVideoSeek(e);
    } else {
      enhanceVideoSeek(e);
    }
  });

  // 视频进度条
  function enhanceVideoSeek(e) {
    var progressWrap = document.getElementById("play-line");
    var playProgress = document.getElementById("bar");

    clearInterval(progressFlag);
    var length = e.pageX - progressWrap.offsetLeft;
    var percent = length / progressWrap.offsetWidth;
    playProgress.style.width = percent * progressWrap.offsetWidth - 2 + "px";
    video.currentTime = percent * video.duration;
    progressFlag = setInterval(getProgress, 60);
  }
  // 视频静音
  $("#mute-click").click(function () {
    if ($(this).is(".cur")) {
      $(this).removeClass("cur");
      $(this).attr("src", "./images/video/icon_voice.png");
      video.muted = false;
      video.volume = 0.35;
      document.getElementsByClassName("soundBar")[0].style.width="35%";
    } else {
      // $(this).addClass('cur');
      $(this).addClass("cur");
      $(this).attr("src", "./images/video/icon_mute.png");
      video.muted = true;
      video.volume = 0;
      document.getElementsByClassName("soundBar")[0].style.width="0%";
    }
  });
  $("#mute-clicks").click(function () {
    if ($(this).is(".cur")) {
      $(this).removeClass("cur");
      $(this).attr("src", "./images/video/icon_voices.png");
      $("#mute-click").attr("src", "./images/video/icon_voice.png");
      video.muted = false;
      video.volume = 0.35;
      document.getElementsByClassName("soundBar")[0].style.width="35%";
    } else {
      // $(this).addClass('cur');
      $(this).addClass("cur");
      $(this).attr("src", "./images/video/icon_mutes.png");
      $("#mute-click").attr("src", "./images/video/icon_mute.png");
      video.muted = true;
      video.volume = 0;
      document.getElementsByClassName("soundBar")[0].style.width="0%";
    }
  });
  // 点击视频全屏
  $("#showFull").click(function () {
    if ($(this).is(".cur")) {
      $(this).removeClass("cur");
      exitFullscreen();
      $("#sidebar-main,#sidebar-icon,.get-back").show();
    } else {
      $(this).addClass("cur");
      launchFullScreen();
      $("#sidebar-main,#sidebar-icon,.get-back").hide();
      //   timer = setInterval(function () {
      //     if (marioVideo.offsetHeight == videoHight) {
      //         alert("执行退出全屏操作...");
      //         document.getElementById("heool").style.color = "black";
      //         clearInterval(timer)
      //     }
      // }, 500)
    }
  });
  // 播放速度
  $("#play-speed li").each(function () {
    var speed = 1;
    $(this).click(function () {
      speed = $(this).children().children().text();
      console.log(speed);
      video.playbackRate = speed;
      $(this).addClass("active").siblings().removeClass("active");
    });
  });

  // 全屏
  function launchFullScreen() {
    var ele = document.documentElement;
    if (ele.requestFullScreen) {
      ele.requestFullScreen();
    } else if (ele.mozRequestFullScreen) {
      ele.mozRequestFullScreen();
    } else if (ele.webkitRequestFullScreen) {
      ele.webkitRequestFullScreen();
    }
  }

  // 退出全屏
  function exitFullscreen() {
    var de = document;
    if (de.exitFullscreen) {
      de.exitFullscreen();
    } else if (de.mozCancelFullScreen) {
      de.mozCancelFullScreen();
    } else if (de.webkitCancelFullScreen) {
      de.webkitCancelFullScreen();
    }
  }

  // 修改esc退出键的问题只谷歌上
  function fullscreenchange() {
    var isFull = document.webkitIsFullScreen;
    if (!isFull) {
      $("#sidebar-main,#sidebar-icon,.get-back").show();
    }
  }
  document.addEventListener("webkitfullscreenchange", fullscreenchange);
  // 修改esc退出键的问题只谷歌上end

  // 阻止f11
  document.onkeydown = function (e) {
    var vol = 0.1; //1代表100%音量，每次增减0.1
    var time = 10; //单位秒，每次增减10秒
    if (e.keyCode == 122) {
      return false;
    }
    //鼠标上下键控制视频音量
    else if (e && e.keyCode === 38) {
      // 按 向上键
      video.volume !== 1 ? (video.volume += vol) : 1;
      return false;
    } else if (e && e.keyCode === 40) {
      // 按 向下键
      video.volume !== 0 ? (video.volume -= vol) : 1;
      return false;
    } else if (e && e.keyCode === 37) {
      // 按 向左键
      video.currentTime !== 0 ? (video.currentTime -= time) : 1;
      return false;
    } else if (e && e.keyCode === 39) {
      // 按 向右键
      video.volume !== video.duration ? (video.currentTime += time) : 1;
      return false;
    } else if (e && e.keyCode === 32) {
      // 按空格键 判断当前是否暂停
      $("#player_btn").click();
      return false;
    }
  };
}

// var liuHeight = document.documentElement.clientHeight;
// $("#black-bg").css("height",liuHeight);
// console.log(liuHeight);
