<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<div id='cssmenu'>
	<ul>
		<li><a href='<c:url value="/"/>'>Home</a></li>
		<li class='active'><a href='#'>User</a>
			<ul>
				<li><a href='<c:url value="/user/info.htm"/>'>Info</a></li>
				<li><a href='<c:url value="/user/edit.htm"/>'>Edit</a></li>
<sec:authorize access="!hasRole('ROOT_ADMIN')">
				<li><a href='<c:url value="/user/delete.htm"/>'>Delete</a></li>
</sec:authorize>
			</ul>
		</li>
<sec:authorize access="hasRole('ADMIN')">
		<li class='active'><a href='#'>Users</a>
			<ul>
				<li><a href='<c:url value="/users/list.htm"/>'>List</a></li>
			</ul>
		</li>
</sec:authorize>
		<li><a href='<c:url value="/about.htm"/>'>About</a></li>
		<li><a href='<c:url value="/logout.htm"/>'>Logout</a></li>
	</ul>
</div>
