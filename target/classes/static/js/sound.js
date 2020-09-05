var v = {
  video: document.querySelector("#my_video"),
  sound: document.getElementsByClassName("volume-box")[0], //音量容器
  soundBar: document.getElementsByClassName("soundBar")[0], //音量
  warp: document.getElementById("my_video"), //视频区域距离左边距离
  soundPercent: 0, //音量百分比
};
//音量可拖拽实现
function updatesound(x, n) {
  if (n) {
    soundPercent = n;
  } else {
    // soundPercent = (100 * (x - v.sound.offsetLeft)) / v.sound.offsetWidth;
    soundPercent = x - v.sound.offsetLeft - 10;
    console.log(v.sound.offsetLeft);
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