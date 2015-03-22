<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@page import="quizzlr.quiz.QuizUtil"%>
<%@ page import="javax.servlet.*,java.util.*,quizzlr.backend.*" %>
<html>
<head>
<%@include file = "includes/loginCheck.jsp" %>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<%
if (request.getParameter("id") == null) {
	RequestDispatcher dispatch = request.getRequestDispatcher("/");
	dispatch.forward(request, response);
	return;
}
%>
<%User currentlyViewedUser = User.getUserFromID(Integer.parseInt(request.getParameter("id")));%>
<title><%= currentlyViewedUser.getUsername() %></title>
</head>
<body>
<%@include file = "includes/header.jsp" %>
<div id="main">
	<div id="contents" >
		<div class="content" id="friend">
		<h1><%= currentlyViewedUser.getUsername() %>'s page</h1>

		<%
		if (currentlyViewedUser.getIsAdmin() == true) {
			 out.println("<h4> Is a Site Admin </h4>");
		 }
		HttpSession userSession = request.getSession();
		if (user == null) return;
		if (user.isFriendOf(currentlyViewedUser)) {
			out.println("You are a friend.");
			out.println("<form action=\"RemoveFriend\" method=\"post\">"
					+ "<input name=\"friendID\" type=\"hidden\" value=\"" + currentlyViewedUser.getUserID() + "\" />"
					+ "<input type=\"submit\" value=\"Remove from friends\" />"
					+ "</form>"
					);


		} else if (!user.equals(currentlyViewedUser)) {
			out.println("<form action=\"SendMessage\" method=\"post\">"
					+ "<input name=\"type\" type=\"hidden\" value=\"friendRequest\" />"
					+ "<input name=\"recipient\" type=\"hidden\" value=\"" + currentlyViewedUser.getUserID() + "\" />"
					+ "<input type=\"submit\" value=\"Add as Friend\" />"
					+ "</form>"
					);
		}
		%>

		 <BR>

		 <%
		 if (user.getIsAdmin() == true) {
			 String banAction = "Ban";
			 if (currentlyViewedUser.getIsBanned()) {
				 banAction = "Unban";
			 }
			 out.println("<form action=\"banConfirm.jsp\" method=\"post\">"
						+ "<input name=\"id\" type=\"hidden\" value=\"" + currentlyViewedUser.getUserID() + "\" />"
						+ "<input name=\"action\" type=\"hidden\" value=\"" + banAction + "\" />"
						+ "<input type=\"submit\" value=\"" + banAction + " User\" />"
						+ "</form>"
						);
			 out.println("<form action=\"areYouSure.jsp\" method=\"post\">"
						+ "<input name=\"id\" type=\"hidden\" value=\"" + currentlyViewedUser.getUserID() + "\" />"
						+ "<input name=\"name\" type=\"hidden\" value=\"" + currentlyViewedUser.getUsername() + "\" />"
						+ "<input type=\"submit\" value=\"Remove User\" />"
						+ "</form>"
						);
			 out.println("    ");
			 if (currentlyViewedUser.getIsAdmin() == false) {
			 	out.println("<form action=\"MakeAdmin\" method=\"post\">"
						+ "<input name=\"id\" type=\"hidden\" value=\"" + currentlyViewedUser.getUserID() + "\" />"
						+ "<input type=\"submit\" value=\"Promote to Administrator\" />"
						+ "</form>"
						);
			 }
		 }


		 %>
		<form action="SendMessage" method="post">
		<input name="type" type ="hidden" value="note" />
		<input name=recipient type ="hidden" value=<%= currentlyViewedUser.getUserID() %> />
		<h3>Send a message:</h3>
		<textarea cols="40" rows="20" name="content" placeholder="Enter your message here."></textarea> <BR>
		<input type="submit" value="Send" />
		</form>

		<h3>Achievements</h3>
		<ul>
		<%
		if (user!= null) {
			Set<Achievement> achievements = currentlyViewedUser.getAchievements();
			for (Achievement a : achievements) {
                out.println("<img src=\"pictures/achievements/" + a.getAchievementType().getImageIcon() + "\" title=\"" + a.getAchievementType().getDescription() + "\"/>");
            }
		}
		%>
		</ul>

		<h3>Quiz History</h3>
		<%= QuizUtil.makeUserResultTable(currentlyViewedUser.getQuizHistory()) %>
        
        <h3>Created Quizzes:</h3>
				<ul>
				<%
					Set<Quiz> Quizzes = currentlyViewedUser.getQuizzesCreated();
					for (Quiz q : Quizzes) {
						if (q.getIsLive()) {
				%>
						<li><a href="quiz.jsp?id=<%=Integer.toString(q.getQuizID())%>"><%=q.getTitle()%></a></li>
						<%
							}
							}
						%>
				</ul>
		</div>
	</div>
	<div id="side">
		<%@include file="includes/sidebar.jsp" %>
	</div>
</div>
<%@include file = "includes/footer.jsp" %>

