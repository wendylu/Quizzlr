<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@ page import="quizzlr.backend.*" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>

<%
if (request.getParameter("recipient") == null) {
	RequestDispatcher dispatch = request.getRequestDispatcher("loginHome.jsp");
	dispatch.forward(request, response);
	return;
}
%>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Note Sent</title>
</head>
<body>
<%@include file = "includes/header.jsp" %>
<div id="main">
	<div id="contents">
		<div class="content" id="faq">
<%
int friendIDMain = Integer.parseInt(request.getParameter("recipient"));
String friendNameMain = User.getUserFromID(friendIDMain).getUsername();
String messageContent = request.getParameter("content");
messageContent = messageContent.replaceAll("&", "&amp");
messageContent = messageContent.replaceAll("<", "&lt;");
messageContent = messageContent.replaceAll(">", "&gt;");
%>
<p> You have sent the following message to <%= friendNameMain %>:<BR>
<% out.println(messageContent); %>
 </p>

<BR> <BR>

<% 
out.println("<a href=userPage.jsp" + "?id="
							+ friendIDMain + ">" + friendNameMain + "'s page");
%>
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