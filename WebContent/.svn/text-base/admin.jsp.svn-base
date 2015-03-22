<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%@include file = "includes/loginCheck.jsp" %>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Admin Home</title>
</head>
<body>
	<%@include file = "includes/header.jsp" %>
	<div id="main">
		<div id="contents">
			<div class="content" id="adminAnnouncements">
			<center><h1>ADMIN DASHBOARD:</h1></center>
				
				<div id="manageAnnouncementsBox" class="subcontent">
				<center><h2>Manage announcements</h2></center>
				    <h3><a href="#" id="existingAnnouncementsBtn">Existing announcements</a></h3>
				    <table id="existingAnnouncementsBox" style="display: none">
				        <tr><th>Content</th><th>Last editor</th><th>Last edit</th><th>Delete</th></tr>
				        <%
				        List<Announcement> announcements = Announcement.getAllAnnouncements();
				        for (Announcement a : announcements) {
				        	out.print("<tr>");
				        	// Print out content
				        	out.println("<td>");
				        	out.println("<form action='UpdateAnnouncement' method='post'><input type='hidden' name='announcementID' value='" + a.getAnnouncementID() + "'/>");
				        	out.println("<input type='text' name='title' value='" + Util.escapeHTML(a.getTitle()) + "' /><br/>");
				        	out.println("<textarea name='content' rows=2 cols=40>" + Util.escapeHTML(a.getContent()) + "</textarea><br/>");
				        	out.println("<input type='submit' name='action' value='edit' /></form>");
				        	out.println("</td>");
				        	// Print out editor
				        	out.println("<td><a href='userPage.jsp?id=" + a.getLastEditor().getUserID() + "'>" + a.getLastEditor().getUsername() + "</a></td>");
				        	// Print out last edit time
				        	out.println("<td>" + Util.formatTime(a.getLastEditTime()) + "</td>");
				        	// Print out delete button
				        	out.println("<td><form action='UpdateAnnouncement' method='post'><input type='hidden' name='announcementID' value='" + a.getAnnouncementID() + "'/><input type='submit' name='action' value='delete'/></form>");
				        	out.println("</tr>");
				        }
				        %>
				    </table>
				    <h3><a href="#" id="newAnnouncementBtn">Post a new announcement</a></h3>
					<form action="CreateAnnouncement" method="post" id="newAnnouncementBox" style="display: none">
						Title: <input type="text" size="40" maxlength="40" name="title" />
						<BR>
						Message: <BR>
						<textarea cols="40" rows="20" name="content"></textarea> <BR>
						<input type="submit" value="Post" />
					</form>
				</div>
				<br />
                <div id="manageUsersBox" class="subcontent">
             		<center><h2>Manage users</h2></center>
                    You can ban/delete a user from the user profile page<br/>
                    <form action="UserSearchServlet" method="post"><input type="text" name="search" placeholder="Search for a User"></form>
                    <h3>Banned users</h3>
                    <%
                    Set<User> bannedUsers = User.getBannedUsers();
                    for (User bannedUser : bannedUsers) {
                    	out.println("<a href='userPage.jsp?id=" + bannedUser.getUserID() + "'>" + bannedUser.getUsername()+"</a>");
                    }
                    %>
                </div>
				<br />                
                <div id="manageQuizzesBox" class="subcontent">
                	<center><h2>Manage quizzes</h2></center>
                    You can delete a quiz from the quiz page<br/>
                    <form action="SearchServlet" method="post"><input type="text" name="search" placeholder="Search for a Quiz"></form>
                    <h3>Quizzes that have been flagged</h3>
                    <%
                    Set<Quiz.InappropriateSummary> inappropriateSummary = Quiz.getInappropriateReportSummary();
                    for (Quiz.InappropriateSummary s : inappropriateSummary) {
                    %>
                    	<h3><a href="#" onClick="$('#quiz_<%= s.quiz.getQuizID() %>_reports').toggle()"><%= s.quiz.getTitle() %></a></h3>
                    	<div id="quiz_<%= s.quiz.getQuizID() %>_reports" style="display: none">
                    		Reported <b><%= s.numReports %></b> times<br/>
                    		<a href="quiz.jsp?id=<%= s.quiz.getQuizID() %>">Go to quiz</a>
                    		<ul>
                    		<%
                    		Set<Quiz.InappropriateReport> reports = s.quiz.getInappropriateReports();
                    		for (Quiz.InappropriateReport r : reports) {
                    		%>
                    			<li><%= r.reason %> - <b><a href="userPage.jsp?id=<%= r.user.getUserID() %>"><%= r.user.getUsername() %></a></b></li>
                    		<%
                    		}
                    		%>
                    		</ul>
                    	</div>
                    <%
                    }
                    %>
                </div>

				<%
				Set<User> userMatches = User.matchingUsernames("");
				Set<Quiz> quizMatches = Quiz.matchingTitles("");
				%>
				<h2>Site Statistics</h2>
				Number of users: <%=userMatches.size() %><br />
				Number of quizzes: <%=quizMatches.size() %>
			</div>
		</div>
		<div id="side">
			<%@include file="includes/sidebar.jsp" %>
		</div>
	</div>
	<script>
	$('#manageAnnouncementsBtn').click(function (e) {
		$('#manageAnnouncementsBox').toggle();
		e.preventDefault();
	});

	$('#existingAnnouncementsBtn').click(function (e) {
        $('#existingAnnouncementsBox').toggle();
        e.preventDefault();
    });

	$('#newAnnouncementBtn').click(function (e) {
        $('#newAnnouncementBox').toggle();
        e.preventDefault();
    });

	$('#manageUsersBtn').click(function (e) {
        $('#manageUsersBox').toggle();
        e.preventDefault();
    });
	$('#manageQuizzesBtn').click(function (e) {
        $('#manageQuizzesBox').toggle();
        e.preventDefault();
    });
	</script>
<%@include file = "includes/footer.jsp" %>
