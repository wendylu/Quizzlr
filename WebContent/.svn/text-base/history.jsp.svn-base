<%@page import="quizzlr.quiz.QuizUtil"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
     <%@ page import="javax.servlet.*,java.util.*,quizzlr.backend.*" %>
    
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Your Quiz History</title>
</head>
<body>
    <%@include file = "includes/header.jsp" %>
    <%
	if (user == null)
	{
		RequestDispatcher dispatch = request.getRequestDispatcher("/");
		dispatch.forward(request, response);
		return;
	}
	%>
    <div id="main">
        <div id="contents">
            <div class="content">
				<h1>Your Quiz History:</h1>
				<%= QuizUtil.makeUserResultTable(user.getQuizHistory()) %>
 			</div>
        </div>
        <div id="side">
			<%@include file="includes/sidebar.jsp" %>
		</div>
    </div>
<%@include file = "includes/footer.jsp" %>