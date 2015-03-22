<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@ page import="javax.servlet.*,java.util.*,quizzlr.backend.*" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">

<title>Confirm Remove User</title>
</head>
<body>

<%@include file = "includes/header.jsp" %>
<div id="main">
	<div id="contents">
		<div class="content" id="faq">
		
<%
	String id = request.getParameter("id");
	if (user == null)
	{	
		RequestDispatcher dispatch = request.getRequestDispatcher("loginHome.jsp");
		dispatch.forward(request, response);
		return;
	} else if (user.getIsAdmin() == false || id == null) {
		RequestDispatcher dispatch = request.getRequestDispatcher("index.jsp");
		dispatch.forward(request, response);
		return;
	}	
%>
<h2>Are you sure you want to delete the user <%= User.getUserFromID(Integer.parseInt(id)).getUsername() %>?</h2>

<p> All account information will be lost. This cannot be undone. </p>
<%
out.print("<form action=\"RemoveConfirm\" method=\"post\">"
			+ "<input name=\"id\" type=\"hidden\" value=\"" + id + "\" />"
			+ "<input name=\"name\" type=\"hidden\" value=\"" + request.getParameter("name") + "\" />"
			+ "<input type=\"submit\" value=\"Remove User\" />" 
			+ "</form>    "
			);
out.println("<form action=\"userPage.jsp\" method=\"post\">"
		+ "<input name=\"id\" type=\"hidden\" value=\"" + id + "\" />"
		+ "<input type=\"submit\" value=\"Cancel\" />" 
		+ "</form>"
		);
%>

</div>
	</div>
	<div id="side">
		<%@include file="includes/sidebar.jsp" %>
	</div>
</div>
<%@include file = "includes/footer.jsp" %>
</body>
</html>