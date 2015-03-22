<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@ page import="javax.servlet.*,java.util.*,quizzlr.backend.*" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Achievements</title>
</head>
<body>
    <%@include file = "includes/header.jsp" %>
    <%
    if (user == null)
    {
    	RequestDispatcher dispatch = request.getRequestDispatcher("/");
    	dispatch.forward(request, response);
    	return;
    }

    Set<Achievement> all = user.getAchievements();
    %>
    <div id="main">
        <div id="contents">
            <div class="content">
				<h1> Congratulations! You have earned the following achievements</h1>
				<ul>
				<%
				for (Achievement a : all) {
					//String imgString = "<img src=\"pictures/newbie.png\" alt=\"Newbie icon\"/>";
					//out.println("<li>" + imgString + "</li>");
					out.println("<img src=\"pictures/achievements/" + a.getAchievementType().getImageIcon() + "\" title=\"" + a.getAchievementType().getDescription() + "\"/>");
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