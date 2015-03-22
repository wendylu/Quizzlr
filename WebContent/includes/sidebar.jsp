    	  	<%@ page import="java.util.*,quizzlr.backend.*, java.sql.*" %>
    	   	 <%
   		User curUser = ((User) session.getAttribute("User"));
    	%>
    	<div class="sidebar">
			<h2>Categories:</h2>
			<ul>
			<li><a href="allcats.jsp">All</a></li>
			<%
				Set<Category> sidebarCategories = Category.getAllCategories();
			for (Category category : sidebarCategories) {
					out.println("<li>" + "<a href='category.jsp" + "?id="
							+ category.getCategoryID() + "'>" + category.getTitle() + "</a> </li>");
				}
			%>
			</ul>
		</div>
		<%
		if (curUser != null) { %>
		<div class="sidebar" id="userOptions">
			<h2>User Options:</h2>
			<ul>
				<li><a href="createQuiz.jsp">Create Quiz</a></li>
				<li><a href="profile.jsp">Profile</a></li>
				<li><a href="inbox.jsp">Inbox</a></li>
				<li><a href="history.jsp">History</a></li>
				<li> <a href="achieve.jsp">Achievements</a> </li>
				<%if(curUser.getIsAdmin()){ %><li><a href="admin.jsp">Admin</a></li><%} %>

				<li><a href="LogoutServlet">Logout</a></li>
			</ul>
		</div>
		<div class="sidebar">
			<h2>All Friends</h2>
			<ul>
				<%
					Set<User> allFriends = curUser.getFriends();
					if (allFriends != null) {
						Iterator<User> iterator = allFriends.iterator();
						while (iterator.hasNext()) {
							User friend = iterator.next();
							String friendName = friend.getUsername();
							int friendID = friend.getUserID();
							out.println("<li>" + "<a href='userPage.jsp" + "?id="
									+ friendID + "'>" + friendName + "</a> </li>");
						}

					}
				%>
			</ul>
		</div>
		<div class="sidebar" id="achievements">
		<center><h2>Achievements Earned:</h2></center>
			<%
				Set<Achievement> sideAchievements = curUser.getAchievements();
					for (Achievement a : sideAchievements) {
						out.println("<img src=\"pictures/achievements/"
								+ a.getAchievementType().getImageIcon()
								+ "\" title=\""
								+ a.getAchievementType().getDescription() + "\"/>");
					}
			%>
		</div>
				<%} else { %>
		<div class="sidebar" id="makeact!">
		<h3>Register <a href="#" class="create">Here</a>!</h3>
		Have the true Quizzlr Experience!
		</div>		
				<%} %>
		<div class="sidebar" id="twitter">
			<script charset="utf-8" src="http://widgets.twimg.com/j/2/widget.js"></script>
			<script>
			new TWTR.Widget({
			  version: 2,
			  type: 'profile',
			  rpp: 5,
			  interval: 30000,
			  width: 'auto',
			  height: 300,
			  theme: {
			    shell: {
			      background: '#568bd6',
			      color: '#000000'
			    },
			    tweets: {
			      background: '#ffffff',
			      color: '#000000',
			      links: '#000000'
			    }
			  },
			  features: {
			    scrollbar: false,
			    loop: false,
			    live: true,
			    behavior: 'all'
			  }
			}).render().setUser('Quizzlr').start();
			</script>
		</div>