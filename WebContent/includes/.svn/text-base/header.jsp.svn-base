<%@ page import="java.util.*,quizzlr.backend.User,quizzlr.backend.Message, java.util.Date " %>
<link rel="stylesheet" href="includes/styles.css" >
<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.7.1/jquery.min.js"></script>
<script src="https://ajax.googleapis.com/ajax/libs/jqueryui/1.8.18/jquery-ui.min.js"></script>
<link rel="http://jqueryui.com/themes/base/jquery.ui.all.css">
<link href='http://fonts.googleapis.com/css?family=Inder' rel='stylesheet' type='text/css'>
<link rel="icon" type="image/x-icon" href="pictures/favicon.ico">
<%
Cookie[] list = request.getCookies();
if (list != null) {
	for (Cookie cookie : list) {
		String key = cookie.getName();
		if (key.equals("id") && !cookie.getValue().equals("")) {

			User stored = User.getUserFromID(Integer.parseInt(cookie.getValue()));
			HttpSession userSession = request.getSession();
			userSession.setAttribute("User", stored);
		}

		if (key.equals("username")) {
		    cookie.setMaxAge(0);
		}
	}
}

if (session.getAttribute("User") != null && ((quizzlr.backend.User)session.getAttribute("User")).getIsBanned()) {
	session.removeAttribute("User");
	%>
	<script>
	document.location.href = "/quizzlr/banned.jsp";
	</script>
	<%
}
%>
	<script>
	$(function() {
		$( "#button" ).click(function() {
			$( "#loginbox" ).toggle();
		});
		$(" #SHOW").click(function(){
			$( "#TOOBAD" ).toggle();
		});
	});
	</script>
	<script>
	$(function() {
	$( "#searchbar" ).autocomplete({
		source: "SearchServlet",
		minLength: 2,
		select: function( event, ui ) {
			if(ui.item)	window.location= ui.item.link;
		}
	});
	$("#create").click(function(){
		$( "#loginbox" ).hide();
		$("#cover").show();
		$("#createBox").show();
		
	});
	});
	</script>
<div id="cover" style=display:none; onClick='$(this).hide(); $("#createBox").hide();'>
</div>
<div id="createBox" style=display:none;>
	<h2>Create Account</h2>
	<form action="createServlet" method="post">
		<input type="text" name="userName" placeholder="Username" /><br />
		<input type="password" name="password" placeholder="Password" /><br />
		<input type="password" name="passwordConfirm" placeholder="Confirm Password"/><br />
		<input type="submit" value="Create" />
	</form> <br />
	<a href="#" onClick='$("#cover").hide(); $("#createBox").hide();'>close window</a>
</div>
<div id="TOOBAD" style=display:none; onClick='$(this).hide();'>
	TOO BAD
</div>

<div id="header">
	<div id="header-items">
		<div id="logo">
		<% if((new Date()).getTime() % 20 != 0) {%>
			<a href="/quizzlr/" ><img name="nav" src="pictures/quizzlr_dark.png" height= "40" onmouseover="this.src='/quizzlr/pictures/quizzlr_white.png'" onmouseout="this.src='/quizzlr/pictures/quizzlr_dark.png'"></a>
		<% }else{ %> 
			<a href="/quizzlr/" ><img name="nav" src="pictures/quizzlr_dark.png" height= "40" onmouseover="this.src='/quizzlr/pictures/redfire.gif'" onmouseout="this.src='/quizzlr/pictures/quizzlr_dark.png'"></a>
		<%} %>
<div class="ui-widget">
<div id="search"><form action="SearchServlet" method="POST"><input id="searchbar" name="search" placeholder=" Search for users, quizzes, and tags" size="95" style="border-radius: 8px;"/></form></div>		
</div>
			
		<div id="login">
			<%
			User user = (User) session.getAttribute("User");
			if(user == null)
			out.println("<a href=\"#\" id=\"button\">LOGIN/REGISTER</a>");
			else {
				
				int newfriendR = 0;
				int newchallenges = 0;
				int newnotes = 0;
				List<Message> messages = user.getMessages();
    			if (messages != null && messages.size() != 0) {
    				for (Message message : messages) {
    					if (message.getIsRead() == false) {
    						if (message.getMessageType() == 1) {
    							newfriendR++;
    						} else if(message.getMessageType() == 2) {
    							newchallenges++;
    						} else if (message.getMessageType() == 3) {
    							newnotes++;
    						}
    					}
    				}
    			}
			%>
			<div id="headerName" ><a href="profile.jsp"><%=user.getUsername()%></a></div>
			<a href="inbox.jsp">
			<img class="icon" id="friendIcon" src="pictures/icons/friends_grey.png" height= "25" onmouseover="this.src='/quizzlr/pictures/icons/friends_white.png'" onmouseout="this.src='/quizzlr/pictures/icons/friends_grey.png'">
			</a>
			<a href="inbox.jsp#notes">
			<img class="icon" id="noteIcon" src="pictures/icons/mail_grey.png" height= "25" onmouseover="this.src='/quizzlr/pictures/icons/mail_white.png'" onmouseout="this.src='/quizzlr/pictures/icons/mail_grey.png'">
			</a>
			<a href="inbox.jsp#challenges">
			<img class="icon" id="challengeIcon" src="pictures/icons/trophy_grey.png" height= "25" onmouseover="this.src='/quizzlr/pictures/icons/trophy_white.png'" onmouseout="this.src='/quizzlr/pictures/icons/trophy_grey.png'">
			<img class="notification" id="friendNotification" src="pictures/icons/notification.png" height= "25" <%if(newfriendR==0  || request.getRequestURI().equals("/quizzlr/inbox.jsp")) out.print("style=display:none;");%>>
			<img class="notification" id="noteNotification" src="pictures/icons/notification.png" height= "25" <%if(newnotes==0  || request.getRequestURI().equals("/quizzlr/inbox.jsp")) out.print("style=display:none;");%>>
			<img class="notification" id="challengeNotification" src="pictures/icons/notification.png" height= "25" <%if(newchallenges==0  || request.getRequestURI().equals("/quizzlr/inbox.jsp")) out.print("style=display:none;");%>>
			<span class="newNum" id="friendNum" <%if(newfriendR==0  || request.getRequestURI().equals("/quizzlr/inbox.jsp")) out.print("style=display:none;");%>><%=Integer.toString(newfriendR) %></span>&nbsp;&nbsp;&nbsp;&nbsp;
			<span class="newNum" id="noteNum" <%if(newnotes==0  || request.getRequestURI().equals("/quizzlr/inbox.jsp")) out.print("style=display:none;");%>><%=Integer.toString(newnotes) %></span>&nbsp;&nbsp;&nbsp;&nbsp;
			<span class="newNum" id="challengeNum" <%if(newchallenges==0  || request.getRequestURI().equals("/quizzlr/inbox.jsp")) out.print("style=display:none;");%>><%=Integer.toString(newchallenges) %></span>
			</a>
			<% }%>
			
		<div id="loginbox" style=display:none;>
			<div id="loginform">
			<form action="LoginServlet" method="post">
			<input type="text" name="userName" placeholder="Enter Username"/><br />
			<input type="password" name="password" placeholder="Enter Password"/><br />
			<input type="submit" value="Login"/><br />
			
		</form>
		<a href="#" class="create">Create Account</a><br />
		<a href="#" id="SHOW">Forgot Password?</a><br />
		<!-- <div class="fb-login-button" data-show-faces="true" data-width="150" data-max-rows="2"></div>-->
	</div>
</div>
		</div>
		</div>
	</div>
</div>

