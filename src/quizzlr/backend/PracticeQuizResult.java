/**
 * 
 */
package quizzlr.backend;

import java.sql.Timestamp;

/**
 * @author David Lo
 *
 */
public class PracticeQuizResult extends QuizResult {
	private User user;
	private Quiz quiz;
	private int score;
	private long elapsedTime;
	private float percentCorrect;
	
	public PracticeQuizResult(User user, Quiz quiz, int score, long elapsedTime, float percentCorrect) {
		super(-1);
		this.user = user;
		this.quiz = quiz;
		this.score = score;
		this.elapsedTime = elapsedTime;
		this.percentCorrect = percentCorrect;
	}

	@Override
	public User getUser() {
		return user;
	}

	@Override
	public Quiz getQuiz() {
		return quiz;
	}

	@Override
	public int getScore() {
		return score;
	}

	@Override
	public Timestamp getTimestamp() {
		return null;
	}

	@Override
	public long getElapsedTime() {
		return elapsedTime;
	}

	@Override
	public float getPercentCorrect() {
		return percentCorrect;
	}

	@Override
	public boolean getIsOutOfDate() {
		return false;
	}

	@Override
	public void setIsOutOfDate(boolean newIsOutOfDate) {
		// Do nothing
	}
}