function getImageVerifyCode(imgId,w,h){
	$("#"+imgId).attr("src","/get_image_verify_code?w="+ w +"&h=" + h + "&" + Math.random());
}

function getResetPasswordVerify(){
	return getMobileVerify("/send_reset_password_verify_code.html");
}

function getRegisterMobileVerify (){
	return getMobileVerify("/send_register_verify_code.html");
}
function getBindMobileVerify(){
	return getMobileVerify("/send_bind_verify_code.html");
}
function getMyMobileCode(){
	jQuery.ajax({
    	url: "/my/send_common_verify_code1.html", 
    	type: "post",
    	async:true,
    	contentType: "application/x-www-form-urlencoded; charset=utf-8",
    	dataType: "json",
    	success: function(msg){		    		
			if(msg.isSuccess){
	    		var obj = msg.obj;
	    		if(obj.code != 0){
	    			showErrorTip(obj.detail,2);
	    		}
	    		else{
	    			showSuccessTip("验证码发送成功，请留意您的手机",2);
	    		}
			}
			else{
				showErrorTip(msg.errorMsg,2);
			}
    	},
    	error: function(XMLHttpRequest, textStatus, errorThrown) {
        },
	});
	return false;
}

function getMyMobileCode2(){
	var mobile = $("#mobile").val();
	if(mobile == '' || null == mobile || undefined == mobile){
		showErrorTip("请输入手机号码",2);
	}
	jQuery.ajax({
    	url: "/my/send_common_verify_code2.html?mobile=" + mobile, 
    	type: "post",
    	async:true,
    	contentType: "application/x-www-form-urlencoded; charset=utf-8",
    	dataType: "json",
    	success: function(msg){		    		
			if(msg.isSuccess){
	    		timeVerifyCode("btnSendVerifyCode");
	    		var obj = msg.obj;
	    		if(obj.code != 0){
	    			showErrorTip(obj.detail,2);
	    		}
	    		else{
	    			showSuccessTip("验证码发送成功，请留意您的手机",2);
	    		}
			}
			else{
				showErrorTip(msg.errorMsg,2);
			}
    	},
    	error: function(XMLHttpRequest, textStatus, errorThrown) {
        },
	});
	return false;
}

var rerifyCodeWait=60; 
function timeVerifyCode(o) {  
    if (rerifyCodeWait == 0) {  
        $("#"+o).removeAttr("disabled");            
        $("#"+o).attr("value","获取验证码");  
        console.log("rerifyCodeWait--->" + rerifyCodeWait);
        $("#"+o).css("color","white");
        rerifyCodeWait = 60;  
    } else {  
    	$("#"+o).attr("disabled","disabled");  
    	$("#"+o).css("color","#ee812d");
    	$("#"+o).attr("value","重新发送(" + rerifyCodeWait + ")");    
        rerifyCodeWait--;  
        console.log("rerifyCodeWait--->" + rerifyCodeWait);
        setTimeout(function() {
        	timeVerifyCode(o);
        },  
        1000) ; 
    }  
} 
function getMobileVerify(url)
{		
	var mobile = $("#mobile").val();
	var imgVerifyCode = $("#imgVerifyCode").val();
	jQuery.ajax({
    	url: url,
    	type: "post",
    	async:true,
    	contentType: "application/x-www-form-urlencoded; charset=utf-8",
    	data: "mobile=" + mobile + "&imgVerifyCode=" + imgVerifyCode , 
    	dataType: "json",
    	success: function(msg){		
			if(msg.isSuccess){
	    		timeVerifyCode("btnSendVerifyCode");
	    		var obj = msg.obj;
	    		if(obj.code != 0){
	    			showErrorTip(obj.detail,2);
	    			getImageVerifyCode('ivc',118,36);
	    		}
	    		else{
	    			showSuccessTip("验证码发送成功，请留意您的手机",2);
	    		}
			}
			else{
				showErrorTip(msg.errorMsg,2);
				getImageVerifyCode('ivc',118,36);
			}

    	},
    	error: function(XMLHttpRequest, textStatus, errorThrown) {
        },
	});
	return false;
}


function getMySQCode(){
	return getMyMobileVerify('/my/send_security_question_code.html');
}

function getMyMobileVerify (url){
	jQuery.ajax({
    	url: url, 
    	type: "post",
    	async:true,
    	contentType: "application/x-www-form-urlencoded; charset=utf-8",
    	dataType: "json",
    	success: function(msg){		    		
			if(msg.isSuccess){
	    		var obj = msg.obj;
	    		if(obj.code != 0){
	    			showErrorTip(obj.detail,2);
	    		}
	    		else{
	    			showSuccessTip("验证码发送成功，请留意您的手机",2);
	    		}
			}
			else{
				showErrorTip(msg.errorMsg,2);
			}
    	},
    	error: function(XMLHttpRequest, textStatus, errorThrown) {
        },
	});
	return false;
}


function getCommonMobileVerify (){		
	var mobile = $("#mobile").val();
	jQuery.ajax({
    	url: "/send_common_verify_code.html", 
    	type: "post",
    	async:true,
    	contentType: "application/x-www-form-urlencoded; charset=utf-8",
    	data: "mobile=" + mobile , 
    	dataType: "json",
    	success: function(msg){		    		
			if(msg.isSuccess){
	    		var obj = msg.obj;
	    		if(obj.code != 0){
	    			showErrorTip(obj.detail,2);
	    		}
	    		else{
	    			showSuccessTip("验证码发送成功，请留意您的手机",2);
	    		}
			}
			else{
				showErrorTip(msg.errorMsg,2);
			}
    	},
    	error: function(XMLHttpRequest, textStatus, errorThrown) {
        },
	});
	return false;
}

function download(id)
{	
	var imgVerifyCode = $("#imgVerifyCode").val();
	jQuery.ajax({
    	url: "/my/ajax/ajax_check_score.html",
    	type: "get",
    	async:true,
    	contentType: "application/x-www-form-urlencoded; charset=utf-8",
    	data: "id=" + id + "&imgVerifyCode=" + imgVerifyCode , 
    	dataType: "json",
    	success: function(msg){		    		
			if(msg.isSuccess){
				open_link('/my/download_'+ id +'.html');
			}
			else{
				if(msg.errorMsg == '' || undefined == msg.errorMsg ){showScoreModel();}
				else{ showErrorTip(msg.errorMsg,2)};
			}
    	},
    	error: function(XMLHttpRequest, textStatus, errorThrown) {
			showLoginModel();
        },
	});
	return false;
}

function view_detail(id)
{	
	var imgVerifyCode = $("#imgVerifyCode").val();
	jQuery.ajax({
    	url: "/my/ajax/ajax_check_score.html",
    	type: "get",
    	async:true,
    	contentType: "application/x-www-form-urlencoded; charset=utf-8",
    	data: "id=" + id + "&imgVerifyCode=" + imgVerifyCode , 
    	dataType: "json",
    	success: function(msg){		    		
			if(msg.isSuccess){
				window.location = '/my/viewdetail_'+ id +'.html';
			}
			else{
				if(msg.errorMsg == ''  || undefined == msg.errorMsg ){showScoreModel();}
				else{ showErrorTip(msg.errorMsg,2)};
			}
    	},
    	error: function(XMLHttpRequest, textStatus, errorThrown) {
			showLoginModel();
        },
	});
	return false;
}

function submit(){
	$("#formId").submit();
}
function open_link(url){
	var $aa = $('<a>');    
    $aa.html('  ');    
    $(document.body).append($aa);    
    $aa.attr('href',url);    
    $aa.attr('target','_blank'); 
    $aa.get(0).click();
}
function showAudit(objType,objId){
	jQuery.ajax({
    	url: "/my/ajax_get_audit_record.html", 
    	type: "get",
    	async:true,
    	contentType: "application/x-www-form-urlencoded; charset=utf-8",
    	data: "objId=" + objId + "&objType=" + objType , 
    	dataType: "json",
    	success: function(msg){    		
			if(msg.isSuccess){
				$("#auditContainer").empty();						
				var list = msg.obj;						
				if(null ==list || list.length ==0){
					$("#auditContainer").append('没有审核记录');
				}
				else
				{		
					$(list).each(function(){//遍历JSON
						var html = '<div class="daudit">'+ this.tcreateString + '    |    '+this.createrString +'    |    '+ this.statusString ;
						html = html + '<div>'+ this.descr +'</div></div>';
						$("#auditContainer").append(html);
					});							
				}
			}
			else{
				$("#auditContainer").append(msg.errorMsg);
			}
			
    	},
    	error: function(XMLHttpRequest, textStatus, errorThrown) {
			alert('系统错误');
        },
	});
	$("#auditModel").modal('show');
}