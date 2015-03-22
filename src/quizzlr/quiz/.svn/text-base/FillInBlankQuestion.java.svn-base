package quizzlr.quiz;

import java.util.Set;

import quizzlr.backend.QuizQuestion;
import quizzlr.backend.Util;

public class FillInBlankQuestion extends QuizQuestion {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String question1;
	private String question2;
	private Set<String> answers;

	public FillInBlankQuestion(String question1, String question2, Set<String> answers) {
		this.question1 = question1;
		this.question2 = question2;
		this.answers = answers;
	}
	
	@Override
	public String toHTML() {
		StringBuffer htmlBuffer = new StringBuffer();
		htmlBuffer.append("<h3>" + question1);
		htmlBuffer.append("<input type='text' placeholder='answer' name='answer_" + getQuizQuestionID() + "'>");
		htmlBuffer.append(question2 + "</h3>");
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
