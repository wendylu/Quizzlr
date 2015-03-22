<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@page import="quizzlr.backend.*"%>
<html>
<head>
<%@page import="quizzlr.quiz.*"%>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Edit Quiz</title>
</head>
<body>
<%@include file = "includes/header.jsp" %>
<div id="main">
    <div id="contents">
        <div class="content" id="question">
            <%
            Quiz quiz = Quiz.getQuizFromID(Integer.parseInt(request.getParameter("id")));
            Set<Tag> tags = quiz.getTags();
            %>

            <h1>Edit quiz</h1>
            <form action="EditQuizServlet" method="post">
                <input type="hidden" name="id" value="<%=quiz.getQuizID() %>">
                Title: <input type="text" name="title" value="<%= Util.escapeHTML(quiz.getTitle()) %>" /><br />
                Description: <br />
                <textarea name="description" rows="3" cols="40"><%= Util.escapeHTML(quiz.getDescription()) %></textarea><br />
                Category:
                <select name="category" id="quizCategory">
				<%
					Set<Category> categories = Category.getAllCategories();
					Category quizCategory = quiz.getCategory();
					for (Category category : categories) {
						String isSelected = (category.equals(quizCategory)) ? " selected" : "";
						out.println("<option value=\"" + category.getCategoryID() + "\"" + isSelected + ">" + category.getTitle() + "</option>");
					}
				%>
				</select><br />
                Tags: <br/>
                <div id="tags">
	                <%
	                for (Tag tag : tags) {
	                	out.println("<span class='tag'>");
	                	out.println("<input type='hidden' name='tags' value='" + Util.escapeHTML(tag.getTitle()) + "' />");
	                	out.println(Util.escapeHTML(tag.getTitle()));
	                	out.println("  <a href='#'>&times;</a>");
	                	out.println("</span>");
	                }
	                %>
                </div>
                <input type="text" id="newTagBox" /><br/>
                <input type="submit" value="submit">
            </form>
        </div>
    </div>
    <div id="side">
        <%@include file="includes/sidebar.jsp" %>
    </div>
</div>
<script src="includes/tags.js"></script>
<%@include file = "includes/footer.jsp" %>