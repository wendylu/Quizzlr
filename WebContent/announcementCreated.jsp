<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Announcement Created</title>
</head>
<body>

<%@include file = "includes/header.jsp" %>
<div id="main">
	<div id="contents">
		<div class="content" id="faq">
<h1>You posted the following announcement: </h1> <BR>
<h3><%= request.getParameter("title") %></h3>
<p><%= request.getParameter("content") %> </p>


<a href="admin.jsp">Administrator Home</a>
<br>

</div>
	</div>
	<div id="side">
		<%@include file="includes/sidebar.jsp" %>
	</div>
</div>
<%@include file = "includes/footer.jsp" %>

</body>
</html>