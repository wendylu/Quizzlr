<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Top scores for today</title>
</head>
<body>
	<%@include file="includes/header.jsp"%>
	<div id="main">
		<div id="contents">
			<div class="content">
				<h1>Highest 100 scores in last day</h1>
				<ul>
				<%
					List<QuizResult> bestResultsInLastDay = QuizResult.getTopResults(1, 100);
					for (QuizResult r : bestResultsInLastDay) {
						%>
						<li><a href="userPage.jsp?id=<%= r.getUser().getUserID() %>"><%= r.getUser().getUsername() %></a> got a <%= r.getPercentCorrect() %>% on <a href="quiz.jsp?id=<%= r.getQuiz().getQuizID() %>"><%= r.getQuiz().getTitle() %></a></li>
						<%
					}
				%>			
				</ul>
			</div>
		</div>
		<div id="side"><%@include file="includes/sidebar.jsp"%>
	</div>
	<%@include file="includes/footer.jsp"%>
</body>