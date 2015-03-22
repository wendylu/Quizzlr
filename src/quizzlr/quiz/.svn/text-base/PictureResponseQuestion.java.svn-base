package quizzlr.quiz;

import java.util.Set;

import quizzlr.backend.QuizQuestion;
import quizzlr.backend.Util;

public class PictureResponseQuestion extends QuizQuestion {

	// SAMPLE QUESTION:
	// question = "This building can be found at what university?"
	// link = "http://events.stanford.edu/events/252/25201/Memchu_small.jpg"
	// above = true
	// answers = {"Stanford University", "Stanford"}

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	private String question; // could be empty
	private String link;
	private boolean above; // whether the picture is above or below the picture text
	private Set<String> answers;

	public PictureResponseQuestion(String question, String link, boolean above, Set<String> answers) {
		this.question = question;
		this.link = link;
		this.above = above;
		this.answers = answers;
	}

	@Override
	public String toHTML() {
		StringBuffer htmlBuffer = new StringBuffer();
		if (above) {
			htmlBuffer.append("<img src=\"" + link + "\">");
		}
		htmlBuffer.append("<h3>" + question + "</h3>");
		if (!above) {
			htmlBuffer.append("<img class=\"questionPicture\" src=\"" + link + "\"><br />");
		}
		htmlBuffer.append("<input type='text' placeholder='answer' name='answer_" + getQuizQuestionID() + "'><br/>");
		String html = htmlBuffer.toString();
		return html;
	}

	@Override
	public String getCorrectAnswers() {
		StringBuffer b = new StringBuffer();
		b.append("<p>Correct answer: " + answers.iterator().next() + "<br />");
		return b.toString();
	}

	@Override
	public int grade(Object response) {
		for (String s : answers) {
			if (Util.strip(s).equals(Util.strip((String) response)))
				return 1;
		}
		return 0;
	}

	@Override
	public int getMaxScore() {
		return 1;
	}

}
