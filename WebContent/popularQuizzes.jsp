<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ page import="javax.servlet.*,java.util.*,quizzlr.backend.*"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">

<title>Popular Quizzes</title>
</head>
<body>
	<%@include file="includes/header.jsp"%>
	<div id="main">
		<div id="contents">
			<div class="content" id="faq">
				<h1>List of Popular Quizzes</h1>
				<ul>
				<% 
				List<Quiz> popularQuizzes = Quiz.getPopularQuizzes();
				for (Quiz r : popularQuizzes) {
					String quizLink = "<a href=\"quiz.jsp?id=" + Integer.toString(r.getQuizID()) + "\">" + r.getTitle() + "</a>";
					out.println("<li>" + "<b> <font color = \"blue\">" + quizLink + "</font> </b>");
				}
				%>
				</ul>
			</div>
		</div>
		<div id="side"><%@include file="includes/sidebar.jsp"%>
		</div>
	</div>
	<%@include file="includes/footer.jsp"%>
</body>
</html>