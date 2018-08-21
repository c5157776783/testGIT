<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="fm" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>

<!DOCTYPE html>
<html lang="en">
<head>
<title>标准规范信息</title>
<meta name="content-type" content="text/html; charset=UTF-8">
<meta name="keywords" content="keyword1,keyword2,keyword3">
<meta name="description" content="This is my page">
</head>
<body>
	<header>
		<h2>标准信息列表</h2>
	</header>
	<section>
		<div>
			<div>
				<form
					action="${pageContext.request.contextPath}/standard/standardlist.html" method="post">
					<input type="hidden" name="_pageIndex" value="${pageIndex } " id="_pageIndex"/>
					<input type="text" name="keys" id="keys" value="${keys }" placeholder="请输入搜索关键字/词"/>
					<input type="submit" name="seach" id="seach" value="提交检索" />
				</form>
				<input type="button" name="insert" id="insert" value="新增标准" />
				<input type="button" name="delete" id="delete" value="删除标准" />
			</div>
		</div>
		<div>
			<table>
				<tr>
					<th><input type="checkbox" name="allcheckbox" id="allcheckbox" value="-1"/></th>
					<th>标准号</th>
					<th>中文名称</th>
					<th>版本</th>
					<th>发布日期</th>
					<th>实施日期</th>
					<th>操作</th>
				</tr>
				<c:forEach var="standard" items="${standardList }" varStatus="status">
				<tr>
					<td><input type="checkbox" name="checkstandard" id="${standard.id }" value="${standard.id }"/></td>
					<td>${standard.stdNum }</td>
					<td>${standard.zhname }</td>
					<td>${standard.version }</td>
					<td><fmt:formatDate value="${standard.releaseDate }" pattern="yyyy-MM-dd" /></td>
					<td><fmt:formatDate value="${standard.implDate }" pattern="yyyy-MM-dd" /></td>
					<td>
						<input type="hidden" value="${standard.packagePath }" id="downloadPath">
						<a id="download" href="../standard/download.html?packagePath=${standard.packagePath}">下载</a>
						<a href="../standard/update/${standard.id}.html">修改</a>
					</td>
				</tr>
				</c:forEach>
			</table>
		</div>
		<div class="page-bar">
			<ul class="page-num-ul clearfix">
				<li>共${totalCount }条记录&nbsp;&nbsp; ${pageIndex }/${totalPage }页</li>
				<c:if test="${pageIndex > 1}">
					<a href="javascript:page_nav(document.forms[0],1);">首页</a>
					<a href="javascript:page_nav(document.forms[0],${pageIndex-1});">上一页</a>
				</c:if>
				<c:if test="${pageIndex < totalPage }">
					<a href="javascript:page_nav(document.forms[0],${pageIndex+1 });">下一页</a>
					<a href="javascript:page_nav(document.forms[0],${totalPage });">最后一页</a>
				</c:if>
			</ul>
		</div>
	</section>
	<input type="hidden" value="${pageContext.request.contextPath }" id="path"/>
</body>
	<script type="text/javascript" src="${pageContext.request.contextPath }/statics/js/jquery-1.12.4.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath }/statics/js/standardlist.js"></script>
</html>