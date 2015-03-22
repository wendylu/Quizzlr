<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Create Account</title>
</head>
<body>
<%@include file="includes/header.jsp"%>
<div id="main">
<div id="contents">
<div class="content" id="faq">
<%
if (request.getParameter("userName") == null) {
	RequestDispatcher dispatch = request.getRequestDispatcher("loginHome.jsp");
	dispatch.forward(request, response);
	return;
}
%>
<h1>Bummer! The Name <%= request.getParameter("userName") %> is Already in Use.</h1>
<p>Please try another username.</p>
<form action="createServlet" method="post">
User Name: <input type="text" name="userName" /><br />
Password: <input type="password" name="password" /><br />
Confirm Password: <input type="password" name="passwordConfirm" /><br />
<input type="submit" value="Create" />
</form>

</div>
</div>
<div id="side"><%@include file="includes/sidebar.jsp"%>
</div>
</div>
<%@include file="includes/footer.jsp"%>
</body>
</html>