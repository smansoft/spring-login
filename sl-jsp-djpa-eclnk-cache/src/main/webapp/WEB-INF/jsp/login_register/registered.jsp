<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<div class="page-login">
	<div class="form-login">
		<form:form id="register-success-form-id" class="register-form" modelAttribute="registerVO" method="post" action="registered.htm">
			<p align="center">Registering of the User is successful</p>
			<p align="left">login: ${login}</p>
			<p align="left">email: ${email}</p>
			<form:button id="register-success-form-ok-id">OK</form:button>
		</form:form>
	</div>
</div>
