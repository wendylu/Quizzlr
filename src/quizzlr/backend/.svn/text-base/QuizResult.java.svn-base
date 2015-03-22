/**
 * Quizzlr
 */
package quizzlr.backend;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;

import quizzlr.backend.DB.ObjectIDTranslator;

/**
 * Class that represents a Quiz Result from the database.
 * All manipulations performed will be immediately reflected in the database,
 * and all property gets will use data from the database.
 * @author David Lo
 *
 */
public class QuizResult {
	private int quizResultID;

	// Constructor
	/** Creates a QuizResult object that references the database
	 * @param quizResultID
	 */
	protected QuizResult(int quizResultID) {
		this.quizResultID = quizResultID;
	}

    // Getters
    /**
     * @return the quiz result ID
     */
    public int getQuizResultID() {
		return quizResultID;
	}

    /**
     * @return the user who took the quiz
     */
    public User getUser() {
    	return User.getUserFromID(DB.getIntProperty("quizResults", "userID", "quizResultID", getQuizResultID()));
	}

    /**
     * @return the quiz
     */
    public Quiz getQuiz() {
    	return Quiz.getQuizFromID(DB.getIntProperty("quizResults", "quizID", "quizResultID", getQuizResultID()));
	}

    /**
     * @return overall score
     */
    public int getScore() {
		return DB.getIntProperty("quizResults", "score", "quizResultID", getQuizResultID());
	}

    /**
     * @return time when quiz was finished
     */
    public Timestamp getTimestamp() {
    	return DB.getTimestampProperty("quizResults", "timestamp", "quizResultID", getQuizResultID());
	}

    /**
     * @return how long it took to finish the quiz
     */
    public long getElapsedTime() {
    	return DB.getLongProperty("quizResults", "elapsedTime", "quizResultID", getQuizResultID());
	}

    /**
     * @return percent of questions correct
     */
    public float getPercentCorrect() {
    	return DB.getFloatProperty("quizResults", "percentCorrect", "quizResultID", getQuizResultID());
	}

    /**
     * @return if the quiz has been modified since the user took it
     */
    public boolean getIsOutOfDate() {
    	return DB.getBooleanProperty("quizResults", "isOutOfDate", "quizResultID", getQuizResultID());
	}

    // Setters
    /**
     * Sets if a quiz is out of date
     * If people modify a quiz, then result is now out of date
     * @param newIsOutOfDate
     */
    public void setIsOutOfDate(boolean newIsOutOfDate) {
    	DB.setBooleanProperty("quizResults", "isOutOfDate", "quizResultID", getQuizResultID(), newIsOutOfDate);
	}

    // Object convenience methods

    // Static convenience methods
    /**
     * Creates a new quiz result and returns it
     * @param user
     * @param quiz
     * @param score
     * @param elapsedTime
     * @param percentCorrect
     * @param isPractice
     * @return the created quiz result
     */
    public static QuizResult createQuizResult(User user, Quiz quiz, int score, long elapsedTime, float percentCorrect) {
    	PreparedStatement stmt = null;
		try {
			stmt = DB.getPreparedStatement("INSERT INTO quizResults (userID,quizID,score,elapsedTime,percentCorrect) VALUES(?,?,?,?,?)");
			stmt.setInt(1, user.getUserID());
			stmt.setInt(2, quiz.getQuizID());
			stmt.setInt(3, score);
			stmt.setLong(4, elapsedTime);
			stmt.setFloat(5, percentCorrect);
			stmt.executeUpdate();

			ResultSet rs = stmt.getGeneratedKeys();
			if (rs.next()) {
				int newQuizResultID = rs.getInt(1);
				return new QuizResult(newQuizResultID);
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

    /**
     * Gets QuizResult from its ID. If no such quiz result exists, returns
     * null.
     * @param quizResultID
     * @return
     */
    public static QuizResult getQuizResultFromID(int quizResultID) {
    	if (DB.doesItemExist("quizResults", "quizResultID", quizResultID)) {
    		return new QuizResult(quizResultID);
    	}

		return null;
	}
    
    /**
     * Gets the top performing scores in the past X days
     * @param daysInPast
     * @param limit max number of items to get
     * @return
     */
    public static List<QuizResult> getTopResults(int daysInPast, int limit) {
    	return DB.getListSortedFiltered("quizResults", "percentCorrect DESC LIMIT " + limit, "DATEDIFF(CURDATE(), timestamp) <= " + daysInPast, new ObjectIDTranslator<QuizResult>() {
			@Override
			public QuizResult translate(ResultSet rs) throws SQLException {
				return QuizResult.getQuizResultFromID(rs.getInt("quizResultID"));
			}
		});
    }
}
