<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
      <%@ page import="javax.servlet.*,java.util.*,quizzlr.backend.*" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Search Results</title>
</head>
<body>
    <%@include file = "includes/header.jsp" %>
    <div id="main">
        <div id="contents">
            <div class="content">
	            <h3>User search results:</h3>
				<ul>
				<%
					HttpSession userSession = request.getSession();
					User loggedInUser = (User) userSession.getAttribute("User");

					if (loggedInUser == null) {
						out.println("<a href=loginHome.jsp>" + "Please log in");
					} else {
						Set<User> matches = (Set<User>) request.getAttribute("matches");
						for (User match : matches) {
							String matchName = match.getUsername();
							matchName = Util.escapeHTML(matchName);
							int matchID = match.getUserID();
							if (matchID != loggedInUser.getUserID()) {
								out.println("<li><a href=userPage.jsp?id=" + matchID + ">" + matchName + "</a></li>");
							}
						}
					}
				%>
				</ul>
			</div>
		</div>
	</div>
<%@include file = "includes/footer.jsp" %>