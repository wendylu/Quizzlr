package quizzlr.quiz;

import java.util.List;

import quizzlr.backend.QuizQuestion;

public class MultipleAnswerMultipleChoice extends QuizQuestion {

	// SAMPLE QUESTION:
	// question = "Which of the following people are professors in the Stanford CS department?
	// response = {"Mehran Sahami",
	//			   "John Taylor",
	//			   "Jerry Cain",
	//			   "Patrick Young",
	//			   "Linda Paulson"}
	// correct = {true, false, true, true, false}

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	private String question;
	private List<String> responses;
	private List<Boolean> correct;

	public MultipleAnswerMultipleChoice(String question, List<String> responses, List<Boolean> correct) {
		this.question = question;
		this.responses = responses;
		this.correct = correct;
	}

	@Override
	public String toHTML() {
		StringBuffer htmlBuffer = new StringBuffer();
		htmlBuffer.append("<h3>" + question + "</h3>");
		for (int i = 0; i < responses.size(); i++) {
			htmlBuffer.append("<input type='checkbox' name='answer_" + getQuizQuestionID() + "_" + (i+1) + "checked' value='true'/>" + responses.get(i) + "<br/>");
		}
		String html = htmlBuffer.toString();
		return html;
	}

	@Override
	public String getCorrectAnswers() {
		StringBuffer b = new StringBuffer();
		b.append("<p>");
		int numCorrect = 0;
		for (int i = 0; i < correct.size(); i++) {
			if (correct.get(i)) numCorrect++;
		}
		if (numCorrect == 1) {
			b.append("Correct answer: ");
			for (int i = 0; i < correct.size(); i++) {
				if (correct.get(i)) b.append(responses.get(i));
			}
		} else if (numCorrect == 2) {

		} else {
			b.append("Correct answers: ");
			int soFar = 0;
			for (int i = 0; i < correct.size(); i++) {
				if (correct.get(i)) {
					if (soFar == numCorrect - 1) {
						b.append(" and " + responses.get(i));
					} else {
						b.append(responses.get(i) + ", ");
					}
					soFar++;
				}
			}
		}
		b.append("<br />");
		return b.toString();
	}

	@Override
	public int grade(Object response) {
		int score = 0;
		List<Boolean> castedResponse = (List<Boolean>) response;
		for (int i = 0; i < correct.size(); i++) {
			if (castedResponse.get(i).equals(correct.get(i))) score++;
		}
		return score;
	}

	@Override
	public int getMaxScore() {
		return correct.size();
	}

	public int getNumPrompts() {
		return correct.size();
	}
}
