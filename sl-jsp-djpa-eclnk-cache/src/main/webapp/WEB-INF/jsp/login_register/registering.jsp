<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<div class="page-login">
	<div class="form-login">
		<form:form id="register-form-id" class="register-form" modelAttribute="registerVO" method="post" action="register.htm">
			<div class="div-input-line">
				<div class="div-50p-left"><div class="div-left-in">User Login:</div></div>
				<div class="div-50p-right"><div class="div-right-in"><form:input id="login-id" maxlength="255" path="login" placeholder="user login"/></div></div>
			</div>
			<div class="div-input-line">
				<div class="div-50p-left"><div class="div-left-in">Email:</div></div>
				<div class="div-50p-right"><div class="div-right-in"><form:input id="email-id" maxlength="255" path="email" placeholder="email"/></div></div>
			</div>
			<div class="div-input-line">
				<div class="div-50p-left"><div class="div-left-in">User Name:</div></div>
				<div class="div-50p-right"><div class="div-right-in"><form:input id="name-id" maxlength="255" path="name" placeholder="user name"/></div></div>
			</div>
			<div class="div-input-line">
				<div class="div-50p-left"><div class="div-left-in">Address:</div></div>
				<div class="div-50p-right"><div class="div-right-in"><form:input id="address-id" maxlength="255" path="address" placeholder="address"/></div></div>
			</div>
			<div class="div-input-line">
				<div class="div-50p-left"><div class="div-left-in">Sex:</div></div>
				<div class="div-50p-right"><div class="div-right-in-p"><form:select id="sex-id" path="sex" items="${sexList}" multiple="false" placeholder="sex"/></div></div>
			</div>
			<div class="div-input-line">
				<div class="div-50p-left"><div class="div-left-in">Password:</div></div>
				<div class="div-50p-right"><div class="div-right-in"><form:password id="password-id" maxlength="32" path="password" placeholder="password"/></div></div>
			</div>
			<div class="div-input-line">
				<div class="div-50p-left"><div class="div-left-in">Confirm Password:</div></div>
				<div class="div-50p-right"><div class="div-right-in"><form:password id="password-confirm-id" maxlength="32" path="passwordConfirm" placeholder="confirm password"/></div></div>
			</div>
			<form:button id="register-form-create-id">Create</form:button>
			<p class="message">Already registered? <a href='<c:url value="/login.htm"/>'>Sign In</a></p>
		</form:form>
	</div>
</div>

