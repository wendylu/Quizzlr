<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ page import="javax.servlet.*,java.util.*,quizzlr.backend.*"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Friends' Created Quizzes</title>
</head>
<body>
<%@include file="includes/header.jsp"%>
<div id="main">
<div id="contents">
<div class="content" id="faq">
<%
	if (user == null)
	{
		RequestDispatcher dispatch = request.getRequestDispatcher("/");
		dispatch.forward(request, response);
		return;
	}

%> <%
			out.println("<h1> Your Friends' Created Quizes </h1>");
			List<Quiz> allCreated = new ArrayList<Quiz>();
			Set<User> allFriends1 = user.getFriends();
			for (User friend : allFriends1) {
				Set<Quiz> created = friend.getQuizzesCreated();
				for (Quiz q : created) {
					allCreated.add(q);
				}
			}
			Collections.sort(allCreated, new Comparator<Quiz>() {
				public int compare(Quiz q1, Quiz q2) {
					return q2.getLastUpdateTime().compareTo(q1.getLastUpdateTime());
				}
			});
			
			
			for (Quiz q: allCreated) {
				String quizLink = "<a href=\"quiz.jsp?id=" + Integer.toString(q.getQuizID()) + "\">" + q.getTitle() + "</a>";
				out.println("<li>" + "<a href=userPage.jsp" + "?id="
						+ q.getCreator().getUserID() + ">" + q.getCreator().getUsername() + "</a>" + " created <b> <font color = \"blue\">" + quizLink + "</font> </b>" + "     " + q.getLastUpdateTime() + "</li>");
			}
			
		
			%>
</div>
</div>
<div id="side"><%@include file="includes/sidebar.jsp"%>
</div>
</div>
<%@include file="includes/footer.jsp"%>
</body>
</html>