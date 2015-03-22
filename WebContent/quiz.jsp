<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@page import="quizzlr.quiz.QuizUtil"%>
<html>
<head>
<%@page import="quizzlr.backend.*"%>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Quiz Page</title>
</head>
<body>
<%@include file = "includes/header.jsp" %><div id="fb-root"></div>
<script>(function(d, s, id) {
  var js, fjs = d.getElementsByTagName(s)[0];
  if (d.getElementById(id)) return;
  js = d.createElement(s); js.id = id;
  js.src = "//connect.facebook.net/en_US/all.js#xfbml=1&appId=246287342125248";
  fjs.parentNode.insertBefore(js, fjs);
}(document, 'script', 'facebook-jssdk'));</script>
<script type="text/javascript" src="https://apis.google.com/js/plusone.js"></script>
    <script src="http://malsup.github.com/jquery.form.js"></script> 
    <script> 
        // wait for the DOM to be loaded 
        $(document).ready(function() { 
            // bind 'myForm' and provide a simple callback function 
            $('#challengeForm').ajaxForm(function() { 
                alert("Challenged!"); 
            });
            $('#rateForm').ajaxForm(function() { 
                alert("Rating Recorded!"); 
            }); 
            $('#reportForm').ajaxForm(function() { 
                alert("Quiz reported!"); 
            }); 
        }); 
    </script> 
<div id="main">
	<div id="contents">
		<div class="content" id="quizPage">
			<% Quiz curQuiz = Quiz.getQuizFromID(Integer.decode(request.getParameter("id"))); %>

			<h1><%=curQuiz.getTitle() %></h1>
			by <a href="userPage.jsp?id=<%= curQuiz.getCreator().getUserID() %>"><%= curQuiz.getCreator().getUsername() %></a>, created on <%= Util.formatTime(curQuiz.getLastUpdateTime()) %> <br />
			<%= curQuiz.getDescription() %>
			<br /><br />
			<b>Category: </b><a href="category.jsp?id=<%= curQuiz.getCategory().getCategoryID() %>"><%= curQuiz.getCategory().getTitle() %></a><br/>
			<b>Tags: </b>
			<%
			Set<Tag> tags = curQuiz.getTags();
			for (Tag tag : tags) {
				out.println("<a class='tag' href='tag.jsp?id=" + tag.getTagID() + "'>" + tag.getTitle() +"</a>");
			}
			%>
			<%
			if (user != null) {
				if (curQuiz.getIsPracticeModeEnabled()) {
			%>
					<div class="startButton"><a href="practiceModePrompt.jsp?id=<%=request.getParameter("id") %>">START QUIZ</a></div>
			<%
			}
				else {
			%>
					<div class="startButton"><a href="GenerateQuizServlet?id=<%=request.getParameter("id") %>">START QUIZ</a></div>
			<%
				}
			}%>
			<%	 if (user != null && (user.getIsAdmin() == true || user.equals(curQuiz.getCreator()))) { %>
			<div class="adminOptions" id="quizOptions">		
			Admin Options:
			<form action="editQuiz.jsp" method="post">
				<input name="id" type="hidden" value="<%= curQuiz.getQuizID() %>" />
				<input type="submit" value="Edit Quiz" />
			</form>
			<form action="youSureQuiz.jsp" method="post">
				<input name="id" type="hidden" value="<%= curQuiz.getQuizID() %>" />
				<input type="submit" value="Remove Quiz" />
			</form>
			<form action="youSureHist.jsp" method="post">
				<input name="id" type="hidden" value="<%= curQuiz.getQuizID() %>" />
				<input type="submit" value="Clear Quiz History" />
			</form>
			</div>
			<%
			}
			%>
			<h3>Top 10 scores</h3>
			<%
			List<QuizResult> topScores = curQuiz.getTopScores(10);
			out.println(QuizUtil.makeQuizTopScoresTable(topScores));
			%>
			<%
			if (user != null) {
				%>
			    <h4><a href="#" onClick="$('#challengeQuiz').toggle()">Challenge a Friend on this quiz</a></h4>

			    <div id="challengeQuiz" class="subcontent" style=display:none;>
			    <h2>Choose friends to challenge on <%= curQuiz.getTitle() %></h2>
					<%Set<User> allFriends1 = user.getFriends();
					if (allFriends1.size() > 0) {
						out.println("<form id=\"challengeForm\" action=\"ChallengeServlet\" method=\"post\">"
								+ "<input name=\"type\" type=\"hidden\" value=\"challenge\" />"
								+ "<input name=\"quizID\" type=\"hidden\" value=\"" + curQuiz.getQuizID() + "\"/>" 
								);
						Iterator<User> iterator= allFriends1.iterator();
						while (iterator.hasNext()) {
							User friend = iterator.next();
							String friendName = friend.getUsername();
							int friendID = friend.getUserID();
							out.println("<input type = \"checkbox\" name=\"" + friendID + "\" value=\"" +friendID + "\">" + friendName + "<BR>");
						}
						out.println("<BR> <input type=\"submit\" value=\"Challenge!\" /> </form>");
					} else {
						out.println("<p>You have no friends. Get some</p>");
					}
					%> <br></div>
					<h4><a href="#" onClick="$('#quizRate').toggle()">Rate this quiz</a></h4>
					<div id="quizRate" class="subcontent" style=display:none;>
						<h2>Rating quiz "<%= curQuiz.getTitle() %>"</h2>
						<form id="rateForm" action="RateQuizServlet" method="post">
							<input type="hidden" name="quizID" value="<%= curQuiz.getQuizID() %>" />
							<label><input type="radio" name="rating" value="1" /><img src="pictures/like.png" style="vertical-align: middle"/> Like</label>
							<label><input type="radio" name="rating" value="1" /><img src="pictures/unlike.png" style="vertical-align: middle"/> Dislike</label> <br/>
							<textarea name="review" cols="40" rows="5" placeholder="Your Review Here."></textarea>
							<p><input type="submit" value="Rate" /></p>
						</form>	
					</div>
			    <%
			}
			List<QuizResult> quizHistory = curQuiz.getQuizHistory();
			List<QuizRating> quizRatings = curQuiz.getRatings();
			int high = curQuiz.getHighScore();
			float average = 0;
			int totalTakers = 0;
			long timing = 0;
			for(QuizResult result : quizHistory)
			{
				average += result.getPercentCorrect();
				timing +=result.getElapsedTime();
				totalTakers++;
			}

			if (totalTakers > 0) {
			%>
			Average Score: <%=Float.toString(average/totalTakers)%>% &middot; Average Time: <%=Util.formatDuration(timing/(long)totalTakers)%> &middot; High Score: <%=Integer.toString(high) %>
			<%
			}
			else {
			%>
			No one has taken this quiz yet.
			<%
			}
			%>
			<br/><b>Quiz rating: </b> <%= curQuiz.getRating() %>
			
			<% 
			if (user != null) {
			%>
			
			<h3><span onmouseover="$(this).hide(); $('#wrong').show();">Your</span><span id="wrong" style=display:none;>You're</span> History</h3>
			
			<ul>
			<%
			List<QuizResult> yourHist = user.getQuizHistory();
			for (QuizResult qr : yourHist) {
				if (qr.getQuiz().equals(curQuiz)) {
					out.println("<li> <b> Score</b>: " + qr.getScore() + "    " + qr.getPercentCorrect() + "%    " + Util.formatTime(qr.getTimestamp()) + "</li>");
				}
			}
			%>
			</ul>
			<%
			}%>
			<h3><a href="#" id="quizHistoryBtn">Quiz History</a></h3>
			<div id="quizHistoryBox" style="display: none">
				<%= QuizUtil.makeQuizResultTable(quizHistory) %>
			</div>
			
			<h3><a href="#" id="quizReviewBtn">Quiz Reviews</a></h3>
			<div id="quizReviewBox" style="display: none">
				<%= QuizUtil.makeQuizReviewTable(quizRatings) %>
			</div>
			<% if (user != null) { %>
			<h4><a href="#" onClick="$('#quizReport').toggle()">Report this quiz</a></h4>
			<div id="quizReport" class="subcontent" style=display:none;>
				<h2>Report quiz "<%= curQuiz.getTitle() %>"</h2>
				<form id="reportForm" action="ReportQuizServlet" method="post">
					<input type="hidden" name="quizID" value="<%= curQuiz.getQuizID() %>" />
					<textarea name="reason" cols="40" rows="5" placeholder="Reason for reporting"></textarea>
					<p><input type="submit" value="Report" /></p>
				</form>	
			</div>
			<% } %>
			<div class="bullshit">
			<script src="//platform.linkedin.com/in.js" type="text/javascript"></script><script type="IN/Share" data-counter="right"></script>
			<script type="text/javascript">(function() {var s = document.createElement('SCRIPT'), s1 = document.getElementsByTagName('SCRIPT')[0];s.type = 'text/javascript';s.async = true;
			s.src = 'http://widgets.digg.com/buttons.js';s1.parentNode.insertBefore(s, s1);})();</script><a class="DiggThisButton DiggCompact"></a><a href="javascript:void(window.open('http://www.myspace.com/Modules/PostTo/Pages/?u='+encodeURIComponent(document.location.toString()),'ptm','height=450,width=550').focus())">
    <img src="http://cms.myspacecdn.com/cms//ShareOnMySpace/Myspace_btn_ShareOnMyspace.png" border="0" alt="Share on Myspace" />
</a><div class="g-plusone" data-annotation="inline"></div><a href="https://twitter.com/intent/tweet?button_hashtag=quiz" class="twitter-hashtag-button" data-related="Quizzlr" data-url="http://quizzlr.info">Tweet #quiz</a>
<script>!function(d,s,id){var js,fjs=d.getElementsByTagName(s)[0];if(!d.getElementById(id)){js=d.createElement(s);js.id=id;js.src="//platform.twitter.com/widgets.js";fjs.parentNode.insertBefore(js,fjs);}}(document,"script","twitter-wjs");</script>
			<div class="fb-like" data-href="http://128.12.54.50/quizzlr/quiz.jsp?id=<%=Integer.toString(curQuiz.getQuizID()) %>" data-send="true" data-width="450" data-show-faces="true"></div><br />
			</div>
		</div>
	</div>
	<div id="side">
		<%@include file="includes/sidebar.jsp" %>
	</div>
</div>
<script>
$('#quizHistoryBtn').click(function(e) {
	$('#quizHistoryBox').toggle();
	e.preventDefault();
});
$('#quizReviewBtn').click(function(e) {
	$('#quizReviewBox').toggle();
	e.preventDefault();
});
</script>
<%@include file = "includes/footer.jsp" %>
