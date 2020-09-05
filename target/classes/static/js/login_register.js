$(function(){
    function changeRegister_btn(){
        var input_index=0;
        $("#register-box input").each(function(){
            if($(this).val()!=''){
                input_index++;
            }
        })
        if(input_index==6&&$('#register_read').get(0).checked){
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
    
})