<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" isELIgnored="false"%>
<%@include file="../jspf/includes.jspf"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%@ include file="../jspf/headconfig.jspf"%>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Homepage</title>
</head>
<body>

	<%@ include file="../jspf/home_menu.jspf"%>

	<div class="jumbotron">

		<h1>
			DB view</b>.
		</h1>
		<ul>
		<c:forEach items="${ticks}" var="record">
			<li><c:out value="${record}"></c:out></li>
		</c:forEach>
		</ul>
		
		<ul>
		<c:forEach items="${names}" var="record">
			<li><c:out value="${record}"></c:out></li>
		</c:forEach>
		</ul>
		
	</div>


	<%@ include file="../jspf/footer.jspf"%>
</body>
</html>