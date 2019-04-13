<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en" >
<c:set var="contextPath" value="${pageContext.request.contextPath}"/>
<head>
	<meta charset="UTF-8">
	<title>Register Success (JSP)</title>
	<link rel="shortcut icon" href="${contextPath}/resources/imgs/favicon.ico" type="image/x-icon"/>
	<link rel="icon" href="${contextPath}/resources/imgs/favicon.ico" type="image/x-icon"/>
	<link rel="stylesheet" href="${contextPath}/resources/css/style.css">
</head>
<body>
	<div class="page">
		<div class="form">
			<form:form id="register-success-form-id" class="register-form" modelAttribute="registerVO" method="post" action="${contextPath}/registered.htm">
				<p align="center">Registering of the User is successful</p>
				<p align="left">login: ${login}</p>
				<p align="left">email: ${email}</p>
				<form:button id="register-success-form-ok-id">OK</form:button>
			</form:form>
		</div>
	</div>
</body>
</html>
