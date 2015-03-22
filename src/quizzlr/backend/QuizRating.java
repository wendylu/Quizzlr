/**
 * 
 */
package quizzlr.backend;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author David Lo
 *
 */
public class QuizRating {
	private int quizRatingID;
	
	public QuizRating(int quizRatingID) {
		this.quizRatingID = quizRatingID;
	}

	public int getQuizRatingID() {
		return quizRatingID;
	}
	
	public Quiz getQuiz() {
		return Quiz.getQuizFromID(DB.getIntProperty("quizRatings", "quizID", "quizRatingID", getQuizRatingID()));
	}
	
	public User getUser() {
		return User.getUserFromID(DB.getIntProperty("quizRatings", "userID", "quizRatingID", getQuizRatingID()));
	}
	
	public int getRating() {
		return DB.getIntProperty("quizRatings", "rating", "quizRatingID", getQuizRatingID());	
	}
	
	public String getReview() {
		String review = DB.getStringProperty("quizRatings", "review", "quizRatingID", getQuizRatingID());
		if (review == null) {
			review = "";
		}
		
		return review;
	}
	
	public static QuizRating createQuizRating(Quiz quiz, User user, int rating, String review) {
		PreparedStatement stmt = null;
		try {
			stmt = DB.getPreparedStatement("REPLACE INTO quizRatings (quizID, userID, rating, review) VALUES(?,?,?,?)");
			stmt.setInt(1, quiz.getQuizID());
			stmt.setInt(2, user.getUserID());
			stmt.setInt(3, rating);
			stmt.setString(4, review);
			stmt.executeUpdate();

			ResultSet rs = stmt.getGeneratedKeys();
			if (rs.next()) {
				int newQuizReviewID = rs.getInt(1);
				return new QuizRating(newQuizReviewID);
			}
		} catch (SQLException e) {
			throw new RuntimeException(e);
		} finally {
			if (stmt != null) {
				try {
					stmt.close();
				} catch (SQLException e) {
					throw new RuntimeException(e);
				}
			}
		}

		return null;
	}
	
	public static QuizRating getQuizRatingByID(int quizRatingID) {
		if (DB.doesItemExist("quizRatings", "quizRatingID", quizRatingID)) {
    		return new QuizRating(quizRatingID);
    	}

		return null;
	}
}
