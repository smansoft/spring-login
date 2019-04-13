<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
<c:set var="contextPath" value="${pageContext.request.contextPath}"/>
<head>
	<meta charset="UTF-8">
	<title>User Info (JSP)</title>
	<link rel="shortcut icon" href="${contextPath}/resources/imgs/favicon.ico" type="image/x-icon"/>
	<link rel="icon" href="${contextPath}/resources/imgs/favicon.ico" type="image/x-icon"/>
	<link rel="stylesheet" href="${contextPath}/resources/css/style.css">
</head>
<body>
	<div class="page">
		<div class="form">
			<p align="center">User Info: ${userVO}</p>
			<p align="left">Login: ${userVO.login}</p>
			<p align="left">Email: ${userVO.email}</p>
			<p align="left">Name: ${userVO.name}</p>
			<p align="left">Enabled: ${userVO.enabled}</p>
			<p align="left">Roles: <c:forEach items="${userVO.authorities}" var="authority">${authority},</c:forEach></p>
			<p align="left"><a href="${contextPath}/user/delete.htm">Delete</a></p>
			<p align="left"><a href="${contextPath}/logout.htm">Logout</a></p>
		</div>
	</div>
</body>
</html>
