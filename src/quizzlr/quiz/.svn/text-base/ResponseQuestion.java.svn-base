package quizzlr.quiz;

import java.util.Set;

import quizzlr.backend.QuizQuestion;
import quizzlr.backend.Util;

public class ResponseQuestion extends QuizQuestion {

	// SAMPLE QUESTION:
	// question = "Which university, located in Palo Alto, CA, is the alma mater of John Elway, John Lynch, and Andrew Luck?
	// answers = {"Stanford", "Stanford University"}

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	private String question;
	private Set<String> answers;

	public ResponseQuestion(String question, Set<String> answers) {
		this.question = question;
		this.answers = answers;
	}

	@Override
	public String toHTML() {
		StringBuffer htmlBuffer = new StringBuffer();
		htmlBuffer.append("<h3>" + question + "</h3>");
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
