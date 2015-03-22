<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Rate this quiz</title>
</head>
<body>
<%@include file = "includes/header.jsp" %>
<div id="main">
	<div id="contents">
		<div class="content">
			<%
			Quiz quiz = Quiz.getQuizFromID(Integer.parseInt(request.getParameter("id")));
			%>
			<h1>Rating quiz "<%= quiz.getTitle() %>"</h1>
			<form action="RateQuizServlet" method="post">
				<input type="hidden" name="quizID" value="<%= quiz.getQuizID() %>" />
				<label><input type="radio" name="rating" value="1" /><img src="pictures/like.png" style="vertical-align: middle"/> Like</label>
				<label><input type="radio" name="rating" value="1" /><img src="pictures/unlike.png" style="vertical-align: middle"/> Dislike</label> <br/>
				Your review: <br/>
				<textarea name="review" cols="40" rows="5"></textarea>
				<p><input type="submit" value="Rate" /></p>
			</form>
		</div>
	</div>
	<div id="side">
		<%@include file="includes/sidebar.jsp" %>
	</div>
</div>
<%@include file = "includes/footer.jsp" %>