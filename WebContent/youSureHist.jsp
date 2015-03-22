<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@ page import="javax.servlet.*,java.util.*,quizzlr.backend.*" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">

<title>Confirm Clear Quiz History</title>

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
	}	
%>
			
<h2>Are you sure you want to clear all history for the quiz "<%= Quiz.getQuizFromID(Integer.parseInt(request.getParameter("id"))).getTitle() %>"?</h2>

<p> All quiz history will be removed. This cannot be undone. </p>
<%
out.print("<form action=\"ClearHistConfirm\" method=\"post\">"
			+ "<input name=\"id\" type=\"hidden\" value=\"" + request.getParameter("id") + "\" />"
			+ "<input type=\"submit\" value=\"Clear History\" />" 
			+ "</form>    "
			);
out.println("<form action=\"quiz.jsp\" method=\"get\">"
		+ "<input name=\"id\" type =\"hidden\" value=\"" + request.getParameter("id") + "\" />"
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