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
			<% Category curCategory = Category.getCategoryFromID(Integer.decode(request.getParameter("id"))); %>

			<h1><%=curCategory.getTitle() %></h1>
			<%= curCategory.getDescription() %>
			<br />
			
			<h3>Quizzes in <%=curCategory.getTitle() %></h3>
			<ul>
			<%
			Set<Quiz> quizzes = curCategory.getQuizzes();
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
