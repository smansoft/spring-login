<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html lang="en" >

<c:set var="contextPath" value="${pageContext.request.contextPath}"/>

<head>
	<meta charset="UTF-8">
	<title><tiles:insertAttribute name="title" ignore="true" /></title>
	<meta name="viewport" content="width=device-width">

  	<script src="${contextPath}/resources/js/table_demo/modernizr.js" type="text/javascript"></script>

	<link rel="stylesheet" href="${contextPath}/resources/css/table_demo/normalize.css">

	<style>
      /* NOTE: The styles were added inline because Prefixfree needs access to your styles and they must be inlined if they are on local disk! */
      * {
  -webkit-box-sizing: border-box;
  -moz-box-sizing: border-box;
  box-sizing: border-box;
}
*:before, *:after {
  -webkit-box-sizing: border-box;
  -moz-box-sizing: border-box;
  box-sizing: border-box;
}

body {
  font-family: "Helvetica Neue", "Helvetica", "Roboto", "Arial", sans-serif;
  color: #5e5d52;
}

a {
  color: #337aa8;
}
a:hover, a:focus {
  color: #4b8ab2;
}

.container {
  margin: 5% 3%;
}
@media (min-width: 48em) {
  .container {
    margin: 2%;
  }
}
@media (min-width: 75em) {
  .container {
    margin: 2em auto;
    max-width: 75em;
  }
}

.responsive-table {
  width: 100%;
  margin-bottom: 1.5em;
}
@media (min-width: 48em) {
  .responsive-table {
    font-size: .9em;
  }
}
@media (min-width: 62em) {
  .responsive-table {
    font-size: 1em;
  }
}
.responsive-table thead {
  position: absolute;
  clip: rect(1px 1px 1px 1px);
  /* IE6, IE7 */
  clip: rect(1px, 1px, 1px, 1px);
  padding: 0;
  border: 0;
  height: 1px;
  width: 1px;
  overflow: hidden;
}
@media (min-width: 48em) {
  .responsive-table thead {
    position: relative;
    clip: auto;
    height: auto;
    width: auto;
    overflow: auto;
  }
}
.responsive-table thead th {
  background-color: #1d96b2;
  border: 1px solid #1d96b2;
  font-weight: normal;
  text-align: center;
  color: white;
}
.responsive-table thead th:first-of-type {
  text-align: left;
}
.responsive-table tbody,
.responsive-table tr,
.responsive-table th,
.responsive-table td {
  display: block;
  padding: 0;
  text-align: left;
  white-space: normal;
}
@media (min-width: 48em) {
  .responsive-table tr {
    display: table-row;
  }
}
.responsive-table th,
.responsive-table td {
  padding: .5em;
  vertical-align: middle;
}
@media (min-width: 30em) {
  .responsive-table th,
  .responsive-table td {
    padding: .75em .5em;
  }
}
@media (min-width: 48em) {
  .responsive-table th,
  .responsive-table td {
    display: table-cell;
    padding: .5em;
  }
}
@media (min-width: 62em) {
  .responsive-table th,
  .responsive-table td {
    padding: .75em .5em;
  }
}
@media (min-width: 75em) {
  .responsive-table th,
  .responsive-table td {
    padding: .75em;
  }
}
.responsive-table caption {
  margin-bottom: 1em;
  font-size: 1em;
  font-weight: bold;
  text-align: center;
}
@media (min-width: 48em) {
  .responsive-table caption {
    font-size: 1.5em;
  }
}
.responsive-table tfoot {
  font-size: .8em;
  font-style: italic;
}
@media (min-width: 62em) {
  .responsive-table tfoot {
    font-size: .9em;
  }
}
@media (min-width: 48em) {
  .responsive-table tbody {
    display: table-row-group;
  }
}
.responsive-table tbody tr {
  margin-bottom: 1em;
  border: 2px solid #1d96b2;
}
@media (min-width: 48em) {
  .responsive-table tbody tr {
    display: table-row;
    border-width: 1px;
  }
}
.responsive-table tbody tr:last-of-type {
  margin-bottom: 0;
}
@media (min-width: 48em) {
  .responsive-table tbody tr:nth-of-type(even) {
    background-color: rgba(94, 93, 82, 0.1);
  }
}
.responsive-table tbody th[scope="row"] {
  background-color: #1d96b2;
  color: white;
}
@media (min-width: 48em) {
  .responsive-table tbody th[scope="row"] {
    background-color: transparent;
    color: #5e5d52;
    text-align: left;
  }
}
.responsive-table tbody td {
  text-align: right;
}
@media (min-width: 30em) {
  .responsive-table tbody td {
    border-bottom: 1px solid #1d96b2;
  }
}
@media (min-width: 48em) {
  .responsive-table tbody td {
    text-align: center;
  }
}
.responsive-table tbody td[data-type=currency] {
  text-align: right;
}
.responsive-table tbody td[data-title]:before {
  content: attr(data-title);
  float: left;
  font-size: .8em;
  color: rgba(94, 93, 82, 0.75);
}
@media (min-width: 30em) {
  .responsive-table tbody td[data-title]:before {
    font-size: .9em;
  }
}
@media (min-width: 48em) {
  .responsive-table tbody td[data-title]:before {
    content: none;
  }
}

    </style>

    <script src="https://cdnjs.cloudflare.com/ajax/libs/prefixfree/1.0.7/prefixfree.min.js"></script>

</head>

<body>

  <div class="container">
  <table class="responsive-table">
    <caption>Top 10 Grossing Animated Films of All Time</caption>
    <thead>
      <tr>
        <th scope="col">Film Title</th>
        <th scope="col">Released</th>
        <th scope="col">Studio</th>
        <th scope="col">Worldwide Gross</th>
        <th scope="col">Domestic Gross</th>
        <th scope="col">Foreign Gross</th>
        <th scope="col">Budget</th>
      </tr>
    </thead>
    <tfoot>
      <tr>
        <td colspan="7">Sources: <a href="http://en.wikipedia.org/wiki/List_of_highest-grossing_animated_films" rel="external">Wikipedia</a> &amp; <a href="http://www.boxofficemojo.com/genres/chart/?id=animation.htm" rel="external">Box Office Mojo</a>. Data is current as of August 25, 2016.</td>
      </tr>
    </tfoot>
    <tbody>
      <tr>
        <th scope="row">Frozen</th>
        <td data-title="Released">2013</td>
        <td data-title="Studio">Disney</td>
        <td data-title="Worldwide Gross" data-type="currency">$1,287,000,000</td>
        <td data-title="Domestic Gross" data-type="currency">$400,738,009	</td>
        <td data-title="Foreign Gross" data-type="currency">$875,742,326</td>
        <td data-title="Budget" data-type="currency">$150,000,000</td>
      </tr>
      <tr>
        <th scope="row">Minions</th>
        <td data-title="Released">2015</td>
        <td data-title="Studio">Universal</td>
        <td data-title="Worldwide Gross" data-type="currency">$1,159,398,397</td>
        <td data-title="Domestic Gross" data-type="currency">$336,045,770</td>
        <td data-title="Foreign Gross" data-type="currency">$823,352,627</td>
        <td data-title="Budget" data-type="currency">$74,000,000</td>
      </tr>
      <tr>
        <th scope="row">Toy Story 3</th>
        <td data-title="Released">2010</td>
        <td data-title="Studio">Disney Pixar</td>
        <td data-title="Worldwide Gross" data-type="currency">$1,066,969,703</td>
        <td data-title="Domestic Gross" data-type="currency">$415,004,880</td>
        <td data-title="Foreign Gross" data-type="currency">$651,964,823</td>
        <td data-title="Budget" data-type="currency">$200,000,000</td>
      </tr>
      <tr>
        <th scope="row">Zootopia</th>
        <td data-title="Released">2016</td>
        <td data-title="Studio">Disney</td>
        <td data-title="Worldwide Gross" data-type="currency">$1,023,227,498</td>
        <td data-title="Domestic Gross" data-type="currency">$341,268,248</td>
        <td data-title="Foreign Gross" data-type="currency">$681,959,250</td>
        <td data-title="Budget" data-type="currency">$150,000,000</td>
      </tr>
      <tr>
        <th scope="row">Despicable Me 2</th>
        <td data-title="Released">2013</td>
        <td data-title="Studio">Universal</td>
        <td data-title="Worldwide Gross" data-type="currency">$970,761,885</td>
        <td data-title="Domestic Gross" data-type="currency">$368,061,265</td>
        <td data-title="Foreign Gross" data-type="currency">$602,700,620</td>
        <td data-title="Budget" data-type="currency">$76,000,000</td>
      </tr>
      <tr>
        <th scope="row">The Lion King</th>
        <td data-title="Released">1994</td>
        <td data-title="Studio">Disney</td>
        <td data-title="Worldwide Gross" data-type="currency">$987,483,777</td>
        <td data-title="Domestic Gross" data-type="currency">$422,783,777</td>
        <td data-title="Foreign Gross" data-type="currency">$564,700,000</td>
        <td data-title="Budget" data-type="currency">$45,000,000</td>
      </tr>
      <tr>
        <th scope="row">Finding Nemo</th>
        <td data-title="Released">2003</td>
        <td data-title="Studio">Pixar</td>
        <td data-title="Worldwide Gross" data-type="currency">$936,743,261</td>
        <td data-title="Domestic Gross" data-type="currency">$380,843,261</td>
        <td data-title="Foreign Gross" data-type="currency">$555,900,000</td>
        <td data-title="Budget" data-type="currency">$94,000,000</td>
      </tr>
      <tr>
        <th scope="row">Shrek 2</th>
        <td data-title="Released">2004</td>
        <td data-title="Studio">Dreamworks</td>
        <td data-title="Worldwide Gross" data-type="currency">$919,838,758</td>
        <td data-title="Domestic Gross" data-type="currency">$441,226,247</td>
        <td data-title="Foreign Gross" data-type="currency">$478,612,511</td>
        <td data-title="Budget" data-type="currency">$150,000,000</td>
      </tr>
      <tr>
        <th scope="row">Finding Dory</th>
        <td data-title="Released">2016</td>
        <td data-title="Studio">Disney Pixar</td>
        <td data-title="Worldwide Gross" data-type="currency">$916,221,557</td>
        <td data-title="Domestic Gross" data-type="currency">$478,714,390</td>
        <td data-title="Foreign Gross" data-type="currency">$437,507,167	</td>
        <td data-title="Budget" data-type="currency">$250,000,000</td>   
      </tr>
      <tr>
        <th scope="row">Ice Age: Dawn of the Dinosaurs</th>
        <td data-title="Released">2009</td>
        <td data-title="Studio">Fox</td>
        <td data-title="Worldwide Gross" data-type="currency">$886,686,817</td>
        <td data-title="Domestic Gross" data-type="currency">$196,573,705</td>
        <td data-title="Foreign Gross" data-type="currency">$690,113,112	</td>
        <td data-title="Budget" data-type="currency">$90,000,000</td>
      </tr>
    </tbody>
  </table>
</div>

  	<script src="${contextPath}/resources/js/jquery-3.3.1.min.js" type="text/javascript"></script>

</body>

</html>
