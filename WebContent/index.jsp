<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%@page import="quizzlr.backend.*,java.util.*,javax.servlet.http.Cookie,javax.servlet.*,quizzlr.quiz.*"%>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Quizzlr: Tests2.0</title>
</head>
<body>
<%@include file = "includes/header.jsp" %>
<div id="main">
	<div id="contents">
		<div class="content" id="intro">
			
			<center><h1>WELCOME TO QUIZZLR!</h1>
			<h2>Click on any quiz to get cracking!</h2></center>

			<h3>Announcements:</h3>
			<ul>
			<%
				List<Announcement> allAnn = Announcement.getAllAnnouncements();
				for (Announcement ann : allAnn) {
					int annID = ann.getAnnouncementID();
					out.println("<li><a href=\"#\" OnClick=$('#announce" + annID
							+ "').toggle(); >" + ann.getTitle() + " </a>");
			%>
				<div class="subcontent" id="announce<%=annID%>" style=display:none;>
					<%=ann.getContent()%><br />
					<em>Posted by: <a href="userPage.jsp?id=<%=ann.getLastEditor().getUserID()%>"><%=ann.getLastEditor().getUsername()%></a> at <%=Util.formatTime(ann.getLastEditTime())%></em>

				</div>
				<%
					out.println("</li>");
					}
				%>
			</ul>
		</div>
		<div class="content" id="quizzes">
			<div class="left-content" id="recently created">
				<h2>Recently created quizzes</h2>
				<ul>
				<%
					List<Quiz> quizzes = Quiz.getRecentlyCreated(10);
					for (Quiz q : quizzes) {
				%>
						<li><a href="quiz.jsp?id=<%=Integer.toString(q.getQuizID())%>"><%=q.getTitle()%></a></li>
						<%
					}
						%>
				</ul>
				<a href="recentQuizzes.jsp">More</a>
			</div>
			<div class="right-content" id="popular quizzes">
			<h2>Most popular quizzes</h2>
			<ul>
			<%
				List<Quiz> popularQuizzes = Quiz.getPopularQuizzes(10);

					for (Quiz r : popularQuizzes) {
						String quizLink = "<a href=\"quiz.jsp?id="
								+ Integer.toString(r.getQuizID()) + "\">"
								+ r.getTitle() + "</a>";
						out.println("<li>" + "<b> <font color = \"blue\">"
								+ quizLink + "</font> </b>");
					}
			%>
			</ul><a href="popularQuizzes.jsp">More</a>
			</div>
			
			 &nbsp;
		</div>
		<div class="content" id="recent quizzes">
			<h2>Highest scores in last day</h2>
			<ul>
			<%
				List<QuizResult> bestResultsInLastDay = QuizResult.getTopResults(1, 10);
				for (QuizResult r : bestResultsInLastDay) {
					%>
					<li><a href="userPage.jsp?id=<%= r.getUser().getUserID() %>"><%= r.getUser().getUsername() %></a> got a <%= r.getPercentCorrect() %>% on <a href="quiz.jsp?id=<%= r.getQuiz().getQuizID() %>"><%= r.getQuiz().getTitle() %></a></li>
					<%
				}
			%>			
			</ul>
			<a href="todaysTopScores.jsp">More</a>
		</div>
		<!--  ONLY SHOWS IF LOGGED IN -->
		<% if (user != null) { %>

		<div class="content" id="history">
			<h2> <%=user.getUsername()+ "'s recent quizzing history"%></h2>

			<h3>Created Quizzes:</h3>
				<ul>
				<%
					Set<Quiz> quizzesCreated = user.getQuizzesCreated();
					for (Quiz q : quizzesCreated) {
						if (q.getIsLive()) {	%>
						<li><a href="quiz.jsp?id=<%=Integer.toString(q.getQuizID())%>"><%=q.getTitle()%></a></li>
			<%	} }%>
				</ul>
			<h3>Quizzes Taken:</h3>
			<%
						List<QuizResult> quizHistory = user.getQuizHistory();
						int subsetSize = (quizHistory.size() > 5) ? 5 : quizHistory
								.size();
						List<QuizResult> quizHistorySubset = quizHistory.subList(0,
								subsetSize); //changed for size=1
						out.println(QuizUtil.makeUserResultTable(quizHistorySubset));
			%>
			</ul><a href="history.jsp">More</a><br />
		</div>
		
		<div class="content" id="friendActivity">
			<h2><%=user.getUsername()%>'s friends' activity</h2>
			<ul>
			<h4> Recently Taken Quizzes </h4>
			<%
						List<QuizResult> friendQuizHistory = user.getFriendQuizHistory();
						subsetSize = (friendQuizHistory.size() > 5) ? 5 : friendQuizHistory.size();
						List<QuizResult> friendQuizHistorySubset = friendQuizHistory.subList(0, subsetSize);
						out.println(QuizUtil.makeQuizTakenTable(friendQuizHistorySubset));
			%>
			<a href="friendHistory.jsp">More</a>
			</ul><ul>
	<%
				out.println("<h4> Recently Created Quizzes </h4>");
				List<Quiz> allCreated = new ArrayList<Quiz>();
				Set<User> allFriends = user.getFriends();
				for (User friend : allFriends) {
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

				int count2 = 0;
				for (Quiz q : allCreated) {
					if (count2 > 5)
						break;
					String quizLink = "<a href=\"quiz.jsp?id="
							+ Integer.toString(q.getQuizID()) + "\">"
							+ q.getTitle() + "</a>";
					out.println("<li>" + "<a href=userPage.jsp" + "?id="
							+ q.getCreator().getUserID() + ">"
							+ q.getCreator().getUsername() + "</a>"
							+ " created <b> <font color = \"blue\">"
							+ quizLink + "</font> </b>" + "     "
							+ Util.formatTime(q.getLastUpdateTime()) + "</li>");
					count2++;
				}
	%><a href="friendCreated.jsp">More</a>
</ul>

<ul>
	<%
			out.println("<h4> Recent Achievements </h4>");
			List<Achievement> allAch = new ArrayList<Achievement>();
			Set<User> allFriendsAchieve = user.getFriends();
			for (User friend : allFriendsAchieve) {
				Set<Achievement> achievements = friend.getAchievements();
				for (Achievement ach : achievements) {
					allAch.add(ach);
				}
			}
			Collections.sort(allAch, new Comparator<Achievement>() {
				public int compare(Achievement a1, Achievement a2) {
					return a2.getTimestamp().compareTo(a1.getTimestamp());
				}
			});
			
			Calendar calendar = Calendar.getInstance();
			java.util.Date now = calendar.getTime();
			java.sql.Timestamp currentTimestamp = new java.sql.Timestamp(now.getTime());
			for (Achievement ach : allAch) {
				if ((currentTimestamp.getTime() / 1000) - (ach.getTimestamp().getTime() / 1000) > (24*3600))
					break;
				
				out.println("<li>" + "<a href=userPage.jsp" + "?id="
						+ ach.getUser().getUserID() + ">"
						+ ach.getUser().getUsername() + "</a>"
						+ " earned <b> "
						+ ach.getAchievementType().getName() + " </b>" + "     "
						+ Util.formatTime(ach.getTimestamp()) + "</li>");
			}
	%>
</ul>

</div>
		<%
			}
		%>
		<!--  END OF USER ONLY AREA -->
		<div class="content" id="comments">
		<div id="fb-root"></div>
		<script>(function(d, s, id) {
		  var js, fjs = d.getElementsByTagName(s)[0];
		  if (d.getElementById(id)) return;
		  js = d.createElement(s); js.id = id;
		  js.src = "//connect.facebook.net/en_US/all.js#xfbml=1&appId=246287342125248";
		  fjs.parentNode.insertBefore(js, fjs);
		}(document, 'script', 'facebook-jssdk'));</script>
		<div class="fb-comments" data-href="http://quizzlr.info " data-num-posts="4" data-width="700"></div>
		</div>
	</div>
	<div id="side">
		<%@include file = "includes/sidebar.jsp" %>
	</div>
</div>
<%@include file = "includes/footer.jsp" %>