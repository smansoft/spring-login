<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
<c:set var="contextPath" value="${pageContext.request.contextPath}"/>
<head>
	<meta charset="UTF-8">
	<title>User Removed (JSP)</title>
	<link rel="shortcut icon" href="${contextPath}/resources/imgs/favicon.ico" type="image/x-icon"/>
	<link rel="icon" href="${contextPath}/resources/imgs/favicon.ico" type="image/x-icon"/>
	<link rel="stylesheet" href="${contextPath}/resources/css/style.css">
</head>
<body>
	<div class="page">
		<div class="form">
			<form:form id="delete-success-form-id" class="delete-form" modelAttribute="userVO" method="post" action="${contextPath}/user/deleted.htm">
				<p align="left">User has been removed</p>
				<p align="left">login: ${login}</p>
				<p align="left">email: ${email}</p>
				<form:button id="delete-success-form-ok-id">OK</form:button>
			</form:form>
		</div>
	</div>
</body>
</html>
