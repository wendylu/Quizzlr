<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@ page import="javax.servlet.*,java.util.*,quizzlr.backend.*" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<%
	
	HttpSession userSession = request.getSession();
	User user = (User) userSession.getAttribute("User");
	String annID = request.getParameter("id");
	
	
	if (annID == null)
	{	
		RequestDispatcher dispatch = request.getRequestDispatcher("loginHome.jsp");
		dispatch.forward(request, response);
		return;
	}
	Announcement ann = Announcement.getAnnouncementByID(Integer.parseInt(annID));
%>
<title><%= ann.getTitle() %></title>
</head>
<body>


<h1> <%= ann.getTitle() %></h1>

<h2> Posted by: <%= ann.getLastEditor().getUsername() %> at <%= ann.getLastEditTime() %></h2>
<p> <%= ann.getContent() %></p>

<BR>

<% 
if (user != null) {
	out.println("<a href=index.jsp>" + "My User Home </a>");
}
%>
</body>
</html>