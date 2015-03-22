<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Index</title>
</head>
<body>
<%@include file = "includes/header.jsp" %>
<div id="main">
	<div id="contents">
		<div class="content" id="welcome">
			<h1>CREATE QUIZ!</h1>
			<form action="CreateQuiz" method="post">
			<div class="quizAttr">
				<input type="text" name="title" placeholder="title"><br />
				<input type="text" name="description" placeholder="description"><br />
				<select name="category" id="quizCategory">
				<%
					Set<Category> categories = Category.getAllCategories();
					for (Category category : categories) {
						out.println("<option value=\"" + category.getCategoryID() + "\">" + category.getTitle() + "</option>");
					}
				%>
				</select><br />
				<input type="radio" name="isRandomOrder" value="false" checked> Fixed Order <input type="radio" name="isRandomOrder" value="true"> Random Order<br />
				<input type="radio" name="isOnePageFormat" value="false" checked> Multiple Page <input type="radio" name="isOnePageFormat" value="true"> One Pages<br />
				<input type="radio" name="isImmediateCorrection" value="false" checked> Correct After <input type="radio" name="isImmediateCorrection" value="true"> Correct Now<br />
				<input type="radio" name="isPracticeModeEnabled" value="false" checked> Practice Mode Off <input type="radio" name="isPracticeModeEnabled" value="true"> Practice Mode On<br />
				Tags: <br/>
                <div id="tags"></div>
                <input type="text" id="newTagBox" /><br/>
			</div>
			<input type="submit" value="Create Quiz"/><br />
			</form>
		</div>
	</div>
	<div id="side">
		<%@include file="includes/sidebar.jsp" %>
	</div>
</div>
<script src="includes/tags.js"></script>
<%@include file = "includes/footer.jsp" %>