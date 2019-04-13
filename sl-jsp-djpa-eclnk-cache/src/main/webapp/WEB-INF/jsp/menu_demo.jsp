<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!doctype html>
<html lang=''>

<c:set var="contextPath" value="${pageContext.request.contextPath}"/>

<head>
   <title><tiles:insertAttribute name="title" ignore="true" /></title>

   <meta charset='utf-8'>
   <meta http-equiv="X-UA-Compatible" content="IE=edge">
   <meta name="viewport" content="width=device-width, initial-scale=1">

   <link rel="stylesheet" href="${contextPath}/resources/css/menu_demo/styles.css">

   <script src="http://code.jquery.com/jquery-latest.min.js" type="text/javascript"></script>
   <script src="${contextPath}/resources/js/menu_demo/script.js" type="text/javascript"></script>
<!--
   <title>CSS MenuMaker</title>
-->
</head>

<body>

<div id='cssmenu'>
<ul>
   <li><a href='#'>Home</a></li>
   <li class='active'><a href='#'>Products</a>
      <ul>
         <li><a href='#'>Product 1</a>
            <ul>
               <li><a href='#'>Sub Product</a></li>
               <li><a href='#'>Sub Product</a></li>
            </ul>
         </li>
         <li><a href='#'>Product 2</a>
            <ul>
               <li><a href='#'>Sub Product</a></li>
               <li><a href='#'>Sub Product</a></li>
            </ul>
         </li>
      </ul>
   </li>
   <li><a href='#'>About</a></li>
   <li><a href='#'>Contact</a></li>
</ul>
</div>

</body>

</html>
