<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:import url="/includes/header.html" />
<c:import url="/includes/left.jsp" />

<h1>The Fresh Corn Records Catalog</h1>
<c:forEach var="product" items="${products}">
	<h2>${product.getArtistName()}</h2>
	<p>${product.getAlbumName()}</p>
</c:forEach>



<c:import url="/includes/right.jsp" />
<c:import url="/includes/footer.jsp" />