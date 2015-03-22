<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@page import="quizzlr.quiz.QuizUtil"%>
<html>
<head>
<%@page import="quizzlr.backend.*"%>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Tagged quizzes</title>
</head>
<body>
<%@include file = "includes/header.jsp" %>
<div id="main">
	<div id="contents">
		<div class="content" id="error">
			<% Tag tag = Tag.getTagFromID(Integer.decode(request.getParameter("id"))); %>

			<h1>Quizzes with tag "<%=tag.getTitle() %>"</h1>
			<ul>
				<%
				Set<Quiz> quizzes = tag.getQuizzes();
				for (Quiz quiz : quizzes) {
					out.println("<li><a href='quiz.jsp?id=" + quiz.getQuizID() + "'>" + quiz.getTitle() + "</a></li>");
				}
				%>
			</ul>
		</div>
	</div>
	<div id="side">
		<%@include file="includes/sidebar.jsp" %>
	</div>
</div>
<%@include file = "includes/footer.jsp" %>
