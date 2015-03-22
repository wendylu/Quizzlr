<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Friend Request Pending</title>
</head>
<body>
<%@include file = "includes/header.jsp" %>
<div id="main">
	<div id="contents">
		<div class="content" id="faq">
<%
if (request.getParameter("recipient") == null) {
	RequestDispatcher dispatch = request.getRequestDispatcher("loginHome.jsp");
	dispatch.forward(request, response);
	return;
}
%>

<h1>You already have a pending friend request</h1>
<BR><BR>
<% 
int friendID1 = Integer.parseInt(request.getParameter("recipient"));
out.println("<a href=userPage.jsp" + "?id="
							+ friendID1 + ">" + "Return to user page </a>");
%>
<BR>
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