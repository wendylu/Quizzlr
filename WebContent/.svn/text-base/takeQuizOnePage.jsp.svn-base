<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%@page import="quizzlr.quiz.*"%>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Taking Quiz</title>
</head>
<body>
<%@include file = "includes/header.jsp" %>
<div id="main">
	<div id="contents">
		<div class="content" id="question">
			<form action="GradeOnePageServlet" method="post">
			<%
			GeneratedQuiz generatedQuiz = (GeneratedQuiz)session.getAttribute("generatedQuiz");
			int questionNum = 1;
			for (QuizQuestion q : generatedQuiz.getQuestions()) {
			%>
				<h1>Question <%=questionNum %>.</h1>
				<%=q.toHTML() %>
			<%
				questionNum++;
			}
			%>
				<input type="submit" value="submit">
			</form>
		</div>
	</div>
	<div id="side">
		<%@include file="includes/sidebar.jsp" %>
	</div>
</div>
<%@include file = "includes/footer.jsp" %>