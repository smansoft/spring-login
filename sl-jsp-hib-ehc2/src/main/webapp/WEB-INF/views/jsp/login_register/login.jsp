<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en" >
<c:set var="contextPath" value="${pageContext.request.contextPath}"/>
<head>
	<meta charset="UTF-8">
	<title>Login (JSP)</title>	
	<link rel="shortcut icon" href="${contextPath}/resources/imgs/favicon.ico" type="image/x-icon"/>
	<link rel="icon" href="${contextPath}/resources/imgs/favicon.ico" type="image/x-icon"/>
	<link rel="stylesheet" href="${contextPath}/resources/css/style.css">
</head>
<body>
	<div class="page">
		<div class="form">
			<form:form id="login-form-id" class="login-form" modelAttribute="userVO" method="post" action="${contextPath}/login.htm">
 				<form:input id="login-id" maxlength="255" path="login" placeholder="user login"/>
				<form:password id="password-id" maxlength="32" path="password" placeholder="password"/>
				<form:button id="register-form-login-id">Login</form:button>
				<p class="message">Not registered? <a href="${contextPath}/register.htm">Create an account</a></p>
			</form:form>
		</div>
	</div>
</body>
</html>
