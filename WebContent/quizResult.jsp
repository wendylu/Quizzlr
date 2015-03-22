<%@page import="quizzlr.backend.GeneratedQuiz.QuestionResult"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@ page import="javax.servlet.*,java.util.*,quizzlr.backend.*" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Quiz result</title>
</head>
<body>
    <%@include file = "includes/header.jsp" %>
    <div id="main">
        <div id="contents">
            <div class="content">
                <%
                QuizResult result = (QuizResult)request.getAttribute("result");
                GeneratedQuiz gq = (GeneratedQuiz) request.getAttribute("generatedQuiz");
                %>
                <h1><%= result.getQuiz().getTitle() %></h1>
                <p>Elapsed time: <%= Util.formatDuration(result.getElapsedTime()) %>
                <p>Answers:
                <ul>
                	<%
                		for (int i = 0; i < gq.getQuestions().size(); i++) {
                			out.println("<li>Question " + (i+1) + ": " + gq.getQuestionAt(i).getCorrectAnswers() + " (score: " + gq.getQuestionAt(i).grade(gq.getResponseAt(i)) + ")");
                		}
                		%>
                </ul>
                <p>You got <%= result.getScore() %> points, or <%= result.getPercentCorrect() %>% of all possible points</p>
                <a href="quiz.jsp?id=<%= result.getQuiz().getQuizID() %>">Back to the quiz</a>
            </div>
        </div>
    </div>
<%@include file = "includes/footer.jsp" %>