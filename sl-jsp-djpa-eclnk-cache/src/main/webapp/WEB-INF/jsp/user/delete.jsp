<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<div class="page">
	<div class="form">
		<form:form id="delete-question-form-id" class="delete-form" modelAttribute="userVO" method="post" action="${contextPath}/user/delete.htm">
			<p align="left">User will be removed</p>
			<p align="left">login: ${login}</p>
			<p align="left">email: ${email}</p>
			<p align="left">Are you sure ? </p>
			<form:button id="delete-success-form-ok-id">OK</form:button>
			<form:button id="delete-success-form-cancel-id">Cancel</form:button>
		</form:form>
	</div>
</div>
