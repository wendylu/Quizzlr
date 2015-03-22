<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Matching</title>
<script>
 
function getMousePos(canvas, evt){
    // get canvas position
    var obj = canvas;
    var top = 0;
    var left = 0;
    while (obj && obj.tagName != 'BODY') {
        top += obj.offsetTop;
        left += obj.offsetLeft;
        obj = obj.offsetParent;
    }
 
    // return relative mouse position
    var mouseX = evt.clientX - left + window.pageXOffset;
    var mouseY = evt.clientY - top + window.pageYOffset;
    return {
        x: mouseX,
        y: mouseY
    };
}
 
	window.onload = function(){
                var canvas = document.getElementById("myCanvas");
                var context = canvas.getContext("2d");
                canvas.addEventListener('mousemove', function(evt){
                	context.clearRect(0, 0, canvas.width, canvas.height);
                    var mousePos = getMousePos(canvas, evt);
                    
            		context.moveTo(mousePos.x,mousePos.y);
            		context.lineTo(300,200);
            		context.stroke();
                }, false);
            };
        </script>
</head>
<body>
<%@include file = "includes/header.jsp" %>
<div id="main">
	<div id="contents">
		<div class="content" id="matching">
		<canvas id="myCanvas" width="600" height="400" style="border:1px solid #D4D4D4;">
        </canvas>
		</div>
	</div>
	<div id="side">
		<%@include file="includes/sidebar.jsp" %>
	</div>
</div>
<%@include file = "includes/footer.jsp" %>