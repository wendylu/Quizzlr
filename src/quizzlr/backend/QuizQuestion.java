/**
 * Quizzlr
 */
package quizzlr.backend;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

/**
 * Class that represents a Quiz Question from the database.
 * All quiz question types will be derived from this class.
 * All manipulations performed will be immediately reflected in the database,
 * and all property gets will use data from the database.
 * @author David Lo
 *
 */
public abstract class QuizQuestion implements Serializable {
	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	private int quizQuestionID;

    // Getters
    /**
     * @return the quiz question ID
     */
    public final int getQuizQuestionID() {
		return quizQuestionID;
	}

    /**
     * @return the quiz ID
     */
    public final int getQuizID() {
		return DB.getIntProperty("quizQuestions", "quizID", "quizQuestionID", getQuizQuestionID());
	}

    /**
     * @return the question number
     */
    public final int getQuestionNumber() {
		return DB.getIntProperty("quizQuestions", "quizID", "questionNumber", getQuizQuestionID());
	}

    // Setters
    /**
     * Sets a new question number for this quiz
     * @param newQuestionNumber
     */
    public final void setQuestionNumber(int newQuestionNumber) {
    	DB.setIntProperty("quizQuestions", "quizID", "questionNumber", getQuizQuestionID(), newQuestionNumber);
	}

    // Make QuizQuestion hashable
    /* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public final int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + quizQuestionID;
		return result;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public final boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (!(obj instanceof QuizQuestion)) {
			return false;
		}
		QuizQuestion other = (QuizQuestion) obj;
		if (quizQuestionID != other.quizQuestionID) {
			return false;
		}
		return true;
	}

	// Object convenience methods
    /**
     * Saves this quiz question into the database
     */
    public final void save() {
    	DB.setBytesProperty("quizQuestions", "questionObject", "quizQuestionID", getQuestionNumber(), serializeToBytes());
    }

    /**
     * Returns an HTML representation of this question that the quiz taker
     * will see
     * @return
     */
    public abstract String toHTML();

    /**
     * Returns an HTML representation of the answers of this quiz
     * @return
     */
    public abstract String getCorrectAnswers();

    /**
     * Grades the users response for this question
     * @param response
     * @return score of response
     */
    public abstract int grade(Object response);

    /**
     * @return the maximum score for this question
     */
    public abstract int getMaxScore();

    // Static convenience methods
    /**
     * Gets a quiz question from its ID. If no such quiz question exists,
     * returns null.
     * @param quizQuestionID
     * @return
     */
    public static final QuizQuestion getQuizQuestionFromID(int quizQuestionID) {
    	if (DB.doesItemExist("quizQuestions", "quizQuestionID", quizQuestionID)) {
    		byte[] bytes = DB.getBytesProperty("quizQuestions", "questionObject", "quizQuestionID", quizQuestionID);
    		QuizQuestion question = deserializeFromBytes(bytes);
    		question.quizQuestionID = quizQuestionID;

    		return question;
    	}

		return null;
	}

    // Helper methods
    /**
     * @return serialized form of this object as a byte array
     */
    final byte[] serializeToBytes() {
    	ByteArrayOutputStream baos = new ByteArrayOutputStream();
		ObjectOutputStream oout;
		try {
			oout = new ObjectOutputStream(baos);
			oout.writeObject(this);
			oout.close();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}

		return baos.toByteArray();
    }

    /**
     * Deserializes this question from its byte array representation
     * @param bytes
     * @return deserialized quiz question
     */
    static final QuizQuestion deserializeFromBytes(byte[] bytes) {
    	if (bytes != null) {
			try {
				ObjectInputStream objectIn = new ObjectInputStream(new ByteArrayInputStream(bytes));
				return (QuizQuestion)objectIn.readObject();
			} catch (IOException e) {
				throw new RuntimeException(e);
			} catch (ClassNotFoundException e) {
				throw new RuntimeException(e);
			}
		}
		return null;
    }
}