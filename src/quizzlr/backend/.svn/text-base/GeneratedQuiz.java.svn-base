package quizzlr.backend;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * This class is used to store a generated quiz and to record any responses the
 * user may have submitted
 * @author David Lo
 *
 */
public class GeneratedQuiz {
	public static class QuestionResult {
		public final int Score;
		public final int MaxScore;

		public QuestionResult(int score, int maxScore) {
			Score = score;
			MaxScore = maxScore;
		}
	}

	private List<QuizQuestion> questions;
	private List<Object> responses;
	private Quiz template;
	private boolean isPractice;

	// Constructor
	/**
	 * Creates a GeneratedQuiz object based on a Quiz template
	 * @param template
	 * @param isPractice
	 */
	protected GeneratedQuiz(Quiz template, boolean isPractice) {
		this.template = template;
		this.isPractice = isPractice;
		this.questions = new ArrayList<QuizQuestion>(template.getQuestions());
		this.responses = new ArrayList<Object>();

		// Randomize questions?
		if (template.getIsRandomOrder()) {
			Collections.shuffle(this.questions);
		}

		// Fill up responses list
		for (int i = 0; i < this.questions.size(); i++) {
			responses.add(null);
		}
	}

	// Getters
	/**
	 * @return the questions
	 */
	/**
	 * @return
	 */
	public List<QuizQuestion> getQuestions() {
		return questions;
	}

	/**
	 * @return the responses
	 */
	/**
	 * @return
	 */
	public List<Object> getResponses() {
		return responses;
	}

	/**
	 * @return the isPractice
	 */
	public boolean isPractice() {
		return isPractice;
	}

	/**
	 * @return the template
	 */
	/**
	 * @return
	 */
	public Quiz getTemplate() {
		return template;
	}

	/**
	 * Gets the question at a certain index
	 * @param index
	 * @return a question
	 */
	public QuizQuestion getQuestionAt(int index) {
		return questions.get(index);
	}

	/**
	 * Gets the user response at a certain index
	 * @param index
	 * @return the user response
	 */
	public Object getResponseAt(int index) {
		return responses.get(index);
	}

	// Setters
	/**
	 * Sets the user response for a question index
	 * @param index
	 * @param response
	 */
	public void setResponseAt(int index, Object response) {
		responses.set(index, response);
	}

	// Object convenience methods
	/**
	 * Grades this generated quiz and returns a QuizResult
	 * @param user
	 * @param elapsedTime
	 * @return
	 */
	public QuizResult grade(User user, Long elapsedTime) {
		int totalPoints = 0;
		int maxPoints = 0;
    	for (int i = 0; i < questions.size(); i++) {
    		QuestionResult result = gradeQuestionAt(i);
    		totalPoints += result.Score;
    		maxPoints += result.MaxScore;
    	}
    	
    	// If practice mode, give temporary result
    	if (isPractice()) {
    		return new PracticeQuizResult(user, template, totalPoints, elapsedTime, 100*((float)totalPoints)/maxPoints);
    	}
    	else {
    		return QuizResult.createQuizResult(user, template, totalPoints, elapsedTime, 100*((float)totalPoints)/maxPoints);
    	}
    }

	/**
	 * Grades a question and returns the result
	 * @param index
	 * @return
	 */
	public QuestionResult gradeQuestionAt(int index) {
		QuizQuestion question = questions.get(index);
		int score = question.grade(responses.get(index));
		int maxScore = question.getMaxScore();

		return new QuestionResult(score, maxScore);
	}
}
