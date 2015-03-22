/**
 * Quizzlr
 */
package quizzlr.backend;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import quizzlr.backend.DB.ObjectIDTranslator;

/**
 * Class that represents a Quiz from the database. This Quiz is a not a real
 * quiz that the end user will see, but rather is an abstract template for
 * concrete GeneratedQuizzes that the user will see.
 * All manipulations performed will be immediately reflected in the database,
 * and all property gets will use data from the database.
 * @author David Lo
 *
 */
public class Quiz {
	private int quizID;

	/**
	 * Creates a quiz object that references the database
	 * @param quizID
	 */
	private Quiz(int quizID) {
		this.quizID = quizID;
	}

	// Getters
    /**
     * @return the quiz id
     */
    public int getQuizID() {
		return quizID;
	}

    /**
     * @return the title
     */
    public String getTitle() {
		String title = DB.getStringProperty("quizzes", "title", "quizID", getQuizID());
		// Title could be empty, it seems
		if (title == null) {
			return "";
		}

		return title;
	}

    /**
     * @return the description
     */
    public String getDescription() {
		return DB.getStringProperty("quizzes", "description", "quizID", getQuizID());
	}

    /**
     * @return the user that created the quiz
     */
    public User getCreator() {
		return User.getUserFromID(DB.getIntProperty("quizzes", "creatorID", "quizID", getQuizID()));
	}

    /**
     * @return if the quiz question order is random
     */
    public boolean getIsRandomOrder() {
		return DB.getBooleanProperty("quizzes", "isRandomOrder", "quizID", getQuizID());
	}

    /**
     * @return if the quiz questions should all be displayed on one page
     */
    public boolean getIsOnePageFormat() {
    	return DB.getBooleanProperty("quizzes", "isOnePageFormat", "quizID", getQuizID());
	}

    /**
     * @return if the quiz should grade responses immediately
     */
    public boolean getIsImmediateCorrection() {
    	return DB.getBooleanProperty("quizzes", "isImmediateCorrection", "quizID", getQuizID());
	}

    /**
     * @return if the quiz allows practice mode
     */
    public boolean getIsPracticeModeEnabled() {
    	return DB.getBooleanProperty("quizzes", "isPracticeModeEnabled", "quizID", getQuizID());
	}

    /**
     * @return the last update time
     */
    public Timestamp getLastUpdateTime() {
    	return DB.getTimestampProperty("quizzes", "lastUpdateTime", "quizID", getQuizID());
	}

    /**
     * @return if the quiz is live to the rest of the world
     */
    public boolean getIsLive() {
    	return DB.getBooleanProperty("quizzes", "isLive", "quizID", getQuizID());
	}
    
    /**
     * Gets the category
     * @return
     */
    public Category getCategory() {
    	return Category.getCategoryFromID(DB.getIntProperty("quizzes", "categoryID", "quizID", getQuizID()));
    }

    // Setters
    /**
     * Sets the title
     * @param newTitle
     */
    public void setTitle(String newTitle) {
    	DB.setStringProperty("quizzes", "title", "quizID", getQuizID(), newTitle);
	}

    /**
     * Sets the description
     * @param newDescription
     */
    public void setDescription(String newDescription) {
    	DB.setStringProperty("quizzes", "description", "quizID", getQuizID(), newDescription);
	}
    
    /**
     * Sets the category
     * @param category
     */
    public void setCategory(Category category) {
    	DB.setIntProperty("quizzes", "categoryID", "quizID", getQuizID(), category.getCategoryID());
    }

    /**
     * Sets if the quiz questions should be shown in random order
     * @param newIsRandomOrder
     */
    public void setIsRandomOrder(boolean newIsRandomOrder) {
    	DB.setBooleanProperty("quizzes", "isRandomOrder", "quizID", getQuizID(), newIsRandomOrder);
	}

    /**
     * Sets if the quiz questions should be shown on one page
     * @param newIsOnePageFormat
     */
    public void setIsOnePageFormat(boolean newIsOnePageFormat) {
    	DB.setBooleanProperty("quizzes", "isOnePageFormat", "quizID", getQuizID(), newIsOnePageFormat);
	}

    /**
     * Sets if the quiz questions should be graded as responses come in
     * @param newIsImmediateCorrection
     */
    public void setIsImmediateCorrection(boolean newIsImmediateCorrection) {
    	DB.setBooleanProperty("quizzes", "isImmediateCorrection", "quizID", getQuizID(), newIsImmediateCorrection);
	}

    /**
     * Sets if practice mode is allowed
     * @param newIsPracticeModeEnabled
     */
    public void setIsPracticeModeEnabled(boolean newIsPracticeModeEnabled) {
    	DB.setBooleanProperty("quizzes", "isPracticeModeEnabled", "quizID", getQuizID(), newIsPracticeModeEnabled);
	}

    /**
     * Sets if the quiz is visible to the rest of the world
     * @param newIsLive
     */
    public void setIsLive(boolean newIsLive) {
    	DB.setBooleanProperty("quizzes", "isLive", "quizID", getQuizID(), newIsLive);
	}

    // Make Quiz hashable
    /* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + quizID;
		return result;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (!(obj instanceof Quiz)) {
			return false;
		}
		Quiz other = (Quiz) obj;
		if (quizID != other.quizID) {
			return false;
		}
		return true;
	}

	// Object convenience methods
    /**
     * Adds a quiz question to the end of the quiz
     * @param question
     */
    public void addQuestion(QuizQuestion question) {
    	// Get position it should be inserted
    	List<QuizQuestion> existingQuestions = getQuestions();
    	int requestedPosition = existingQuestions.size();

    	addQuestion(question, requestedPosition);
	}

    /**
     * Adds a quiz question to the quiz in the requested position
     * @param question
     * @param requestedPosition
     */
    public void addQuestion(QuizQuestion question, int requestedPosition) {
    	PreparedStatement stmt = null;
		try {
			// Scoot all questions after requestedPosition down
			stmt = DB.getPreparedStatement("UPDATE quizQuestions SET questionNumber=questionNumber+1 WHERE quizID>=? AND questionNumber>=?");
			stmt.setInt(1, getQuizID());
			stmt.setInt(2, requestedPosition);
			stmt.executeUpdate();
			// Add question into proper place
			stmt = DB.getPreparedStatement("INSERT INTO quizQuestions (quizID,questionNumber,questionObject) VALUES(?,?,?)");
			stmt.setInt(1, getQuizID());
			stmt.setInt(2, requestedPosition);
			stmt.setBytes(3, question.serializeToBytes());
			stmt.executeUpdate();
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
    }

    /**
     * Removes a quiz question
     * @param question
     */
    public void removeQuestion(QuizQuestion question) {
    	PreparedStatement stmt = null;
		try {
			// Remove the question
			DB.deleteItem("quizQuestions", "quizQuestionID", question.getQuizID());

			// Scoot all questions after the quiz question up
			stmt = DB.getPreparedStatement("UPDATE quizQuestions SET questionNumber=questionNumber-1 WHERE quizID>=?");
			stmt.setInt(1, question.getQuestionNumber());
			stmt.executeUpdate();
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
	}

    /**
     * Gets all questions of this quiz
     * @return
     */
    public List<QuizQuestion> getQuestions() {
		return DB.getMatchedListSorted("quizQuestions", "quizID", getQuizID(), "questionNumber ASC", new DB.ObjectIDTranslator<QuizQuestion>() {
			@Override
			public QuizQuestion translate(ResultSet rs) throws SQLException {
				return QuizQuestion.getQuizQuestionFromID(rs.getInt("quizQuestionID"));
			}
		});
	}

    public QuizQuestion getQuestion(int q) {
    	return DB.getMatchedListSorted("quizQuestions", "quizID", getQuizID(), "questionNumber ASC", new DB.ObjectIDTranslator<QuizQuestion>() {
			@Override
			public QuizQuestion translate(ResultSet rs) throws SQLException {
				return QuizQuestion.getQuizQuestionFromID(rs.getInt("quizQuestionID"));}}).get(q);
    }

    /**
     * Gets all quiz results of this quiz, ignoring practice quizzes
     * @return
     */
    public List<QuizResult> getQuizHistory() {
    	return DB.getMatchedListSorted("quizResults", "quizID", getQuizID(), "timestamp DESC", new DB.ObjectIDTranslator<QuizResult>() {
			@Override
			public QuizResult translate(ResultSet rs) throws SQLException {
				return QuizResult.getQuizResultFromID(rs.getInt("quizResultID"));
			}
		});
	}
    
    /**
     * Gets the top scores on the quiz
     * @param limit
     * @return
     */
    public List<QuizResult> getTopScores(int limit) {
    	return DB.getMatchedListSortedLimit("quizResults", "quizID", getQuizID(), "score DESC, elapsedTime ASC", new DB.ObjectIDTranslator<QuizResult>() {
			@Override
			public QuizResult translate(ResultSet rs) throws SQLException {
				return QuizResult.getQuizResultFromID(rs.getInt("quizResultID"));
			}
		}, limit);
    }

    /**
     * Clears all quiz history
     */
    public void clearQuizHistory() {
    	DB.deleteItems("quizResults", "quizID", getQuizID());
    }

    /**
     * Gets the high score on this quiz (ignores practice mode results)
     * @return
     */
    public int getHighScore() {
    	PreparedStatement stmt = null;
		try {
    		stmt = DB.getPreparedStatement("SELECT MAX(score) FROM quizResults WHERE quizID=?");
			stmt.setInt(1, getQuizID());
    		ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
            	return rs.getInt(1);
            }

            // No previous scores
            return 0;
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
    }

    /**
     * Gets all users that have taken this quiz
     * @return
     */
    public Set<User> getQuizTakers() {
    	return DB.getMatchedSet("quizResults", "quizID", getQuizID(), new DB.ObjectIDTranslator<User>() {
			@Override
			public User translate(ResultSet rs) throws SQLException {
				return User.getUserFromID(rs.getInt("userID"));
			}
		});
	}

    /**
     * Gets all tags this quiz belongs to
     * @return
     */
    public Set<Tag> getTags() {
    	return DB.getMatchedSet("quizTags", "quizID", getQuizID(), new DB.ObjectIDTranslator<Tag>() {
			@Override
			public Tag translate(ResultSet rs) throws SQLException {
				return Tag.getTagFromID(rs.getInt("tagID"));
			}
		});
	}

    /**
     * Add tag to this quiz
     * @param tag
     */
    public void addTag(Tag tag) {
    	tag.tagQuiz(this);
	}

    /**
     * Removes tag from this quiz
     * @param tag
     */
    public void removeTag(Tag tag) {
    	tag.untagQuiz(this);
	}
    
    /**
     * Clears all tags from this quiz
     */
    public void clearTags() {
    	DB.deleteItems("quizTags", "quizID", getQuizID());
    }
    
    
    /**
     * Gets the rating of a quiz
     * @return
     */
    public int getRating() {
    	PreparedStatement stmt = null;
		try {
			stmt = DB.getPreparedStatement("SELECT IFNULL(SUM(rating),0) FROM quizRatings WHERE quizID=?");
			stmt.setInt(1, getQuizID());
			ResultSet rs = stmt.executeQuery();
			if (rs.next()) {
				return rs.getInt(1);
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

		return 0;
    }
    
    /**
     * Gets all the ratings of this quiz
     * @return
     */
    public List<QuizRating> getRatings() {
    	return DB.getMatchedListSorted("quizRatings", "quizID", getQuizID(), "quizRatingID DESC", new ObjectIDTranslator<QuizRating>() {
			@Override
			public QuizRating translate(ResultSet rs) throws SQLException {
				return QuizRating.getQuizRatingByID(rs.getInt("quizRatingID"));
			}
		});
    }
    
    public static class InappropriateReport {
    	public final User user;
    	public final Quiz quiz;
    	public final String reason;
    	
    	InappropriateReport(User user, Quiz quiz, String reason) {
    		this.user = user;
    		this.quiz = quiz;
    		this.reason = reason;
    	}
    }
    /**
     * Gets a set of reports that say this quiz is inappropriate
     * @return
     */
    public Set<InappropriateReport> getInappropriateReports() {
    	return DB.getMatchedSet("badQuizFlags", "quizID", getQuizID(), new ObjectIDTranslator<InappropriateReport>() {
			@Override
			public InappropriateReport translate(ResultSet rs)
					throws SQLException {
				return new InappropriateReport(User.getUserFromID(rs.getInt("userID")), Quiz.this, rs.getString("reason"));
			}
		});
    }
    
    /**
     * Submits a report on a quiz being inappropriate
     * @param user
     * @param reason
     */
    public void makeInappropriateReport(User user, String reason) {
    	PreparedStatement stmt = null;
		try {
			stmt = DB.getPreparedStatement("REPLACE INTO badQuizFlags (userID,quizID,reason) VALUES(?,?,?)");
			stmt.setInt(1, user.getUserID());
			stmt.setInt(2, getQuizID());
			stmt.setString(3, reason);
			stmt.executeUpdate();
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
    }

    /**
     * Deletes this quiz
     */
    public void delete() {
    	DB.deleteItem("quizzes", "quizID", getQuizID());
    }

    /**
     * Generates an actual quiz that will be used to test the end user
     * @param isPractice if quiz is to be taken in practice mode
     * @return
     */
    public GeneratedQuiz generateQuiz(boolean isPractice) {
    	if (isPractice && !getIsPracticeModeEnabled()) {
    		throw new IllegalArgumentException("Practice mode is not enabled on this quiz");
    	}
    	return new GeneratedQuiz(this, isPractice);
    }

    // Static convenience methods
    /**
     * Creates a new quiz
     * @param title
     * @param description
     * @param category
     * @param creator
     * @param isRandomOrder
     * @param isOnePageFormat
     * @param isImmediateCorrection
     * @param isPracticeModeEnabled
     * @return the new quiz
     */
    public static Quiz createQuiz(String title, String description, Category category, User creator, boolean isRandomOrder, boolean isOnePageFormat, boolean isImmediateCorrection, boolean isPracticeModeEnabled) {
    	PreparedStatement stmt = null;
		try {
			stmt = DB.getPreparedStatement("INSERT INTO quizzes (title,description,creatorID,isRandomOrder,isOnePageFormat,isImmediateCorrection,isPracticeModeEnabled,categoryID) VALUES(?,?,?,?,?,?,?,?)");
			stmt.setString(1, title);
			stmt.setString(2, description);
			stmt.setInt(3, creator.getUserID());
			stmt.setBoolean(4, isRandomOrder);
			stmt.setBoolean(5, isOnePageFormat);
			stmt.setBoolean(6, isImmediateCorrection);
			stmt.setBoolean(7, isPracticeModeEnabled);
			stmt.setInt(8, category.getCategoryID());
			stmt.executeUpdate();

			ResultSet rs = stmt.getGeneratedKeys();
			if (rs.next()) {
				int newQuizID = rs.getInt(1);
				return new Quiz(newQuizID);
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
     * Gets all quizzes with a title that contains searchStr
     * as a substring
     * @param searchStr
     * @return set of quizzes with matching title
     */
    public static Set<Quiz> matchingTitles(String searchStr) {
    	HashSet<Quiz> results = new HashSet<Quiz>();

    	PreparedStatement stmt = null;
		try {
			stmt = DB.getPreparedStatement("SELECT quizID FROM quizzes WHERE title LIKE ?");
			stmt.setString(1, "%" + searchStr + "%");
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				results.add(getQuizFromID(rs.getInt("quizID")));
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

		return results;
    }

    /**
     * Gets all quizzes with a description that contains searchStr
     * as a substring
     * @param searchStr
     * @return set of quizzes with matching description
     */
    public static Set<Quiz> matchingDescriptions(String searchStr) {
    	HashSet<Quiz> results = new HashSet<Quiz>();

    	PreparedStatement stmt = null;
		try {
			stmt = DB.getPreparedStatement("SELECT quizID FROM quizzes WHERE description LIKE ? AND isLive=1");
			stmt.setString(1, "%" + searchStr + "%");
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				results.add(getQuizFromID(rs.getInt("quizID")));
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

		return results;
    }

    /**
     * Gets a quiz from its quizID. If no such quiz exists, returns null.
     * @param quizID
     * @return
     */
    public static Quiz getQuizFromID(int quizID) {
    	if (DB.doesItemExist("quizzes", "quizID", quizID)) {
    		return new Quiz(quizID);
    	}

		return null;
	}
    
    /**
     * Gets all popular quizzes, with a limit on number gotten
     * @param limit
     * @return
     */
    public static List<Quiz> getPopularQuizzes(int limit) {
    	Statement stmt = null;
		ArrayList<Quiz> result = new ArrayList<Quiz>();
		try {
    		stmt = DB.getStatement();
    		ResultSet rs = stmt.executeQuery("SELECT quizID, COUNT(*) FROM quizResults GROUP BY quizID ORDER BY COUNT(*) DESC LIMIT " + limit);
            while (rs.next()) {
            	result.add(Quiz.getQuizFromID(rs.getInt("quizID")));
            }

            return result;
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
    }
    
    /**
     * Gets all quizzes, sorted by popularity
     * @return
     */
    public static List<Quiz> getPopularQuizzes() {
    	Statement stmt = null;
		ArrayList<Quiz> result = new ArrayList<Quiz>();
		try {
    		stmt = DB.getStatement();
    		ResultSet rs = stmt.executeQuery("SELECT quizID, COUNT(*) FROM quizResults GROUP BY quizID ORDER BY COUNT(*) DESC");
            while (rs.next()) {
            	result.add(Quiz.getQuizFromID(rs.getInt("quizID")));
            }

            return result;
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
    }
    
    /**
     * Gets the top rated quizzes
     * @param limit
     * @return
     */
    public static List<Quiz> getTopRatedQuizzes(int limit) {
    	Statement stmt = null;
		ArrayList<Quiz> result = new ArrayList<Quiz>();
		try {
    		stmt = DB.getStatement();
    		ResultSet rs = stmt.executeQuery("SELECT * FROM (SELECT quizID, IFNULL(SUM(rating),0) as ratingSum FROM quizRatings GROUP BY quizID ORDER BY IFNULL(SUM(rating),0) DESC) topRated WHERE ratingSum > 0");
            while (rs.next()) {
            	result.add(Quiz.getQuizFromID(rs.getInt("quizID")));
            }

            return result;
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
	}
    
    /**
     * Gets all quizzes, sorted by timestamp descending
     * @return
     */
    public static List<Quiz> getAllQuizzes() {
    	return DB.getListSortedFiltered("quizzes", "lastUpdateTime DESC", "isLive=1", new ObjectIDTranslator<Quiz>() {
			@Override
			public Quiz translate(ResultSet rs) throws SQLException {
				return Quiz.getQuizFromID(rs.getInt("quizID"));
			}
		});
    }
    
    /**
     * Gets all recently created quizzes with a limit
     * @param limit
     * @return
     */
    public static List<Quiz> getRecentlyCreated(int limit) {
    	return DB.getListSortedFiltered("quizzes", "lastUpdateTime DESC LIMIT " + limit, "isLive=1", new ObjectIDTranslator<Quiz>() {
			@Override
			public Quiz translate(ResultSet rs) throws SQLException {
				return Quiz.getQuizFromID(rs.getInt("quizID"));
			}
		});
    }
    
    public static class InappropriateSummary {
    	public final Quiz quiz;
    	public final int numReports;
    	
    	InappropriateSummary(Quiz quiz, int numReports) {
			this.quiz = quiz;
			this.numReports = numReports;
		}
    }
    
    /**
     * Gets a summary of inappropriate quiz reports
     * @return
     */
    public static Set<InappropriateSummary> getInappropriateReportSummary() {
    	Set<InappropriateSummary> summary = new HashSet<Quiz.InappropriateSummary>();
    	PreparedStatement stmt = null;
		try {
    		stmt = DB.getPreparedStatement("SELECT quizID, COUNT(*) FROM badQuizFlags GROUP BY quizID");
    		ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
            	summary.add(new InappropriateSummary(Quiz.getQuizFromID(rs.getInt("quizID")), rs.getInt(2)));
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
		
		return summary;
    }
}
