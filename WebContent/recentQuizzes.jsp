<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Recently created quizzes</title>
</head>
<body>
	<%@include file="includes/header.jsp"%>
	<div id="main">
		<div id="contents">
			<div class="content">
				<h1>Recently Created Quizzes:</h1>
				<ul>
				<%
					List<Quiz> quizzes = Quiz.getAllQuizzes();
					for (Quiz q : quizzes) {
				%>
						<li><a href="quiz.jsp?id=<%=Integer.toString(q.getQuizID())%>"><%=q.getTitle()%></a></li>
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