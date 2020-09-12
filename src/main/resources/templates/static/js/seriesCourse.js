$(function () {
  // 目录隐藏/显示
  $("#catalog").on("click", ".module_title", function () {
    var el = $(this).siblings(".module_content");
    if (el.is(":visible")) {
      el.slideUp();
      $(this).children(".i_top").css({
        transform: "rotate(180deg)",
      });
    } else {
      el.slideDown();
      $(this).children(".i_top").css({
        transform: "rotate(0deg)",
      });
    }
  });
  // 视频
  var v = {
    video: document.querySelector("#my_video"),
    sound: document.getElementsByClassName("volume-box")[0], //音量容器
    soundBar: document.getElementsByClassName("soundBar")[0], //音量
    warp: document.getElementById("video-full-box"), //视频区域距离左边距离
    soundPercent: 0, //音量百分比
  };
  //音量可拖拽实现
  function updatesound(x, n) {
    if (n) {
      soundPercent = n;
    } else {
      // soundPercent = (100 * (x - v.sound.offsetLeft)) / v.sound.offsetWidth;
      soundPercent = x - v.sound.offsetLeft - v.warp.offsetLeft - 10;
      console.log(x, v.sound.offsetLeft, v.warp.offsetLeft, soundPercent);
    }
    if (soundPercent > 100) {
      soundPercent = 100;
    }
    if (soundPercent < 0) {
      soundPercent = 0;
      $("#mute-click").addClass("cur");
      $("#mute-click").attr("src", "./images/video/icon_mute.png");
      $("#mute-clicks").addClass("cur");
      $("#mute-clicks").attr("src", "./images/video/icon_mutes.png");
    }
    if (soundPercent > 0) {
      $("#mute-click").removeClass("cur");
      $("#mute-click").attr("src", "./images/video/icon_voice.png");
      $("#mute-clicks").removeClass("cur");
      $("#mute-clicks").attr("src", "./images/video/icon_voices.png");
    }
    v.soundBar.style.width = soundPercent + "%";
    v.video.volume = soundPercent / 100;
  }
  //音量获取坐标调用 拖拽实现方法
  var enableSoundDrag = function (e) {
    var soundDrag = false;
    v.sound.onmousedown = function (e) {
      soundDrag = true;
      updatesound(e.pageX);
    };
    v.sound.onmouseup = function (e) {
      if (soundDrag) {
        soundDrag = false;
        updatesound(e.pageX);
      }
    };
    v.sound.onmousemove = function (e) {
      if (soundDrag) {
        updatesound(e.pageX);
      }
    };
  };
  enableSoundDrag();
  updatesound(null, 35); //初始化音量
//   全屏/退出全屏
  $("#showFullvideo").click(function () {
    if ($(this).is(".cur")) {
      $(this).removeClass("cur");
      $("#video-full-box").css({
        width: "100%",
        height: "100%",
      });
      launchFullScreen();
    } else {
      $(this).addClass("cur");
      exitFullscreen();
      $("#video-full-box").css({
        width: "1010px",
        height: "567px",
      });
    }
  });
  // 视频当前进度条点击
  $("#play-line2").click(function (e) {
    $("#video-end").removeClass("active");
    if (video.paused || video.ended) {
      video.play();
      $("#player_btn").removeClass("active");
      enhanceVideoSeek2(e);
    } else {
      enhanceVideoSeek2(e);
    }
  });

  // 视频进度条
  function enhanceVideoSeek2(e) {
    var progressWrap = document.getElementById("play-line2");
    var playProgress = document.getElementById("bar");
    console.log(progressWrap.offsetLeft,playProgress.offsetLeft);
    clearInterval(progressFlag);
    var length = e.pageX - progressWrap.offsetLeft-$('#video-full-box')[0].offsetLeft;
    var percent = length / progressWrap.offsetWidth;
    playProgress.style.width = percent * progressWrap.offsetWidth - 2 + "px";
    video.currentTime = percent * video.duration;
    progressFlag = setInterval(getProgress, 60);
  }
  // 关闭视频
  $("#close-video").click(function(){
    $("#video-full-box").hide();
  })
  
  $(".catalog .list .right_flex .tryLook_button").click(function(){
    $("#video-full-box").show();
  })
});
