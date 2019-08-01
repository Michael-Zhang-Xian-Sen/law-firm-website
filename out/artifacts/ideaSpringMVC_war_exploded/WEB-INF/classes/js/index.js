/**
 * 创建人：张浩
 * 创建时间：2019.7.9
 * 用途：作为index.html页面的唯一js文件，对页面进行控制操作。
 */

$(document).ready(function(){
	$("#left-power-img").click(logOut);				// 绑定注销事件
	$("#left-gear-img").click(updateToggle);		// 绑定“齿轮"样式切换
	$("#b13").click(clientToggle);					// 绑定"用户管理"样式切换
	$("#add-user-submit").click(addUser);			// 绑定"新增用户"功能
	$("#middle-viewer").click(selectImg);			// 绑定"选择图片"功能
	$("#middle-upload").click(uploadImg);			// 绑定"上传图片"功能
	$("#middle-viewer-hidden").change(renderUploadImgName);		// File input绑定"渲染头像修改input"事件
	$("#middle-update-submit").click(updateUserPrivateInfo);	// 绑定"修改个人信息"功能
	dateUpdate();									// 打开页面时刷新时间
	setInterval(dateUpdate,1000);			// 设置没过1s进行一次时间刷新
	initIndexUser();								// 初始化用户信息
	initUserManagement();							// 初始化用户管理信息
});

// 中间表格toggle操作
$('#myTabs li').click(function (e) {
	e.preventDefault()
	$("#myTabs li").removeClass("active");
	$(this).addClass("active");

	var pane_str = $(this).children("a").attr("href");
	console.log(pane_str);
	$(".tab-pane").removeClass("active");
	$(pane_str).addClass("active");


	// 更改右上角button。
	$("#add-user").addClass("hidden");
	$("#add-team").addClass("hidden");
	$("#add-role").addClass("hidden");
	var buttonId = "#add-"+pane_str.split("-")[1];
	console.log(buttonId);
	$(buttonId).removeClass("hidden");

	// 更改title
	$("title").text("汉韬 - "+$(this).text());
})

// 注销操作
function logOut(){
	// 向后台发送消息，注销当前session
	$.ajax({
		  type:"GET",
		  url:"logOut",
		  async:false,
		  contentType:"application/x-www-form-urlencoded",
		  dataType:"json",
		  success:function(message){
			  console.log(message);
			  if(message.status == "success"){
			  	console.log("注销成功！")
			  	window.location.href = "login";
			  }else{
			  	console.log("注销失败！");
			  }
		  },
		  error:function(message){
			  console.log(message);
		  }
	 });
}

// 获取cookie
function getCookie(cookieName){
	var cookieValue = null;//返回cookie的value值
	if (document.cookie && document.cookie != '') {
		var cookies = document.cookie.split(';');//将获得的所有cookie切割成数组
		for (var i = 0; i < cookies.length; i++) {
			var cookie = cookies[i];//得到某下标的cookies数组
			if (cookie.substring(0, cookieName.length + 1).trim() == cookieName.trim() + "=") {//如果存在该cookie的话就将cookie的值拿出来
				cookieValue = cookie.substring(cookieName.length + 2, cookie.length);
				break;
			}
		}
	}
	return cookieValue;
}

// 初始化获取个人数据
function initIndexUser(){
	$.ajax({
		type:"post",
		url:"initIndexUser",
		async:false,
		contentType:"application/x-www-form-urlencoded",
		data:JSON.parse(JSON.stringify(initIndexMessage("userMessage"))),
		dataType:"json",
		success:function(message){
			console.log(message);
			if(message == null){
				console.log("初始化用户信息失败！");
				// window.location.href = "/demo/login.html";
			}else{
				$("#left-username").text(message.name);
				$("#user-img").attr("src",message.head_img);
				// 图片存储？
				console.log("初始化用户信息成功！");
			}
		},
		error:function(message){
			console.log(message);
		}
	})
}

// 初始化用户管理信息
function initUserManagement(){
	$.ajax({
		type:"post",
		url:"initUserManagement",
		async:false,
		contentType:"application/x-www-form-urlencoded",
		data:JSON.parse(JSON.stringify(initIndexMessage("userManagement"))),
		dataType:"json",
		success:function(message){
			console.log("响应信息的长度为："+message.length);
			console.log("第一个人的姓名为："+message[0].name);
			renderUserManagement(message);
		},
		error:function(message){
			console.log(message);
		}
	})
}

// 渲染用户管理栏目
function renderUserManagement(message){
	for(var i=0;i<message.length;i++){
		console.log("json数据中sex类型为："+typeof message[i].gender);
		console.log("判断数据中sex类型为："+typeof 'm')
		if(message[i].gender == 'm'){
			var sex="男";
		}else{
			var sex="女";
		}

		$("#li-user").append("<div class=\"tab-row tab-user-info\">\n" +
			"\t\t\t\t\t    \t\t<div class=\"tab-c1 tab-block2\">\n" +
			"\t\t\t\t\t\t\t\t\t<input type=\"checkbox\" name=\"checkboxG5\" id=\"checkboxG5\" class=\"css-checkbox\">\n" +
			"\t\t\t\t\t\t\t\t\t<label for=\"checkboxG5\" class=\"css-label tabs-check-box\"></label>\n" +
			"\t\t\t\t\t    \t\t</div>\n" +
			"\t\t\t\t\t    \t\t<div class=\"tab-c2 tab-block2\">\n" +
			"\t\t\t\t\t    \t\t\t<span class=\"td-no\">"+(parseInt(i)+1)+"</span>\n" +
			"\t\t\t\t\t    \t\t</div>\n" +
			"\t\t\t\t\t    \t\t<div class=\"tab-c3 tab-block2\">\n" +
			"\t\t\t\t\t    \t\t\t<span class=\"td-username\">"+message[i].nickname+"</span>\n" +
			"\t\t\t\t\t    \t\t</div>\n" +
			"\t\t\t\t\t    \t\t<div class=\"tab-c4 tab-block2\">\n" +
			"\t\t\t\t\t    \t\t\t<span class=\"td-truename\">"+message[i].name+"</span>\n" +
			"\t\t\t\t\t    \t\t</div>\n" +
			"\t\t\t\t\t    \t\t<div class=\"tab-c5 tab-block2\">\n" +
			"\t\t\t\t\t    \t\t\t<span class=\"td-sex\">"+sex+"</span>\n" +
			"\t\t\t\t\t    \t\t</div>\n" +
			"\t\t\t\t\t    \t\t<div class=\"tab-c6 tab-block2\">\n" +
			"\t\t\t\t\t    \t\t\t<span class=\"td-apartment\">"+message[i].apartment+"</span>\n" +
			"\t\t\t\t\t    \t\t</div>\n" +
			"\t\t\t\t\t    \t\t<div class=\"tab-c7 tab-block2\">\n" +
			"\t\t\t\t\t    \t\t\t<span class=\"td-job\">"+message[i].job+"</span>\n" +
			"\t\t\t\t\t    \t\t</div>\n" +
			"\t\t\t\t\t    \t\t<div class=\"tab-c8 tab-block2\">\n" +
			"\t\t\t\t\t    \t\t\t<span class=\"td-contact\">"+message[i].contact+"</span>\n" +
			"\t\t\t\t\t    \t\t</div>\n" +
			"\t\t\t\t\t    \t\t<div class=\"tab-c9 tab-block2\">\n" +
			"\t\t\t\t\t    \t\t\t<span class=\"td-email\">"+message[i].email+"</span>\n" +
			"\t\t\t\t\t    \t\t</div>\n" +
			"\t\t\t\t\t    \t\t<div class=\"tab-c10-2 tab-block2\">\n" +
			"\t\t\t\t\t    \t\t\t<img class='update-user-info' src=\"resources/images/ht_lawyer_firm/pen.png\">\n" +
			"\t\t\t\t\t    \t\t\t<img class='delete-user-info' src=\"resources/images/ht_lawyer_firm/bin.png\">\n" +
			"\t<button class=\"btn btn-primary update-user-info-button hide\" type=\"button\">提交</button>\n" +
			"\t<button type=\"button\" class=\"btn btn-danger cancel-update-user-info-button hide\">取消</button>"+
			"\t\t\t\t\t    \t\t</div>\n" +
			"\t\t\t\t\t    \t</div>");
	}
	$(".update-user-info").on('click',preUpdateUser);	// 绑定"更新用户"功能
	$(".cancel-update-user-info-button").on('click',cancelUpdateUser);
	$(".update-user-info-button").on('click',updateUser);
	$(".delete-user-info").on('click',deleteUser);
}

function removeUserManagement(){
	$("div").remove(".tab-user-info");
}

// 初始化索引信息
function initIndexMessage(str){
	var json = {
		"message":str
	};

	return json;
}

// "修改"样式切换
function updateToggle(){
	// 更换header
	$("#middle-content-header-name").addClass("hide-opacity");
	$("#middle-content-header-name2").removeClass("hide-opacity");
	$("#middle-content-header-search-wrapper").addClass("hide-opacity")
	// 更换body
	$("#middle-content-body").addClass("hidden");
	$("#middle-content-body2").removeClass("hidden");
	//修改右栏
	$("#b13").removeClass("now-right-block");
	// 修改title
	$("title").text("汉韬 - 个人信息");
}

// "用户管理"切换样式
function clientToggle(){
	if(!$("#b13").hasClass("now-right-block")){
		// 更换header
		$("#middle-content-header-name2").addClass("hide-opacity");
		$("#middle-content-header-name").removeClass("hide-opacity");
		$("#middle-content-header-search-wrapper").removeClass("hide-opacity")

		// 更换body
		$("#middle-content-body2").addClass("hidden");
		$("#middle-content-body").removeClass("hidden");

		// 修改右栏
		$("#b13").addClass("now-right-block");

		// 修改title
		$("title").text("汉韬 - 用户管理");

		// 更改右上角button。
		$("#add-user").addClass("hidden");
		$("#add-team").addClass("hidden");
		$("#add-role").addClass("hidden");
		$("#add-user").removeClass("hidden");

		// 更改框上方的按钮
		$("#myTabs li").removeClass("active");
		$("#myTabs").children(":first").addClass("active");

		// 更改中间内容
		$(".tab-pane").removeClass("active");
		$("#li-user").addClass("active");
	}else{
		return;
	}
}

/**
 * 修改时间
 */
function dateUpdate(){
	var myDate=new Date();
	var localDate = myDate.toLocaleDateString().split("/");
	var hours = myDate.getHours();
	var min = myDate.getMinutes();
	var weekday;
	
	if(hours.toString().length==1){
		hours = "0" + hours.toString();
	}
	if(min.toString().length==1){
		min = "0" + min.toString();
	}
	switch(myDate.getDay()){
		case 1:weekday="星期一";break;
		case 2:weekday="星期二";break;
		case 3:weekday="星期三";break;
		case 4:weekday="星期四";break;
		case 5:weekday="星期五";break;
		case 6:weekday="星期六";break;
		case 7:weekday="星期日";break;
	}
	
	$("#header-year").text(localDate[0]);
	$("#header-month").text(localDate[1]);
	$("#header-day").text(localDate[2]);
	$("#header-weekday").text(weekday);
	$("#header-hours").text(hours);
	$("#header-minutes").text(min);
}

/**
 * 添加用户信息
 */
function addUser(){
	$.ajax({
		type:"post",
		url:"addUser",
		async:false,
		contentType:"application/x-www-form-urlencoded",
		data:JSON.parse(JSON.stringify(addUserInfo())),
		dataType:"json",
		success:function(message){
			if(message.status != "failed"){
				console.log("添加成功！");
				// 若成功，返回全表的查询结果
				console.log(message);
				// 提示添加成功!
				addTips();
				// 先删除结果
				removeUserManagement();
				// 再渲染结果
				renderUserManagement(message);
			}else{
				alert("添加失败！");
			}
		},
		error:function(message){
			console.log("添加失败！");
			// 若添加失败，添加提示信息
			console.log(message);
		}
	})
}

/**
 * 获取待添加的用户信息
 * @returns {{gender: string, contact: (*|jQuery|string|undefined), nickname: (*|jQuery|string|undefined), name: (*|jQuery|string|undefined), job: (*|jQuery|string|undefined), apartment: (*|jQuery|string|undefined), email: (*|jQuery|string|undefined)}}
 */
function addUserInfo(){
	var gender;
	if($("#add-gender-m").parent().hasClass("active")){
		gender = 'm';
	}else{
		gender = 'w';
	}

	var json = {
		"nickname":$("#add-nickname").val(),
		"name":$("#add-name").val(),
		"gender":gender,
		"apartment":$("#add-apartment").val(),
		"job":$("#add-job").val(),
		"contact":$("#add-contact").val(),
		"email":$("#add-email").val()
	};

	return json;
}

function addTips(){
	$("#user-modal-footer").append("<div class=\"alert alert-success\" role=\"alert\">添加成功！</div>");
}

/**
 * 修改用户信息
 */
function preUpdateUser(){
	// 图片设置hide。
	$(this).parent().children("img").addClass("hide");

	// 按钮移除hide
	$(this).parent().children("button").removeClass("hide");

	// 同时从性别至电子邮箱几个span全部隐藏
	$(this).parent().parent().find(".td-sex ").addClass("hide");
	$(this).parent().parent().find(".td-apartment").addClass("hide");
	$(this).parent().parent().find(".td-job").addClass("hide");
	$(this).parent().parent().find(".td-contact").addClass("hide");
	$(this).parent().parent().find(".td-email").addClass("hide");

	// 同时从性别至电子邮箱添加input框
	$(this).parent().parent().children(".tab-c5").append("<input type=\"text\" class=\"updateSexTd\">");
	$(this).parent().parent().children(".tab-c6").append("<input type=\"text\" class=\"updateApartmentTd\">");
	$(this).parent().parent().children(".tab-c7").append("<input type=\"text\" class=\"updateJobTd\">");
	$(this).parent().parent().children(".tab-c8").append("<input type=\"text\" class=\"updateContactTd\">");
	$(this).parent().parent().children(".tab-c9").append("<input type=\"text\" class=\"updateEmailTd\">");
}

// 取消修改用户信息
function cancelUpdateUser(){
	// 添加按钮hide，移除图片hide
	$(this).parent().children("button").addClass("hide");
	$(this).parent().children("img").removeClass("hide");

	console.log(this);

	// 同时移除从性别至电子邮箱的input框
	$(this).parent().parent().find(".updateSexTd").remove();
	$(this).parent().parent().find(".updateApartmentTd").remove();
	$(this).parent().parent().find(".updateJobTd").remove();
	$(this).parent().parent().find(".updateContactTd").remove();
	$(this).parent().parent().find(".updateEmailTd").remove();

	// 添加移除从性别至电子邮箱的class
	$(this).parent().parent().find(".td-sex ").removeClass("hide");
	$(this).parent().parent().find(".td-apartment").removeClass("hide");
	$(this).parent().parent().find(".td-job").removeClass("hide");
	$(this).parent().parent().find(".td-contact").removeClass("hide");
	$(this).parent().parent().find(".td-email").removeClass("hide");
}


function cancelUpdateUser2(tempThis,message){
	console.log("输出的是this"+$(tempThis));
	$(tempThis).parent().children("button").addClass("hide");
	$(tempThis).parent().children("img").removeClass("hide");

	console.log(this);

	// 同时移除从性别至电子邮箱的input框
	$(tempThis).parent().parent().find(".updateSexTd").remove();
	$(tempThis).parent().parent().find(".updateApartmentTd").remove();
	$(tempThis).parent().parent().find(".updateJobTd").remove();
	$(tempThis).parent().parent().find(".updateContactTd").remove();
	$(tempThis).parent().parent().find(".updateEmailTd").remove();

	// 添加移除从性别至电子邮箱的class
	$(tempThis).parent().parent().find(".td-sex ").removeClass("hide");
	$(tempThis).parent().parent().find(".td-apartment").removeClass("hide");
	$(tempThis).parent().parent().find(".td-job").removeClass("hide");
	$(tempThis).parent().parent().find(".td-contact").removeClass("hide");
	$(tempThis).parent().parent().find(".td-email").removeClass("hide");

	// 修改span
	$(tempThis).parent().parent().find(".td-sex ").text(message.gender);
	$(tempThis).parent().parent().find(".td-apartment").text(message.apartment);
	$(tempThis).parent().parent().find(".td-job").text(message.job);
	$(tempThis).parent().parent().find(".td-contact").text(message.contact);
	$(tempThis).parent().parent().find(".td-email").text(message.email);
}
/**
 * 修改用户信息
 */
function updateUser(){
	var json ={
		"nickname":$(this).parent().parent().find(".td-username").text(),
		"gender":$(this).parent().parent().find(".updateSexTd").val(),
		"apartment":$(this).parent().parent().find(".updateApartmentTd").val(),
		"job":$(this).parent().parent().find(".updateJobTd").val(),
		"contact":$(this).parent().parent().find(".updateContactTd").val(),
		"email":$(this).parent().parent().find(".updateEmailTd").val()
	};
	console.log(json);
	var tempThis = this;
	$.ajax({
		type:"post",
		url:"updateUser",
		async:false,
		contentType:"application/x-www-form-urlencoded",
		data:JSON.parse(JSON.stringify(json)),
		dataType:"json",
		success:function(message){
			console.log(message);
			// 弹出修改成功的消息
			cancelUpdateUser2(tempThis,message);
		},
		error:function(message){
			console.log(message);
		}
	})
}

// 删除功能
function deleteUser(){
	var tempThis = this;
	json = {
		"nickname":$(tempThis).parent().parent().find(".td-username").text().toString()
	}
	$.ajax({
		type:'post',
		url:'deleteUser',
		async:false,
		contentType:"application/x-www-form-urlencoded",
		data:JSON.parse(JSON.stringify(json)),
		dataType:"json",
		success:function(message){
			console.log(message);
			if(message.status == "success"){
				$(tempThis).parent().parent().empty();
			}else{
				// 提示错误信息
			}
		},
		error:function(message){
			console.log(message);
		}
	})
}

/**
 * 触发file input进行图片选择
 */
function selectImg(){
	$("#middle-viewer-hidden").trigger("click");

}

/**
 * 绑定上传图片功能
 */
function uploadImg() {
	var tempThis = this;
	var formData = new FormData(document.getElementById("upload-user-img"));
	$.ajax({
		type:"post",
		url:"uploadImg",
		data:formData,
		cache:false,
		processData:false,
		contentType: false,
		success:function(message){
			// 一旦改变 执行该函数
			$("#user-img").attr("src",message.head_img);
		}
	})
}

/**
 * 向"头像修改"input框添加图片名称。
 */
function renderUploadImgName(){
	str=$(this).val();
	var arr=str.split('\\');//注split可以用字符或字符串分割
	var my=arr[arr.length-1];//这就是要取得的图片名称
	console.log("图片名称为："+my);
	$("#head-img-name-input").val(my);
}

/**
 * 修改个人信息
 */
function updateUserPrivateInfo(){
	// 检验密码输入是否正确！较麻烦，以后改。
	// if($("#update-password1").val()!=$("#update-password2").val()){
	//
	// }

	$.ajax({
		type:"post",
		url:"updateUserPrivateInfo",
		data:JSON.parse(JSON.stringify(getUserPrivateInfo())),
		cache:false,
		success:function(message){
			// 清空所有栏目的信息
			$("#update-password1").val("");
			$("#update-password2").val("");
			$("#update-phone").val("");
			$("#update-email").val("");

			// 提示修改成功
			alert("修改成功！");
		},
		error:function(message){
			console.log(message);
		}
	})
}

/**
 * 获取个人信息->修改个人信息
 * @returns {{password: (*|jQuery|string|undefined), contact: (*|jQuery|string|undefined), email: (*|jQuery|string|undefined)}}
 */
function getUserPrivateInfo(){
	return {
		"password":$("#update-password1").val(),
		"contact":$("#update-phone").val(),
		"email":$("#update-email").val()
	}
}