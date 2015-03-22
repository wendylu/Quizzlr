<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Index</title>
</head>
<body>
<%@include file = "includes/header.jsp" %>
<script>
function addResponse(object, type){
	var index = $(object).find('div').length;
	var otherindex = $(object).find('input').length;
	switch(type)
	{
		case 'multipleResponse': $(object).append('<input type="text" name="answer' + (index+2) + '_1" placeholder="answer ' + (index+2) + '">' + 
				'<div id="MultipleResponseAnswer'+(index+2)+'" class="extraAnswer"></div><input type="button" name="multipleResponseAdd'+(index+2)+'" value="add alternate" onClick="addAnswer(\'#MultipleResponseAnswer'+(index+2)+'\','+(index+2)+')">' +
				'<br />'); break;
		case 'multipleChoice': $(object).append('<input type="text" name="answer' + (otherindex/2+2) + '" placeholder="answer ' + (otherindex/2+2) + '"><input type="radio" name="correctIndex" value="'+(otherindex/2+2)+'"><br />'); break;
		case 'multipleAnswerMultipleChoice': $(object).append('<input type="text" name="answer' + (otherindex/2+2) + '" placeholder="answer ' + (otherindex/2+2) + '"><input type="checkbox" value="true" name="answer'+(otherindex/2+2)+'true"><br />'); break;
	}
}

function addAnswer(object,curanswer){
	var index = $(object).find('input').length;
	$(object).append('<input type="text" name="answer'+curanswer + '_' + (index+2) + '" placeholder="alternative ' + (index+1) + '">');
}

function addBlank(){
	var index = $('.alternativesExtra').find('input').length;
	$('.alternativesExtra').append('<input type=\"text\" name=\"answer1_\"'+(index+2)+'\" placeholder=\"alternative '+(index+1)+'"><br />');
}

$(function() {
	function hide_all(){
		$(".response").hide();
		$(".multipleResponse").hide();
		$(".pictureResponse").hide();
		$(".multipleChoice").hide();
		$(".multipleAnswerMultipleChoice").hide();
		$(".fillintheBlank").hide();
	}
	hide_all();
	$(".response").show();
	$("#questionType").change(function() {
		hide_all();
		switch ($(this).val())
		{
			case 'response':	$(".response").show(); break;
			case 'multipleResponse':	$(".multipleResponse").show(); break;
			case 'pictureResponse':	$(".pictureResponse").show(); break;
			case 'multipleChoice':	$(".multipleChoice").show(); break;
			case 'multipleAnswerMultipleChoice':	$(".multipleAnswerMultipleChoice").show(); break;
			case 'fillintheBlank': $(".fillintheBlank").show(); break;
		}
	});
	$(".pictureUrl").change(function(){
		$(".pictureFrame").empty();
		$(".pictureFrame").append('<img class="questionPicture" src="'+$(this).val()+'"/>');
	});
});
</script>
<div id="main">
	<div id="contents">
		<div class="content" id="welcome">
			<h1>CREATE QUESTION!</h1>

			Question Type:
            <select name="type" id="questionType">
                <option value="response">Response</option>
                <option value="multipleResponse">Multiple Response</option>
                <option value="pictureResponse">Picture Response</option>
                <option value="multipleChoice">Multiple Choice</option>
                <option value="multipleAnswerMultipleChoice">Multiple Answer Multiple Choice</option>
                <option value="fillintheBlank">Fill in the Blank</option>
            </select><br />

			<form action="CreateResponseQuestion" method="post" class="response">
				<div class="questionAttr">
					<input type="text" name="responseQuestion" placeholder="question"><br />
					<input type="text" name="answer1_1" placeholder="answer">
					<div id="responseAnswer1" class="extraAnswer"></div>
					<input type="button" name="responseadd1" value="add alternate" onClick="addAnswer('#responseAnswer1',1)"><br />
				</div>
				<br /><br />
                <input type="submit" name="submit" value="Add Question"/><br />
                <input type="submit" name="finish" value="Add Question and Finish Quiz"/><br />
			</form>

			<form action="CreateMultipleResponseQuestion" method="post" class="multipleResponse">
                <div class="questionAttr">
					<input type="text" name="multipleResponseQuestion" placeholder="question"><br />
					<input type="text" name="answer1_1" placeholder="answer 1"><div id="multipleResponseAnswer1" class="extraAnswer"></div>
					<input type="button" name="multipleReponseAdd1" value="add alternate" onClick="addAnswer('#multipleResponseAnswer1',1)"><br />
					<div class="multipleResponseExtra"></div>
					<input type="button" value="add answer" id="multipleResponse1Add" onClick="addResponse('.multipleResponseExtra','multipleResponse')"/>
				</div>
				<br /><br />
                <input type="submit" name="submit" value="Add Question"/><br />
                <input type="submit" name="finish" value="Add Question and Finish Quiz"/><br />
			</form>

			<form action="CreatePictureResponseQuestion" method="post" class="pictureResponse">
                <div class="questionAttr">
					<input type="text" name="pictureResponseQuestion" placeholder="question"><br />
					<input type="text" class="pictureUrl" name="picture" placeholder="picture"><br />
					<div class="pictureFrame"></div>
					<input type="text" name="answer1_1" placeholder="answer">
					<div id="pictureAnswer1" class="extraAnswer"></div>
					<input type="button" name="pictureadd1" value="add alternate" onClick="addAnswer('#pictureAnswer1',1)"><br />
					<input type="radio" name="above" value="Above" checked> Picture Above Text <br /><input type="radio" name="above" value="Below"> Picture Below Text
				</div>
				<br /><br />
                <input type="submit" name="submit" value="Add Question"/><br />
                <input type="submit" name="finish" value="Add Question and Finish Quiz"/><br />
            </form>

			<form action="CreateMultipleChoiceQuestion" method="post" class="multipleChoice">
                <div class="questionAttr">
					<input type="text" name="multipleChoiceQuestion" placeholder="question"><br />
					<input type="text" name="answer1" placeholder="answer 1"><input type="radio" name="correctIndex" value="1"><br />
					<div class="multipleChoiceExtra"></div>
					<input type="button" value="add answer" id="multipleChoice1Add" onClick="addResponse('.multipleChoiceExtra','multipleChoice')"/>
				</div>
				<br /><br />
                <input type="submit" name="submit" value="Add Question"/><br />
                <input type="submit" name="finish" value="Add Question and Finish Quiz"/><br />
            </form>

			<form action="CreateMultipleAnswerMultipleChoiceQuestion" method="post" class="multipleAnswerMultipleChoice">
                <div class="questionAttr">
					<input type="text" name="multipleAnswerMultipleChoiceQuestion" placeholder="question"><br />
					<input type="text" name="answer1" placeholder="answer 1"><input type="checkbox" name="answer1true" value="true"><br />
					<div class="multipleAnswerMultipleChoiceExtra"></div>
					<input type="button" value="add answer" id="multipleChoice1Add" onClick="addResponse('.multipleAnswerMultipleChoiceExtra','multipleAnswerMultipleChoice')"/>
				</div>
				<br /><br />
                <input type="submit" name="submit" value="Add Question"/><br />
                <input type="submit" name="finish" value="Add Question and Finish Quiz"/><br />
            </form>

			<form action="CreateFillInBlankQuestion" method="post" class="fillintheBlank">
                <div class="questionAttr">
					<textarea id="blank1" name="fillInBlankQuestion1" placeholder="
					Sentence Before the Blank..." cols=30 rows=2 ></textarea>
					<input type="text" name="answer1_1" placeholder="Blank">
					<textarea id="blank2" name="fillInBlankQuestion2" placeholder="...Sentence After the Blank" cols=30 ></textarea><br />
					<div class="alternativesExtra"></div>
					<input type="button" value="add alternate" id="multipleChoice1Add" onClick="addBlank()">
				</div>
				<br /><br />
                <input type="submit" name="submit" value="Add Question"/><br />
                <input type="submit" name="finish" value="Add Question and Finish Quiz"/><br />
            </form>
            
			<h3><a href="/quizzlr/">SAVE QUIZ AND RETURN TO HOME</a></h3>

			<br />
		</div>
	</div>
	<div id="side">
		<%@include file="includes/sidebar.jsp" %>
	</div>
</div>
<%@include file = "includes/footer.jsp" %>