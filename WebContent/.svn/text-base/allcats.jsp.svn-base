<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@page import="quizzlr.quiz.QuizUtil"%>
<html>
<head>
<%@page import="quizzlr.backend.*"%>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Category</title>
</head>
<body>
<%@include file = "includes/header.jsp" %>
<div id="main">
	<div id="contents">
		<div class="content" id="error">
			<h1>All Quizzes</h1>
			<br />
			
			<ul>
			<%
			Set<Quiz> quizzes = Quiz.matchingDescriptions("");
			for (Quiz q : quizzes) {
				out.println("<li> <b> <font color = \"blue\"> <a href=\"quiz.jsp?id=" + Integer.toString(q.getQuizID()) + "\">" + q.getTitle() + "</a> </font> </b> <br />" + q.getDescription());
			}
			%>
			</ul>
		</div>
	</div>
	<div id="side">
		<%@include file="includes/sidebar.jsp" %>
	</div>
</div>
<script>
$('#quizHistoryBtn').click(function(e) {
	$('#quizHistoryBox').toggle();
	e.preventDefault();
});
</script>
<%@include file = "includes/footer.jsp" %>
