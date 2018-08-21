<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="fm" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE html>
<html lang="en">
  <head>
    <title>增加标准信息</title>
    <meta name="content-type" content="text/html; charset=UTF-8">
    <meta name="keywords" content="keyword1,keyword2,keyword3">
    <meta name="description" content="This is my page">
  </head>
  <body>
    <header><h2>增加标准信息</h2></header>
    <section>
    	<fm:form method="post" enctype="multipart/form-data" id="frm" modelAttribute="standard" action="${pageContext.request.contextPath }/standard/insert.html">
    	<table>
    		<tr>
    			<td>标准号</td>
    			<td><fm:input path="stdNum" id="stdNum"/><span></span></td>
    		</tr>
    		<tr>
    			<td>中文名称</td>
    			<td><fm:input path="zhname" id="zhname"/><span></span></td>
    		</tr>
    		<tr>
    			<td>版本</td>
    			<td><fm:input path="version" id="version"/><span></span></td>
    		</tr>
    		<tr>
    			<td>关键字/词</td>
    			<td><fm:input path="keys" id="keys"/><span></span></td>
    		</tr>
    		<tr>
    			<td>发布日期(yyyy-MM-dd)</td>
    			<td><fm:input path="releaseDate" id="releaseDate"/><span></span></td>
    		</tr>
    		<tr>
    			<td>实施日期(yyyy-MM-dd)</td>
    			<td><fm:input path="implDate" id="implDate"/><span></span></td>
    		</tr>
    		<tr>
    			<td>附件<p id="hid1"><fm:input path="packagePath" /><p></td>
    			<td><input type="file" name="packagePath_" id="packagePath_"/><span></span></td>
    		</tr>
    		<tr>
    			<td colspan="2">
    				<input type="button" name="insert" id="insert" value="保存" />
					<input type="button" name="toback" id="toback" value="返回" />
    			</td>
    		</tr>
    	</table>
    		<input type="hidden" id="uploadFileError" value="${uploadFileError}"/>
    	</fm:form>
    </section>
    <input type="hidden" value="${pageContext.request.contextPath }" id="path"/>
  </body>
    <script type="text/javascript" src="${pageContext.request.contextPath }/statics/js/jquery-1.12.4.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath }/statics/js/insert.js"></script>
</html>