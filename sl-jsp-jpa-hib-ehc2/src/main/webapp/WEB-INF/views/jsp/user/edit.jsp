<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
<c:set var="contextPath" value="${pageContext.request.contextPath}"/>
<head>
	<meta charset="UTF-8">
	<title>Editing of User Info (JSP)</title>
	<link rel="shortcut icon" href="${contextPath}/resources/imgs/favicon.ico" type="image/x-icon"/>
	<link rel="icon" href="${contextPath}/resources/imgs/favicon.ico" type="image/x-icon"/>
	<link rel="stylesheet" href="${contextPath}/resources/css/style.css">
</head>
<body>
	<div class="page">
		<div class="form">
			<form:form id="edit-form-id" class="edit-form" modelAttribute="registerVO" method="post" action="${contextPath}/user/edit.htm">
 				<form:input id="login-id" maxlength="255" path="login" placeholder="user login" readonly="true"/>
 				<form:input id="email-id" maxlength="255" path="email" placeholder="email" readonly="true"/>
 				<form:input id="name-id" maxlength="255" path="name" placeholder="user name"/>
				<form:password id="password-id" maxlength="32" path="password" placeholder="password"/>
				<form:password id="password-confirm-id" maxlength="32" path="passwordConfirm" placeholder="confirm password"/>
				<p class="message">Admin <form:checkbox id="is-admin-id" path="isAdmin" placeholder="admin"/></p>
				<form:button id="edit-form-create-id">Save</form:button>
			</form:form>
		</div>
	</div>
</body>
</html>
