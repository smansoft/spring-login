<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>

<div class="page">
	<div class="form">
		<p align="center">Error: <h1>${errorCode}</h1></p>
		<p align="center">${errorMsg}</p>
		<p align="center">${errorMsgExt}</p>
		<p align="center">${remoteUser}</p>		
<sec:authorize access="hasRole('ROOT_ADMIN')">
		<p align="center">hasRole('ROOT_ADMIN')</p>
</sec:authorize>
<sec:authorize access="hasRole('ADMIN')">
		<p align="center">hasRole('ADMIN')</p>
</sec:authorize>		
	</div>
</div>
