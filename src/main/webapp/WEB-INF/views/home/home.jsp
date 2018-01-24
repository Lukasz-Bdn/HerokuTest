<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" isELIgnored="false"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Homepage</title>
</head>
<body>


	<div class="jumbotron">

		<h1>
			Welcome to School Manager </b>.
		</h1>

		<h2>Use top menu to navigate the website</h2>
		<h3>
			<a href="${pageContext.request.contextPath}/logout"
				class="btn btn-primary">Logout</a>
		</h3>
		<div>

			<a class="btn btn-primary"
				href="${pageContext.request.contextPath}/student/userStudent">Open
				student profile</a>
		</div>
		<div>

			<a class="btn btn-primary"
				href="${pageContext.request.contextPath}/teacher/userTeacher">Open
				teacher profile</a>
		</div>
		<div>

			<a class="btn btn-primary"
				href="${pageContext.request.contextPath}/student/userNewStudent">Create
				student profile</a>
		</div>
		<div>

			<a class="btn btn-primary"
				href="${pageContext.request.contextPath}/teacher/userNewTeacher">Create
				teacher profile</a>
		</div>
		<div>

			<a class="btn btn-primary"
				href="${pageContext.request.contextPath}/school/all">"Can do anything profile" (for developers)</a>
		</div>


	</div>


</body>
</html>