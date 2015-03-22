package quizzlr.quiz;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import quizzlr.backend.QuizQuestion;
import quizzlr.backend.Util;

public class MultipleResponseQuestion extends QuizQuestion {

	// SAMPLE QUESTION:
	// question = "Which eight universities were the original members of the Pac-8 conference?
	// answers = {{"Washington", "UW", "University of Washington"}
	//			  {"Washington State", "WSU", "Washington State University", "Wazzu"}
	//			  {"Oregon", "UO", "University of Oregon"}
	//			  {"Oregon State", "OSU", "Oregon State University"}
	//			  {"California", "Cal", "Berkeley", "UC Berkeley"}
	//			  {"Stanford", "Stanford University"}
	//			  {"UCLA", "UC Los Angeles"}
	//			  {"USC", "University of Southern California", "Southern Cal", "Southern California"}
	// ordered = false

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	private String question;
	private List<Set<String>> answers;
	private boolean ordered;

	public MultipleResponseQuestion(String question, List<Set<String>> answers, boolean ordered) {
		this.question = question;
		this.answers = answers;
		this.ordered = ordered;
	}

	@Override
	public String toHTML() {
		StringBuffer htmlBuffer = new StringBuffer();
		htmlBuffer.append("<h3>" + question + "</h3>");
		for (int i = 0; i < answers.size(); i++) {
			htmlBuffer.append("<input type='text' name='answer_" + getQuizQuestionID() + "_" + i + "' placeholder='answer'/><br/>");
		}
		String html = htmlBuffer.toString();
		return html;
	}

	@Override
	public String getCorrectAnswers() {
		StringBuffer b = new StringBuffer();
		b.append("<p>Correct answers: <br /><ul>");
		for (Set<String> s : answers)
			b.append("<li>" + s.iterator().next() + "</li><br />");
		b.append("</ul>");
		return b.toString();
	}

	@Override
	public int grade(Object response) {
		int score = 0;
		if (ordered) {
			for (int i = 0; i < answers.size(); i++) {
				boolean correct = false;
				for (String answer : answers.get(i)) {
					if (Util.strip(answer).equals(Util.strip(((List<String>) response).get(i)))) correct = true;
				}
				if (correct) score++;
			}
		} else {
			for (Set<String> possibleAnswers : answers) {
				boolean found = false;
				
				for (String possibleAnswer : possibleAnswers) {
					for (String singleResponse : (List<String>) response) {
						if (Util.strip(singleResponse).equals(Util.strip(possibleAnswer)))
							found = true;
					}
				}
				
				if (found) {
					score++;
				}
			}
		}
		return score;
	}

	@Override
	public int getMaxScore() {
		return answers.size();
	}

	public int getNumPrompts() {
		return answers.size();
	}
}
