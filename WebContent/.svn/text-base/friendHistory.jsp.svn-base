<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ page import="javax.servlet.*,java.util.*,quizzlr.backend.*,quizzlr.quiz.*"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">

<title>Your Friends' Quiz History</title>
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

List<QuizResult> quizHistory = user.getFriendQuizHistory();	
%>

<h1>Your Friends' Quiz History:</h1>

<% 
out.println(QuizUtil.makeQuizTakenTable(quizHistory));
/*
for (QuizResult r : quizHistory) {
	String quizLink = "<a href=\"quiz.jsp?id=" + Integer.toString(r.getQuiz().getQuizID()) + "\">" + r.getQuiz().getTitle() + "</a>";
	out.println("<li>" + "<a href=userPage.jsp" + "?id=" + r.getUser().getUserID() + ">" + r.getUser().getUsername() + "</a>" + " took <b> <font color = \"blue\">" + quizLink + "</font> </b>" + "    <b>Score</b>: " + r.getScore() + "    " + r.getPercentCorrect() + "%     " + r.getTimestamp() + "</li>");
}
*/
%>
</div>
</div>
<div id="side"><%@include file="includes/sidebar.jsp"%>
</div>
</div>
<%@include file="includes/footer.jsp"%>
</body>
</html>