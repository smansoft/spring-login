<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>

<div id='cssmenu'>
	<ul>
		<li><a href='${contextPath}/'>Home</a></li>
		<li class='active'><a href='#'>User</a>
			<ul>
				<li><a href='${contextPath}/user/info.htm'>Info</a></li>
				<li><a href='${contextPath}/user/edit.htm'>Edit</a></li>
<sec:authorize access="!hasRole('ROOT_ADMIN')">
				<li><a href='${contextPath}/user/delete.htm'>Delete</a></li>
</sec:authorize>
			</ul>
		</li>
<sec:authorize access="hasRole('ADMIN')">
		<li class='active'><a href='#'>Users</a>
			<ul>
				<li><a href='${contextPath}/users/list.htm'>List</a></li>
			</ul>
		</li>
</sec:authorize>
		<li><a href='${contextPath}/about.htm'>About</a></li>
		<li><a href='${contextPath}/logout.htm'>Logout</a></li>
	</ul>
</div>
