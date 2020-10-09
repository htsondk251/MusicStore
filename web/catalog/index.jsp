<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:import url="/includes/header.jsp"/>
<c:import url="/includes/left.jsp"/>

<section>
<h1>The Fresh Corn Records</h1>
<table>	
	<tr>
		<th>Artist</th>
		<th>Album</th>
		<th>&nbsp;</th>
	</tr>
	<c:forEach var="product" items="${products}">
	<tr>
		<td><c:out value="${product.artistName}"/></td>
		<td>${product.albumName}</td>
		<td><form action="product/${product.code}" method="get">
			<input type="submit" value="View">
		    </form>
	</tr>
	</c:forEach>
</table>
</section>
<c:import url="/includes/right.jsp"/>
<c:import url="/includes/footer.jsp"/>