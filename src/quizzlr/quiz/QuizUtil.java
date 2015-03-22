package quizzlr.quiz;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import quizzlr.backend.*;

public class QuizUtil {
	public static String makeUserResultTable(List<QuizResult> results) {
		StringBuilder html = new StringBuilder();
		html.append("<table>");
		html.append("<tr><th>Quiz</th><th>Score</th><th>% correct</th><th>Elapsed time</th><th>Time</th></tr>");
		for (QuizResult r : results) {
			html.append("<tr>");
			html.append("<td><a href='quiz.jsp?id=" + r.getQuiz().getQuizID() + "'>" + r.getQuiz().getTitle() + "</a></td>");
			html.append("<td>" + r.getScore() + "</td>");
			html.append("<td>" + r.getPercentCorrect() + "%</td>");
			html.append("<td>" + Util.formatDuration(r.getElapsedTime()) + "</td>");
			html.append("<td>" + Util.formatTime(r.getTimestamp()) + "</td>");
			html.append("</tr>");
		}
		html.append("</table>");
		
		return html.toString();
	}
	
	public static String makeQuizResultTable(List<QuizResult> results) {
		StringBuilder html = new StringBuilder();
		html.append("<table>");
		html.append("<tr><th>User</th><th>Score</th><th>% correct</th><th>Elapsed time</th><th>Time</th></tr>");
		for (QuizResult r : results) {
			html.append("<tr>");
			html.append("<td><a href='userPage.jsp?id=" + r.getUser().getUserID() + "'>" + r.getUser().getUsername() + "</a></td>");
			html.append("<td>" + r.getScore() + "</td>");
			html.append("<td>" + r.getPercentCorrect() + "%</td>");
			html.append("<td>" + Util.formatDuration(r.getElapsedTime()) + "</td>");
			html.append("<td>" + Util.formatTime(r.getTimestamp()) + "</td>");
			html.append("</tr>");
		}
		html.append("</table>");
		
		return html.toString();
	}
	
	public static String makeQuizTopScoresTable(List<QuizResult> results) {
		StringBuilder html = new StringBuilder();
		html.append("<table>");
		html.append("<tr><th>User</th><th>Score</th><th>% correct</th><th>Elapsed time</th><th>Time</th></tr>");
		for (QuizResult r : results) {
			html.append("<tr>");
			html.append("<td><a href='userPage.jsp?id=" + r.getUser().getUserID() + "'>" + r.getUser().getUsername() + "</a></td>");
			html.append("<td>" + r.getScore() + "</td>");
			html.append("<td>" + r.getPercentCorrect() + "%</td>");
			html.append("<td>" + Util.formatDuration(r.getElapsedTime()) + "</td>");
			html.append("<td>" + Util.formatTime(r.getTimestamp()) + "</td>");
			html.append("</tr>");
		}
		html.append("</table>");
		
		return html.toString();
	}
	
	public static String makeQuizReviewTable(List<QuizRating> ratings) {
		StringBuilder html = new StringBuilder();
		html.append("<table>");
		html.append("<tr><th>User</th><th>Rating</th><th>Review</th></tr>");
		for (QuizRating r : ratings) {
			String imgIcon = "pictures/like.png";
			if (r.getRating() < 0) {
				imgIcon = "pictures/unlike.png";
			}
			html.append("<tr>");
			html.append("<td><a href='userPage.jsp?id=" + r.getUser().getUserID() + "'>" + r.getUser().getUsername() + "</a></td>");
			html.append("<td><img src='" + imgIcon + "'/></td>");
			html.append("<td>" + r.getReview() + "</td>");
			html.append("</tr>");
		}
		html.append("</table>");
		
		return html.toString();
	}
	
	public static String makeUserReviewTable(List<QuizRating> ratings) {
		StringBuilder html = new StringBuilder();
		html.append("<table>");
		html.append("<tr><th>User</th><th>Rating</th><th>Review</th></tr>");
		for (QuizRating r : ratings) {
			String imgIcon = "pictures/like.png";
			if (r.getRating() < 0) {
				imgIcon = "pictures/unlike.png";
			}
			html.append("<tr>");
			html.append("<td><a href='quiz.jsp?id=" + r.getQuiz().getQuizID() + "'>" + r.getQuiz().getTitle() + "</a></td>");
			html.append("<td><img src='" + imgIcon + "'/></td>");
			html.append("<td>" + r.getReview() + "</td>");
			html.append("</tr>");
		}
		html.append("</table>");
		
		return html.toString();
	}
	
	public static String makeQuizTakenTable(List<QuizResult> results) {
		StringBuilder html = new StringBuilder();
		html.append("<table>");
		html.append("<tr><th>User</th><th>Quiz</th><th>Score</th><th>% correct</th><th>Elapsed time</th><th>Time</th></tr>");
		for (QuizResult r : results) {
			html.append("<tr>");
			html.append("<td><a href='userPage.jsp?id=" + r.getUser().getUserID() + "'>" + r.getUser().getUsername() + "</a></td>");
			html.append("<td><a href='quiz.jsp?id=" + r.getQuiz().getQuizID() + "'>" + r.getQuiz().getTitle() + "</a></td>");
			html.append("<td>" + r.getScore() + "</td>");
			html.append("<td>" + r.getPercentCorrect() + "%</td>");
			html.append("<td>" + Util.formatDuration(r.getElapsedTime()) + "</td>");
			html.append("<td>" + Util.formatTime(r.getTimestamp()) + "</td>");
			html.append("</tr>");
		}
		html.append("</table>");
		
		return html.toString();
	}
	
	public static Object getUserResponse(QuizQuestion question, HttpServletRequest request) {
		if (question instanceof ResponseQuestion) {
			return (String)request.getParameter("answer_"+question.getQuizQuestionID());
		}
		else if (question instanceof MultipleResponseQuestion) {
			List<String> responses = new ArrayList<String>();
			MultipleResponseQuestion q = (MultipleResponseQuestion)question;
			for (int i = 0; i < q.getNumPrompts(); i++) {
				responses.add((String)request.getParameter("answer_" + question.getQuizQuestionID() + "_" + i));
			}
			return responses;
		}
		else if (question instanceof MultipleAnswerMultipleChoice) {
			List<Boolean> responses = new ArrayList<Boolean>();
			MultipleAnswerMultipleChoice q = (MultipleAnswerMultipleChoice)question;
			for (int i = 0; i < q.getNumPrompts(); i++) {
				// if checkbox is not checked, no value will come in
				boolean checked = (request.getParameter("answer_" + question.getQuizQuestionID() + "_" + (i+1) + "checked") != null);
				responses.add(checked);
			}
			return responses;
		}
		else if (question instanceof MultipleChoiceQuestion) {
			return Integer.parseInt((String)request.getParameter("answer_" + question.getQuizQuestionID()));
		}
		else if (question instanceof PictureResponseQuestion) {
			return request.getParameter("answer_" + question.getQuizQuestionID());
		}
		else if (question instanceof FillInBlankQuestion) {
			return request.getParameter("answer_" + question.getQuizQuestionID());
		}
		
		return null;
	}
	
	private static String[] profanities = { "fuck", "shit", "ass", "bitch", "bastard", "cunt", "damn", "fag" };
	private static boolean containsProfanity(String string) {
		string = Util.strip(string);
		for (String profanity : profanities) {
			if (string.contains(profanity)) {
				return true;
			}
		}
		
		return false;
	}
	
	public static boolean checkPottyMouthAchievement(Object response) {
		if (response instanceof String) {
			if (containsProfanity((String)response)) {
				return true;
			}
		}
		else if (response instanceof Iterable<?>) {
			for (Object o : (Iterable<?>)response) {
				if (o instanceof String) {
					if (containsProfanity((String)response)) {
						return true;
					}
				}
			}
		}
		
		return false;
	}
}
