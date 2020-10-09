<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:import url="/includes/header.jsp"/>
<c:import url="/includes/left.jsp"/>
<section>
	<h1>Download registration</h1>
	<p>Before you can download and listen to these sound files, you must<br>
	register with us by entering your name and email address below.</p>
	<form action="<c:url value='listen/register'/>" method="post">
		<label>Email</label>
		<input type="email" name="email" required><br>
		<label>First Name</label>
		<input type="text" name="firstName" required><br>
		<label>Last Name</label>
		<input type="text" name="lastName" required><br>
		<label>&nbsp;</label>
		<input type="submit" value="Register" id="submit">
	</form>
</section>
<c:import url="/includes/right_buttons.jsp"/>
<c:import url="/includes/footer.jsp"/>