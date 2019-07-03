<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<div class="page">
	<div class="form">
		<div class="container">
			<table class="responsive-table">
				<caption>User Info:</caption>
				<thead>
				  <tr>
					<th width="width-30p" scope="col">User Filed Name</th>
					<th width="width-70p" scope="col">User Field Value</th>
				  </tr>
				</thead>
				<tbody>
				  <tr>
					<th scope="row">Common User Info</th>
					<td data-title="User Field Value">${userVO}</td>
				  </tr>
				  <tr>
					<th scope="row">Login</th>
					<td data-title="User Field Value">${userVO.login}</td>
				  </tr>
				  <tr>
					<th scope="row">Email</th>
					<td data-title="User Field Value">${userVO.email}</td>
				  </tr>
				  <tr>
					<th scope="row">Name</th>
					<td data-title="User Field Value">${userVO.name}</td>
				  </tr>
				  <tr>
					<th scope="row">Address</th>
					<td data-title="User Field Value">${userVO.address}</td>
				  </tr>
				  <tr>
					<th scope="row">Sex</th>
					<td data-title="User Field Value">${userVO.sex}</td>
				  </tr>
				  <tr>
					<th scope="row">Enabled</th>
					<td data-title="User Field Value">${userVO.enabled}</td>
				  </tr>
				  <tr>
					<th scope="row">Roles</th>
					<td data-title="User Field Value"><c:forEach items="${userVO.userRoles}" var="userRole"><div>${userRole}</div></c:forEach></td>
				  </tr>
				</tbody>
				<tfoot>
				</tfoot>
			</table>
		</div>
	</div>
</div>





