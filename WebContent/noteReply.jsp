<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
     <%@ page import="javax.servlet.*,quizzlr.backend.*" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Message Reply</title>
</head>
<body>
<%@include file="includes/header.jsp"%>
<div id="main">
<div id="contents">
<div class="content" id="faq">
<%
if (request.getParameter("messageID") == null) {
	RequestDispatcher dispatch = request.getRequestDispatcher("loginHome.jsp");
	dispatch.forward(request, response);
	return;
}
%>

<% 
Message origMessage = Message.getMessageFromID(Integer.parseInt(request.getParameter("messageID")));
out.println("<h1> Message Reply </h1> <BR>");
out.println("<b>" + origMessage.getFromUser().getUsername() + "</b>" + " wrote: " + origMessage.getMessageContent() + "<BR><br>");
%>


<BR>
<form action="SendMessage" method="post">
<input name="type" type ="hidden" value="note" />
<input name=recipient type ="hidden" value=<%= origMessage.getFromUser().getUserID() %> />
Send a reply:<BR>
<textarea cols="40" rows="20" name="content" placeholder="Enter your reply here.">
</textarea> <BR>
<input type="submit" value="Send" />
</form>

</div>
</div>
<div id="side"><%@include file="includes/sidebar.jsp"%>
</div>
</div>
<%@include file="includes/footer.jsp"%>
</body>
</html>