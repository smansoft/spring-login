<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<div class="page-login">
	<div class="form-login">
		<div class="header">Edit User Info:</div>
		<form:form id="edit-form-id" class="edit-form" modelAttribute="registerVO" method="post" action="edit.htm">
			<div class="div-input-line">
				<div class="div-50p-left"><div class="div-left-in">User Login:</div></div>
				<div class="div-50p-right"><div class="div-right-in"><form:input id="login-id" maxlength="255" path="login" placeholder="user login" readonly="true"/></div></div>
			</div>
			<div class="div-input-line">
				<div class="div-50p-left"><div class="div-left-in">Email:</div></div>
				<div class="div-50p-right"><div class="div-right-in"><form:input id="email-id" maxlength="255" path="email" placeholder="email" readonly="true"/></div></div>
			</div>
			<div class="div-input-line">
				<div class="div-50p-left"><div class="div-left-in">User Name:</div></div>
				<div class="div-50p-right"><div class="div-right-in"><form:input id="name-id" maxlength="255" path="name" placeholder="user name"/></div></div>
			</div>
			<div class="div-input-line">
				<div class="div-50p-left"><div class="div-left-in">Sex:</div></div>
				<div class="div-50p-right"><div class="div-right-in-p"><form:select id="sex-id" path="sex" items="${sexList}" multiple="false" placeholder="sex"/></div></div>
			</div>
			<div class="div-input-line">
				<div class="div-50p-left"><div class="div-left-in">Address:</div></div>
				<div class="div-50p-right"><div class="div-right-in"><form:input id="address-id" maxlength="255" path="address" placeholder="address"/></div></div>
			</div>
			<div class="div-input-line">
				<div class="div-50p-left"><div class="div-left-in">Password:</div></div>
				<div class="div-50p-right"><div class="div-right-in"><form:password id="password-id" maxlength="32" path="password" placeholder="password"/></div></div>
			</div>
			<div class="div-input-line">
				<div class="div-50p-left"><div class="div-left-in">Confirm Password:</div></div>
				<div class="div-50p-right"><div class="div-right-in"><form:password id="password-confirm-id" maxlength="32" path="passwordConfirm" placeholder="confirm password"/></div></div>
			</div>
			<div class="div-input-line">
				<div class="div-50p-left"><div class="div-left-in">User Roles:</div></div>
				<div class="div-50p-right"><div class="div-right-in-p">
					<c:if test="${isEditRoles}">
						<form:select id="user-roles-id" path="userRoles" items="${userRolesList}" 
						multiple="true" placeholder="user roles" size="${fn:length(userRolesList)}"
						disabled="${!isEditRoles}"/>
					</c:if>
					<c:if test="${!isEditRoles}">
						<div class="div-roles">
							<c:forEach items="${registerVO.userRoles}" var="userRole"><div>${userRole}</div></c:forEach>
						</div>
					</c:if>
				</div></div>
			</div>
			<div class="div-input-line div-input-line-button">
				<form:button id="edit-form-save-id">Save</form:button>
			</div>
		</form:form>
	</div>
</div>

