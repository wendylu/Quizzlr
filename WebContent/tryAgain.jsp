<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Information Incorrect</title>
</head>
<body>
<%@include file = "includes/header.jsp" %>
<div id="main">
	<div id="contents">
		<div class="content" id="faq">
<h1> Please Try Again</h1>
<p>Either your user name or password is incorrect. Please try again</p>
<form action="LoginServlet" method="post">
User Name: <input type="text" name="userName" /><br />
Password: <input type="password" name="password" />
<input type="submit" value="Login" />
</form> 
<p>
<a href="createAccount.jsp">Create New Account</a> 
</p>
</div>
	</div>
	<div id="side">
		<%@include file="includes/sidebar.jsp" %>
	</div>
</div>
<%@include file = "includes/footer.jsp" %>
</body>
</html>