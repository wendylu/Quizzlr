<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@ page import="javax.servlet.*,java.util.*,quizzlr.backend.*" %>
    
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>User Removed</title>
</head>
<body>
<%@include file = "includes/header.jsp" %>
<div id="main">
	<div id="contents">
		<div class="content" id="faq">
<% 
		String idStr = request.getParameter("id");
		
		if (user == null || user.getIsAdmin() == false || idStr == null) {
			RequestDispatcher dispatch = request.getRequestDispatcher("index.jsp");
			dispatch.forward(request, response);
			return;
		}

%>
<h1> You have permanently removed the user <%= request.getParameter("name") %></h1>

<BR>

	</div>
	</div>
	<div id="side">
		<%@include file="includes/sidebar.jsp" %>
	</div>
</div>
<%@include file = "includes/footer.jsp" %>
</body>
</html>