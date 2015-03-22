package quizzlr.quiz;

import java.util.List;

import quizzlr.backend.QuizQuestion;

public class MultipleChoiceQuestion extends QuizQuestion {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	private String question;
	private List<String> answers;
	private int correctIndex;

	public MultipleChoiceQuestion(String question, List<String> answers, int correctIndex) {
		this.question = question;
		this.answers = answers;
		this.correctIndex = correctIndex;
	}

	@Override
	public String toHTML() {
		StringBuffer htmlBuffer = new StringBuffer();
		htmlBuffer.append("<h3>" + question + "</h3>");
		for (int i = 0; i < answers.size(); i++) {
			htmlBuffer.append("<input type='radio' name='answer_" + getQuizQuestionID() + "' value='" + (i+1) + "' />" + answers.get(i) + "<br/>");
		}
		String html = htmlBuffer.toString();
		return html;
	}

	@Override
	public String getCorrectAnswers() {
		StringBuffer b = new StringBuffer();
		b.append("<p>Correct answer: " + answers.get(correctIndex-1) + "<br />");
		return b.toString();
	}

	@Override
	public int grade(Object response) {
		if (((Integer) response).equals(correctIndex)) return 1;
		return 0;
	}

	@Override
	public int getMaxScore() {
		return 1;
	}
}
