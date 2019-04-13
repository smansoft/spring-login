<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en" >
<c:set var="contextPath" value="${pageContext.request.contextPath}"/>
<head>
	<meta charset="UTF-8">
	<title>Users List (JSP)</title>
	<link rel="shortcut icon" href="${contextPath}/resources/imgs/favicon.ico" type="image/x-icon"/>
	<link rel="icon" href="${contextPath}/resources/imgs/favicon.ico" type="image/x-icon"/>
	<link rel="stylesheet" href="${contextPath}/resources/css/style.css">
</head>
<body>
	<div class="page_list">
		<div class="form_list">
			<table class="table table-hover">
	    		<thead>
		      		<tr>
				        <th>User</th>
				        <th>Login</th>
				        <th>Email</th>
				        <th>Name</th>
				        <th>Enabled</th>
				        <th>Roles</th>
					</tr>
		    	</thead>
	    		<tbody>
					<c:forEach items="${userVOs}" var="userVO">
						<tr>
							<td>${userVO}</td>
							<td>${userVO.login}</td>
							<td>${userVO.email}</td>
							<td>${userVO.name}</td>
							<td>${userVO.enabled}</td>
							<td><c:forEach items="${userVO.authorities}" var="authority">${authority},</c:forEach>
							</td>
						</tr>
					</c:forEach>
	    		</tbody>
	    	</table>
		</div>
	</div>
</body>
</html>
