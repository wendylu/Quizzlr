<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ page import="javax.servlet.*,java.util.*,quizzlr.backend.*"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Challenge Your Friends</title>
</head>
<body>
<%@include file="includes/header.jsp"%>
<div id="main">
<div id="contents">
<div class="content" id="faq">
<%
	if (user == null)
	{	
		RequestDispatcher dispatch = request.getRequestDispatcher("/");
		dispatch.forward(request, response);
		return;
	}
	String idStr = request.getParameter("id");
	if (idStr == null) {
		RequestDispatcher dispatch = request.getRequestDispatcher("/");
		dispatch.forward(request, response);
		return;
	}
	Quiz quiz = Quiz.getQuizFromID(Integer.parseInt(idStr));
	if (quiz == null) {
		RequestDispatcher dispatch = request.getRequestDispatcher("quizNotFound.jsp");
		dispatch.forward(request, response);
		return;
	}
%>

<h1>Choose friends to challenge on <%= quiz.getTitle() %></h1>


<%
	Set<User> allFriends1 = user.getFriends();
	if (allFriends1.size() > 0) {
		out.println("<form action=\"ChallengeServlet\" method=\"post\">"
				+ "<input name=\"type\" type=\"hidden\" value=\"challenge\" />"
				+ "<input name=\"quizID\" type=\"hidden\" value=\"" + quiz.getQuizID() + "\"/>" 
				);
		Iterator<User> iterator= allFriends1.iterator();
		while (iterator.hasNext()) {
			User friend = iterator.next();
			String friendName = friend.getUsername();
			int friendID = friend.getUserID();
			out.println("<input type = \"checkbox\" name=\"" + friendID + "\" value=\"" +friendID + "\">" + friendName + "<BR>");
		}
		out.println("<BR> <input type=\"submit\" value=\"Challenge!\" /> </form>");
	} else {
		out.println("<p>You have no friends. Get some</p>");
	}
	%> <br>

</div>
</div>
<div id="side"><%@include file="includes/sidebar.jsp"%>
</div>
</div>
<%@include file="includes/footer.jsp"%>
</body>
</html>