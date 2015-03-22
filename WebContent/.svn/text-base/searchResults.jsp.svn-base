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

					Set<User> userMatches = (Set<User>) request.getAttribute("userMatches");
					if(userMatches!=null){
					for (User match : userMatches) {
						String matchName = match.getUsername();
						int matchID = match.getUserID();
						if (matchID != loggedInUser.getUserID()) {
							out.println("<li><a href=userPage.jsp?id=" + matchID + ">" + matchName + "</a></li>");
						}
					}
					}
				%>
				</ul>

				<hr/>
				<h3>Quiz search results:</h3>
                <ul>
                <%
                    Set<Quiz> quizMatches = (Set<Quiz>) request.getAttribute("quizMatches");
                	if(quizMatches!=null){
                    for (Quiz match : quizMatches) {
                        String matchName = match.getTitle();
                        int matchID = match.getQuizID();
                        out.println("<li><a href=quiz.jsp?id=" + matchID + ">" + matchName + "</a></li>");
                    }
                	}
                %>
                </ul>
                <hr />
                <h3>Tag search results:</h3>
                <ul>
                <%
                    Set<Tag> tagMatches = (Set<Tag>) request.getAttribute("tagMatches");
                	if(tagMatches!=null){
                    for (Tag match : tagMatches) {
                        String matchName = match.getTitle();
                        int matchID = match.getTagID();
                        out.println("<li><a href=tag.jsp?id=" + matchID + ">" + matchName + "</a></li>");
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