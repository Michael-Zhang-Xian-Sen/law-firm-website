/**
 * 创建时间：2019.7.11
 * 创建人：张浩
 */

$(document).ready(function(){
	initPage();								// 初始化inut信息
	$("#login-submit").click(userLogin);  	// 绑定提交事件
});

function getInformation(){
	return {
		"name":$("#username input").val(),
		"password":$("#password input").val()
	}
}

function setCookie(name, value, seconds) {
    seconds = seconds || 0;   //seconds有值就直接赋值，没有为0
    var expires = "";
    if (seconds != 0) {      //设置cookie生存时间
        var date = new Date();
        date.setTime(date.getTime() + (seconds * 1000));
        expires = "; expires=" + date.toGMTString();
    }
    document.cookie = name + "=" + escape(value) + expires + "; path=/";   //转码并赋值
}

function getCookie(c_name) {
    if (document.cookie.length > 0) {
        var c_start = document.cookie.indexOf(c_name + "=")
        if (c_start != -1) {
            c_start = c_start + c_name.length + 1
            var c_end = document.cookie.indexOf(";", c_start)
            if (c_end == -1) c_end = document.cookie.length
            return unescape(document.cookie.substring(c_start, c_end)).replace(/\"/g, "");
        }
    }
    return null;
}

function clearCookie(name) {
    setCookie(name, "", -1);
}

/**
 * 用户登录事件
 */
function userLogin(){
	$.ajax({
		type:"POST",
		url:"login.do",
		async:false,
		cache:false,
		ifModified:true,
		contentType:"application/x-www-form-urlencoded",
		data:JSON.parse(JSON.stringify(getInformation())),
		dataType:"json",
		success:function(message){
			console.log(message);
			if(message.status == "failed"){
				// 清空密码栏并提示错误信息
				$("#password input").val("");
				$("#password label").removeClass("hidden");
			}else{
				$("#password label").addClass("hidden");
				// 检查是否有选中记住用户名和密码。如果有的话则设置cookie。
				if($("#checkboxG5").prop('checked')){
					// 设置用户名、密码及checkbox的cookie
					// 设置用户名cookie
					$.cookie('username', $(".user-info-input").val(), { expires: 7 });
					$.cookie('password', $("#password .user-info-input").val(), { expires: 7 });
					$.cookie('checked',"true",{expires:7});
				}else{
					// 清除cookie
					$.cookie('username', '', { expires: -1 });
					$.cookie('password', '', { expires: -1 });
					$.cookie('checked', '', { expires: -1 });
				}

				// 跳转页面
				var date = new Date();
				var time = date.getTime()
				window.location.href = "index?" + time;
			}
		},
		error:function(message){
			console.log(message);
		}
	});
}

function initPage(){
	// 若之前设置为记住用户名和密码
	if($.cookie("checked") == "true"){
		var name = $.cookie("username");
		var password = $.cookie("password");

		$("#checkboxG5").attr("checked",true);
		$("#username .user-info-input").val(name);
		$("#password .user-info-input").val(password);
	}
}