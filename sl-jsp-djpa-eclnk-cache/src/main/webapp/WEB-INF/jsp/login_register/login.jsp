<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<div class="page-login">
	<div class="form-login">
		<form:form id="login-form-id" class="login-form" modelAttribute="userVO" method="post" action="${contextPath}/login.htm">
			<div class="div-input-line">
				<div class="div-50p-left"><div class="div-left-in">User Login:</div></div>
				<div class="div-50p-right"><div class="div-right-in"><form:input id="login-id" maxlength="255" path="login" placeholder="user login"/></div></div>
			</div>
			<div class="div-input-line">
				<div class="div-50p-left"><div class="div-left-in">Password:</div></div>
				<div class="div-50p-right"><div class="div-right-in"><form:password id="password-id" maxlength="32" path="password" placeholder="password"/></div></div>
			</div>
			<form:button id="login-form-login-id">Login</form:button>
			<p class="message">Not registered? <a href="${contextPath}/register.htm">Create an account</a></p>
		</form:form>
	</div>
</div>
