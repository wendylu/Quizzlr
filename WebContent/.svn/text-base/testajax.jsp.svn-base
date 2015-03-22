<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Admin Home</title>
</head>
<body>
	<%@include file = "includes/header.jsp" %>
<script>
	$(function() {
	$( "#birds" ).autocomplete({
		source: "UserSearchServlet",
		minLength: 2,
		select: function( event, ui ) {
			if(ui.item)	window.location="userPage.jsp?id=" + ui.item.id;
		}
	});
	});
	</script>
	<div id="main">
		<div id="contents">
			<div class="content" id="admin">
<div class="demo">

<div class="ui-widget">
	<label for="birds">Birds: </label>
	<input id="birds" />
</div>

<div class="ui-widget" style="margin-top:2em; font-family:Arial">
	Result:
	<div id="log" style="height: 200px; width: 300px; overflow: auto;" class="ui-widget-content"></div>
</div>

</div><!-- End demo -->

		<a href="UserSearchServlet?term="a">TEST</a>


		</div>
	</div>
	<div id="side">
		<%@include file="includes/sidebar.jsp" %>
	</div>
</div>
<%@include file = "includes/footer.jsp" %>
