<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@ page import="javax.servlet.*,java.util.*,quizzlr.backend.*" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Welcome User</title>
</head>
<body>
<h1> Welcome to your Home Page</h1>
<hr>

<%
	HttpSession userSession = request.getSession();
	User user = (User) userSession.getAttribute("User");
	if (user == null)
	{
		RequestDispatcher dispatch = request.getRequestDispatcher("loginHome.jsp");
		dispatch.forward(request, response);
		return;
	}

%>

<%
	if (user.getIsAdmin() == true) {
		out.println("<a href=admin.jsp>" + "Administrator Home </a>");
	}
%>

<h2>Announcements</h2>
<%
List<Announcement> allAnn = Announcement.getAllAnnouncements();
for(Announcement ann : allAnn)
{
	int annID = ann.getAnnouncementID();
	out.println("<a href=announce.jsp" + "?id=" + annID + ">" + ann.getTitle() + " </a> <BR>");
}
%>

<h2>Quizzes:</h2>
<ul>
<%
Set<Quiz> quizzes = Quiz.matchingDescriptions("");
for(Quiz q : quizzes)
{
	if(q.getIsLive())
	{%>
	<li><a href="quiz.jsp?id=<%=Integer.toString(q.getQuizID())%>"><%=q.getTitle()%></a></li>
	<%}
}
%>
</ul>


<%
	out.println("<a href=inbox.jsp>" + "Inbox </a>");

%>

<%
List<QuizResult> friendsResults = new ArrayList<QuizResult>();
Set<User> allFriends = user.getFriends();

if (allFriends.size() > 0) {
	Iterator<User> iterator = allFriends.iterator();
	while (iterator.hasNext()) {
		User friend = iterator.next();
		String friendName = friend.getUsername();
		int friendID = friend.getUserID();
		if (friend.getQuizHistory() != null) {
			List<QuizResult> histSet = friend.getQuizHistory();
			for (QuizResult r : histSet) {
				Calendar calendar = Calendar.getInstance();
				java.util.Date now = calendar.getTime();
				java.sql.Timestamp currentTimestamp = new java.sql.Timestamp(now.getTime());
				if ((currentTimestamp.getTime() / 1000) - (r.getTimestamp().getTime() / 1000) < (24*3600)) {
					//if within the last day
					friendsResults.add(r);
				}
			}
		}
	}
}
%>
<h2>Friends' Recent Activities</h2>
<ul>
<% 
//display activities within the last day
for (QuizResult result: friendsResults) {
	out.println("<li>" + "<a href=userPage.jsp" + "?id="
		+ result.getUser().getUserID() + ">" + result.getUser().getUsername() + "</a>" + " took the quiz " + result.getQuiz().getTitle() + " and scored " + result.getScore() + "</li>");
}
%>
</ul>

<p>All Friends</p>
<ul>
	<%
		if (user != null) {
			//Set<User> allFriends = user.getFriends();
			if (allFriends.size() > 0) {
				Iterator<User> iterator = allFriends.iterator();
				while (iterator.hasNext()) {
					User friend = iterator.next();
					String friendName = friend.getUsername();
					int friendID = friend.getUserID();
					out.println("<li>" + "<a href=userPage.jsp" + "?id="
							+ friendID + ">" + friendName + "</a> </li>");
				}

			} else {
				out.println("You have no friends. Get some");
			}
		}
	%>
</ul>
<BR>
<BR>

Search for Other Users
<form action="UserSearchServlet" method="post">
User Name: <input type="text" name="searchTerm" /><br />
<input type="submit" value="Search" />
</form>

<BR>

<a href="/quizzlr">home</a>

<BR>
<BR>

<form action="LogoutServlet" method="post">
<input type="submit" value="Logout" />
</form>

</body>
</html>