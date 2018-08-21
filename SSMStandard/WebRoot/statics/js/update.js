$(function(){
	$("#hid1").hide();
	$("#hid2").hide();
	$("#hid3").hide();
	$("#hid4").hide();
	$("#filepath").hide();
	let stdNumInit=$("#stdNum").val();
	
	validateTip=function(element,css,tipString,status){
		element.css(css);
		element.html(tipString);
		element.prev().attr("validateStatus",status);
	}
	
	$("#stdNum").on("blur",function(){
		let value=$(this).val();
		if(stdNumInit!=value){
			if(value!=null && value!=""){
				$.ajax({
					type:"GET",//请求类型
					url:$("#path").val()+"/standard/stdNumexist.html",//请求的url
					data:{stdNum:$(this).val()},//请求参数
					dataType:"json",//ajax接口（请求url）返回的数据类型
					success:function(data){//data：返回数据（json对象）
						alert(data);
						if(data == "exist"){//账号已存在，错误提示
							validateTip($("#stdNum").next(),{"color":"red"},"该用户账号已存在",false);
						}else{//账号可用，正确提示
							validateTip($("#stdNum").next(),{"color":"green"},"该账号可以使用",true);
						}
					},
					error:function(data){//当访问时候，404，500 等非200的错误状态码
						validateTip($("#stdNum").next(),{"color":"red"},"您访问的页面不存在",false);
					}
				});
			}else{
				validateTip($(this).next(),{"color":"red"},"* 标准号不能为空！",false);
			}
		}else{
			validateTip($("#stdNum").next(),{"color":"green"},"没修改！",true);
		}
	}).on("focus",function(){
		//显示友情提示
		validateTip($(this).next(),{"color":"#666666"},"* 标准号必填",false);
	}).focus();
	
	if($("#uploadFileError").val() == null || $("#uploadFileError").val() == ""){
		$("#packagePath").next().html("* 上传大小不能超过500K * 上传文件类型必须为：jpg、jpeg、png、pneg");
	}else{
		$("#packagePath").next().html($("#uploadFileError").val());
	}
	
	$("#zhname").on("blur",function(){
		let value=$(this).val();
		if(value==null || value==''){
			validateTip($(this).next(),{"color":"red"},"* 中文名称不能为空！",false);
		}else{
			validateTip($(this).next(),{"color":"green"},"可用",true);
		}
	}).on("focus",function(){
		//显示友情提示
		validateTip($(this).next(),{"color":"#666666"},"* 中文名称必填",false);
	});
	
	$("#version").on("blur",function(){
		let value=$(this).val();
		if(value==null || value==''){
			validateTip($(this).next(),{"color":"red"},"* 版本不能为空！",false);
		}else{
			validateTip($(this).next(),{"color":"green"},"可用",true);
		}
	}).on("focus",function(){
		//显示友情提示
		validateTip($(this).next(),{"color":"#666666"},"* 版本必填",false);
	});
	
	$("#keys").on("blur",function(){
		let value=$(this).val();
		if(value==null || value==''){
			validateTip($(this).next(),{"color":"red"},"* 关键字/词不能为空！",false);
		}else{
			validateTip($(this).next(),{"color":"green"},"可用",true);
		}
	}).on("focus",function(){
		//显示友情提示
		validateTip($(this).next(),{"color":"#666666"},"* 关键字/词必填",false);
	});
	
	$("#releaseDate").on("blur",function(){
		let value=$(this).val();
		let reg= /^\d{4}(\-|\/|.)\d{1,2}\1\d{1,2}$/;
		if(value!=""){
			if(!reg.test(value)){
				validateTip($(this).next(),{"color":"red"},"* 日期格式有错！",false);
			}else{
				validateTip($(this).next(),{"color":"green"},"可用",true);
			}
		}else{
			validateTip($(this).next(),{"color":"green"},"",true);
		}
	}).on("focus",function(){
		//显示友情提示
		validateTip($(this).next(),{"color":"#666666"},"* 发布日期(yyyy-MM-dd)选填",false);
	});
	
	$("#implDate").on("blur",function(){
		let value=$(this).val();
		let reg= /^\d{4}(\-|\/|.)\d{1,2}\1\d{1,2}$/;
		if(value!=""){
			if(!reg.test(value)){
				validateTip($(this).next(),{"color":"red"},"* 日期格式有错！",false);
			}else{
				validateTip($(this).next(),{"color":"green"},"可用",true);
			}
		}else{
			validateTip($(this).next(),{"color":"green"},"",true);
		}
	}).on("focus",function(){
		//显示友情提示
		validateTip($(this).next(),{"color":"#666666"},"* 实施日期(yyyy-MM-dd)选填",false);
	});
	
	$("#packagePath_").on("blur",function(){
		let value=$(this).val();
		if($("#isChoose1").is(':checked')){
			if(value==null || value==""){
				validateTip($(this).next(),{"color":"red"},"* 附件不能为空！",false);
			}else{
				validateTip($(this).next(),{"color":"green"},"可用",true);
			}
		}
	}).on("focus",function(){
		//显示友情提示
		validateTip($(this).next(),{"color":"#666666"},"* 附件必填",false);
	});
	
	$("#update").on("click",function(){
		if ($("#stdNum").attr("validateStatus") != "true") {
			$("#stdNum").blur();
		} else if ($("#zhname").attr("validateStatus") != "true") {
			$("#zhname").blur();
		} else if ($("#version").attr("validateStatus") != "true") {
			$("#version").blur();
		} else if ($("#keys").attr("validateStatus") != "true") {
			$("#keys").blur();
		} else if ($("#releaseDate").attr("validateStatus") != "true") {
			$("#releaseDate").blur();
		} else if ($("#implDate").attr("validateStatus") != "true") {
			$("#implDate").blur();
		} else if ($("#packagePath_").attr("validateStatus") != "true") {
			$("#packagePath_").blur();
		}else {
			if (confirm("是否确认提交数据")) {
				$("#frm").submit();
			}
		}
	})
	
	$("#toback").click(function(){
		window.history.go(-1);
	})
	
	$("#isChoose1").click(function(){
		$("#filepath").show();
		validateTip($("#packagePath_").next(),{"color":"red"},"",false);
	})
	
	$("#isChoose2").click(isChoose=function(){
		$("#filepath").hide();
		validateTip($("#packagePath_").next(),{"color":"green"},"没作修改",true);
	})
	
	isChoose();
})