<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%@page import="quizzlr.quiz.*"%>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Practice mode?</title>
</head>
<body>
<%@include file = "includes/header.jsp" %>
<div id="main">
	<div id="contents">
		<div class="content" id="question">
			<%
			Quiz quiz = Quiz.getQuizFromID(Integer.parseInt(request.getParameter("id")));
			%>

			<h1><%= quiz.getTitle() %></h1>
			Do you want to take this quiz in practice mode?
			<form action="GenerateQuizServlet" method="get">
				<input type="hidden" name="id" value="<%= quiz.getQuizID() %>" />
				<input type="submit" name="isPracticeMode" value="No" /> 
				<input type="submit" name="isPracticeMode" value="Yes" />
			</form>
		</div>
	</div>
	<div id="side">
		<%@include file="includes/sidebar.jsp" %>
	</div>
</div>
<%@include file = "includes/footer.jsp" %>