$(function() {
	validateTip=function(element,css,tipString,status){
		element.css(css);
		element.html(tipString);
		element.prev().attr("validateStatus",status);
	}
	
	page_nav=function (frm, num) {
		$("#_pageIndex").val(num);
//		frm._pageIndex.value = num;
		frm.submit();
	}

//	function jump_to(frm, num) {
//		//验证用户的输入
//		var regexp = /^[1-9]\d*$/;
//		var totalPage = document.getElementById("totalPage").value;
//		if (!regexp.test(num)) {
//			alert("请输入大于0的正整数！");
//			return false;
//		} else if ((num - totalPage) > 0) {
//			alert("请输入小于总页数的页码");
//			return false;
//		} else {
//			page_nav(frm, num);
//		}
//	}
	
	$("#insert").click(function() {
		window.location.href = $("#path").val()+"/standard/insert.html";
	})

	$("#allcheckbox").on("click", function() {
//		if ($(this).is(':checked')) {
//			$("[name='checkstandard']").prop("checked", "checked");
//		} else {
//			$("[name='checkstandard']").removeProp("checked");
//		}
		$("[name='checkstandard']").prop("checked", $(this).prop("checked"));
	})

	
	$("#delete").on("click", function() {
		let id_array = new Array();
		$("[name='checkstandard']:checked").each(function() {
			id_array.push($(this).val());
		})
		let ids = id_array.join(",");
		$.ajax({
			type : "get",
			url : $("#path").val() + "/standard/delete.html",
			data : {id : ids},
			dataType: "text",
			success : function(data) {
				if ("false" == data) {
					alert("删除出错！")
				}
				if ("true" == data) {
					alert("删除成功！")
					window.location.href = $("#path").val()+"/standard/standardlist.html?_pageIndex="+$("#_pageIndex").val();
				}
			}
		})
	})
	
//	$("#download").on("click",function(){
//		let path=$("#downloadPath").val();
//		$.ajax({
//			type : "get",
//			url : $("#path").val() + "/standard/download.html",
//			data : {packagePath : path},
//			dataType: "text",
//			success : function(data) {
//				if ("false" == data) {
//					alert("下载系统出错！");
//				}
//				if ("empty" == data) {
//					alert("文件地址无效！");
//				}
//			}
//		})
//	})

})