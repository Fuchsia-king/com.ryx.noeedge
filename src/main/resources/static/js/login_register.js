$(function(){
    function changeRegister_btn(){
        var input_index=0;
        $("#register-box input").each(function(){
            if($(this).val()!=''){
                input_index++;
            }
        })
        if(input_index==8&&$('#register_read').get(0).checked){
            $('#index_register').addClass('active');
        }else{
            $('#index_register').removeClass('active');
        }
    }
    // 登录按钮判断
    $('#login_phone,#login_psw').keyup(function(){
        let phone=$(this).val();
        let psw=$('#login_psw').val();
        if(phone!=''&&psw!=''){
            $("#index_login").addClass('active');
        }else{
            $("#index_login").removeClass('active');
        }
    })
    // 注册按钮判断
    $('#register-box input').keyup(function(){
        changeRegister_btn();
    })
    $("#register_read").change(function(){
        changeRegister_btn();
    })
    // 找回密码确认按钮判断
    $("#forgetPsw-btn input").keyup(function(){
        var input_index=0;
        $("#forgetPsw-btn input").each(function(){
            if($(this).val()!=''){
                input_index++;
            }
        })
        if(input_index==4){
            $('#index_submit_psw').addClass('active');
        }else{
            $('#index_submit_psw').removeClass('active');
        }
    })

    //点击登录
    $('#loginbut').click(function(){
        $("#index_login").addClass('active');//使登录按钮变橙色
    });

    //获取登录表单返回数据,隐藏登录注册按钮,显示个人信息
    $("#login-form").ajaxForm(function(data) {
        if(data.code==200){
            $('#infoname').text(data.username);
            $('#login_div').removeClass('active');
            $('#login_regist1').toggle();
            $('#logreg1').toggle();
            $('#face-head1').toggle();
        }else{
            $('#login_error')[0].style.display='';
        }
    });

    //获得随机防爬图形码
    $('#verify_img').click(function(){
        let w = 118;
        let h = 36;
        // let l = 4; //默认验证码为4位,支持调整
        getImageVerifyCode('verify_img',w,h);
    });
    //获得手机验证码
    $('#btnSendVerifyCode').click(function (){
        var mobile = $("#mobile").val();
        var imgVerifyCode = $("#imgVerifyCode").val();
        let data = {"mobile":mobile,"imgVerifyCode":imgVerifyCode}
        $.post('/send_register_verify_code',data,function (msg){
            let obj = $.parseJSON(msg);
            if(!obj.isSuccess){
                layer.msg(obj.errorMsg);
            }
        });
    });
    
})