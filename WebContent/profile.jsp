<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@page import="quizzlr.quiz.QuizUtil"%>
<%@page import="quizzlr.quiz.IAmTheGreatestAchievementType"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<%@ page import="javax.servlet.*,java.util.*,quizzlr.backend.*" %>


<title>Profile Page</title>
</head>
<body>
<%@include file = "includes/header.jsp" %>

<div id="main">
	<div id="contents">
		<div class="content" id="profile">
			<h1><%=user.getUsername() %>'s Profile</h1>
		 	<h3>Quiz History</h3>
		 	<%= QuizUtil.makeUserResultTable(user.getQuizHistory()) %>
            
            <h3>Created Quizzes:</h3>
			<ul>
			<%
				Set<Quiz> createdQuizzes = user.getQuizzesCreated();
				for (Quiz q : createdQuizzes) {
			%>
				<li><a href="quiz.jsp?id=<%=Integer.toString(q.getQuizID())%>"><%=q.getTitle()%></a></li>
			<%
				}
			%>
			</ul>
			<h3>Quizzes you've started creating:</h3>
			<ul>
			<%
				Set<Quiz> notFinishedQuizzes = user.getHalfCreatedQuizzes();
				for (Quiz q : notFinishedQuizzes) {
			%>
				<li><a href="ResumeQuizCreationServlet?id=<%=Integer.toString(q.getQuizID())%>"><%=q.getTitle()%></a></li>
			<%
				}
			%>
			</ul>
            <h3>Achievements</h3>
            <%
           	Set<Achievement> achievements = user.getAchievements();
           	for (Achievement a : achievements) {
           		out.println("<img src=\"pictures/achievements/" + a.getAchievementType().getImageIcon() + "\" title=\"" + a.getAchievementType().getDescription() + "\"/>");
           	}
            %>


		</div>
	</div>
	<div id="side">
		<%@include file="includes/sidebar.jsp" %>
	</div>
</div>
<%@include file = "includes/footer.jsp" %>