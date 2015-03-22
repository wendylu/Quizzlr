<%@page import="java.text.SimpleDateFormat"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ page import="javax.servlet.*,java.util.*,quizzlr.backend.*"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">

<title>Your Inbox</title>
</head>
<body>
<%@include file="includes/header.jsp"%>
<%
	if (user == null)
	{
		RequestDispatcher dispatch = request.getRequestDispatcher("loginHome.jsp");
		dispatch.forward(request, response);
		return;
	}
%>
<div id="main">
<div id="contents">
<div class="content" id="friend">


<h3></h3>
<h3>Friend Requests</h3>
<table border="1">
	<tr>
		<th>Sent time</th>
		<th>Sender</th>
		<th>Message</th>
		<th>Actions</th>
	</tr>
	<%


	boolean messagePrinted = false;
	List<Message> allMessages = user.getMessages();


	for (Message message : allMessages) {
		String userLink = "<a href=userPage.jsp" + "?id=" + message.getFromUser().getUserID() + ">" + message.getFromUser().getUsername() + "</a>";
		if (message.getMessageType() == 1) {
			if (!message.getFromUser().isFriendOf(user)) {


			out.println("<tr> <td>" + Util.formatTime(message.getSentTimestamp()) + "</td> <td>" + userLink + "</td>" + "<td>Friend Request</td>" + "<td>" + "<form action=\"MessageReplyServlet\" method=\"post\">"
			+ "<input name=\"messageID\" type=\"hidden\" value=\"" + message.getMessageID() + "\" />"
			+ "<input name=\"accept\" type=\"hidden\" value=\"yes\" />"
			+ "<input type=\"submit\" value=\"Accept\" />"
			+ "</form>"
			+ "<form action=\"MessageReplyServlet\" method=\"post\">"
			+ "<input name=\"messageID\" type=\"hidden\" value=\"" + message.getMessageID() + "\" />"
			+ "<input name=\"accept\" type=\"hidden\" value=\"no\" />"
			+ "<input type=\"submit\" value=\"Decline\" />"
			+ "</form>"
			+ "</td>");
			messagePrinted = true;
			}
		} 
		message.setIsRead(true);
		out.println("</tr>");
	}
		
	if (!messagePrinted) {
		out.println("<tr> <td> No New Friend Requests </td> </tr>");
	}
%>
</table>

<a name="challenges"></a><br>
<h3></h3><br> 
<h3>Challenges</h3>
<table border="1">
	<tr>
		<th>Sent time</th>
		<th>Sender</th>
		<th>Message</th>
		<th>Actions</th>
	</tr>
	<%
			messagePrinted = false;
			for (Message message : allMessages) {
				String userLink = "<a href=userPage.jsp" + "?id=" + message.getFromUser().getUserID() + ">" + message.getFromUser().getUsername() + "</a>";
				if (message.getMessageType() == 2) {
					String messageContent = message.getMessageContent();
					int quizID = Integer.parseInt(messageContent);
					Quiz quiz = Quiz.getQuizFromID(quizID);
					// Quiz is deleted
					if (quiz == null) {
						message.delete();
						continue;
					}
					String quizName = Quiz.getQuizFromID(quizID).getTitle();
					String quizLink = "<a href=\"quiz.jsp?id="
						+ Integer.toString(quiz.getQuizID()) + "\">"
						+ quizName + "</a>";
					List<QuizResult> quizHistory = message.getFromUser().getQuizHistory();
					int bestScore = 0;
					for (QuizResult r : quizHistory) {
						if (r.getQuiz().equals(quiz)) {
							if (r.getScore() > bestScore) {
								bestScore = r.getScore();
							}
						}
					}

					String bestScoreNote = "";
					if (bestScore == 0) {
						bestScoreNote = userLink + " has not taken this quiz yet.";
					} else {
						bestScoreNote = userLink + " scored " + bestScore + " on this quiz.";
					}

					String challengeNote = userLink + " has challenged you to take the quiz " + quizLink + ". ";
					out.println("<tr> <td>" + Util.formatTime(message.getSentTimestamp()) + "</td> <td>" + userLink + "</td> <td>" + challengeNote + bestScoreNote + "</td> <td>"
							+ "<form action=\"quiz.jsp?id=" + quizID + "\" method=\"post\">"
							+ "<input type=\"submit\" value=\"Go to quiz\" />"
							+ "</form>"
							+ "<form action=\"MessageReplyServlet\" method=\"post\">"
							+ "<input name=\"messageID\" type=\"hidden\" value=\"" + message.getMessageID() + "\" />"
							+ "<input type=\"submit\" value=\"Delete\" />"
							+ "</form>"
					+ "</td>");

					messagePrinted = true;
				}
				message.setIsRead(true);
				out.println("</tr>");
			}
				
			if (!messagePrinted) {
				out.println("<tr> <td> No New Challenges </td> </tr>");
			}	
			%>
</table>

<a name="notes"></a>
<h3></h3><br> 
<h3>Notes</h3>
<table border="1">
	<tr>
		<th>Sent time</th>
		<th>Sender</th>
		<th>Message</th>
		<th>Actions</th>
	</tr>
	<%
			messagePrinted = false;
			for (Message message : allMessages) {
				String userLink = "<a href=userPage.jsp" + "?id=" + message.getFromUser().getUserID() + ">" + message.getFromUser().getUsername() + "</a>";
				if (message.getMessageType() == 3) {
					String messageContent = message.getMessageContent();
					messageContent = messageContent.replaceAll("&", "&amp");
					messageContent = messageContent.replaceAll("<", "&lt;");
					messageContent = messageContent.replaceAll(">", "&gt;");
					out.println("<tr> <td>" + Util.formatTime(message.getSentTimestamp()) + "</td> <td>" + userLink + "</td> <td>" + messageContent + "</td> <td>"
							+ "<form action=\"noteReply.jsp\" method=\"post\">"
							+ "<input name=\"messageID\" type=\"hidden\" value=\"" + message.getMessageID() + "\" />"
							+ "<input type=\"submit\" value=\"Reply\" />"
							+ "</form>"
							+ "<form action=\"MessageReplyServlet\" method=\"post\">"
							+ "<input name=\"messageID\" type=\"hidden\" value=\"" + message.getMessageID() + "\" />"
							+ "<input name=\"action\" type=\"hidden\" value=\"delete\" />"
							+ "<input type=\"submit\" value=\"Delete\" />"
							+ "</form>"
					+ "</td>");
					messagePrinted = true;
				}
				message.setIsRead(true);
				out.println("</tr>");
			}
				
			if (!messagePrinted) {
				out.println("<tr> <td> No New Notes </td> </tr>");
			}	
			%>
</table>

</div>
</div>
<div id="side"><%@include file="includes/sidebar.jsp"%>
</div>
</div>
<%@include file="includes/footer.jsp"%>