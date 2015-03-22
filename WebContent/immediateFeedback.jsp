<%@page import="quizzlr.backend.GeneratedQuiz.QuestionResult"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@ page import="javax.servlet.*,java.util.*,quizzlr.backend.*" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Quiz progress so far</title>
</head>
<body>
    <%@include file = "includes/header.jsp" %>
    <div id="main">
        <div id="contents">
            <div class="content">
                <%
                GeneratedQuiz generatedQuiz = (GeneratedQuiz)request.getAttribute("generatedQuiz");
                QuestionResult feedback = (QuestionResult)request.getAttribute("feedback");
                Integer nextQuestionNum = (Integer)request.getAttribute("nextQuestionNum");
                Integer totalScore = (Integer)request.getAttribute("totalScore");
                %>
                <h1><%= generatedQuiz.getTemplate().getTitle() %></h1>
                <h2>Question <%= (nextQuestionNum-1) %></h2>
                <p>You got <%= feedback.Score %> out of <%= feedback.MaxScore %> points</p>
                <h3>Quiz score so far: <%= totalScore %></h3>
                <form action="takequiz.jsp" method="post">
                    <input type="hidden" name="q" value="<%= nextQuestionNum %>"><br />
                    <input type="submit" value="Continue">
                </form>
            </div>
        </div>
    </div>
<%@include file = "includes/footer.jsp" %>