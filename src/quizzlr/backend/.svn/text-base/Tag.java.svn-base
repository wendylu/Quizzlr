/**
 * Quizzlr
 */
package quizzlr.backend;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Set;

import quizzlr.backend.DB.ObjectIDTranslator;
import quizzlr.backend.DB.SQLResultGetter;

/**
 * Class that represents a Quiz Tag from the database.
 * All manipulations performed will be immediately reflected in the database,
 * and all property gets will use data from the database.
 * @author David Lo
 *
 */
public class Tag {
	private int tagID;

	/**
	 * Creates a Tag object that references the database
	 * @param tagID
	 */
	private Tag(int tagID) {
		this.tagID = tagID;
	}

    // Getters
    /**
     * @return the tag ID
     */
    public int getTagID() {
		return tagID;
	}

    /**
     * @return the title
     */
    public String getTitle() {
		return DB.getStringProperty("tags", "title", "tagID", getTagID());
	}

    // Setters
    /**
     * Sets the title of the tag
     * @param newTitle
     */
    public void setTitle(String newTitle) {
    	DB.setStringProperty("tags", "title", "tagID", getTagID(), newTitle);
	}

    // Make Tag hashable
    /* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + tagID;
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
		if (!(obj instanceof Tag)) {
			return false;
		}
		Tag other = (Tag) obj;
		if (tagID != other.tagID) {
			return false;
		}
		return true;
	}

	// Object convenience methods
    /**
     * Gets a set of all quizzes that fall into this tag
     * @return
     */
    public Set<Quiz> getQuizzes() {
    	final Set<Quiz> quizzes = new HashSet<Quiz>();
    	DB.getMatchedSet("quizTags", "tagID", getTagID(), new ObjectIDTranslator<Quiz>() {
			@Override
			public Quiz translate(ResultSet rs) throws SQLException {
				Quiz quiz = Quiz.getQuizFromID(rs.getInt("quizID"));
				if (quiz.getIsLive()) {
					quizzes.add(quiz);
				}
				
				return quiz;
			}
		});
    	
    	return quizzes;
	}

    /**
     * Puts a quiz into this tag
     * @param quiz
     */
    public void tagQuiz(Quiz quiz) {
    	PreparedStatement stmt = null;
		try {
			stmt = DB.getPreparedStatement("INSERT IGNORE INTO quizTags (quizID,tagID) VALUES(?,?)");
			stmt.setInt(1, quiz.getQuizID());
			stmt.setInt(2, getTagID());
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
     * Kicks a quiz out of this tag
     * @param quiz
     */
    public void untagQuiz(Quiz quiz) {
    	PreparedStatement stmt = null;
		try {
			stmt = DB.getPreparedStatement("DELETE FROM quizTags WHERE quizID=? AND tagID=?");
			stmt.setInt(1, quiz.getQuizID());
			stmt.setInt(2, getTagID());
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
     * Deletes this tag from the database
     */
    public void delete() {
    	DB.deleteItem("tags", "tagID", getTagID());
    }

    // Static convenience methods
    /**
     * Gets all tags with a title that contains searchStr
     * as a substring
     * @param searchStr
     * @return set of tags with matching title
     */
    public static Set<Tag> matchingTitles(String searchStr) {
    	HashSet<Tag> results = new HashSet<Tag>();
    	
    	PreparedStatement stmt = null;
		try {
			stmt = DB.getPreparedStatement("SELECT tagID FROM tags WHERE title LIKE ?");
			stmt.setString(1, "%" + searchStr + "%");
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				results.add(getTagFromID(rs.getInt("tagID")));
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
     * Creates a new tag
     * @param title
     * @return
     */
    public static Tag createTag(String title) {
    	title = title.toLowerCase();
    	PreparedStatement stmt = null;
		try {
			stmt = DB.getPreparedStatement("INSERT IGNORE INTO tags (title) VALUES(?)");
			stmt.setString(1, title);
			stmt.executeUpdate();

			ResultSet rs = stmt.getGeneratedKeys();
			if (rs.next()) {
				int newTagID = rs.getInt(1);
				return new Tag(newTagID);
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
     * Gets a tag by its ID. Returns null if no such tag exists.
     * @param tagID
     * @return
     */
    public static Tag getTagFromID(int tagID) {
    	if (DB.doesItemExist("tags", "tagID", tagID)) {
    		return new Tag(tagID);
    	}

		return null;
	}
    
    /**
     * Gets a tag by its title. Creates the tag if no such tag already exists
     * @param title
     * @return
     */
    public static Tag getTagFromTitle(String title) {
    	Tag tag = DB.getProperty("tags", "tagID", "title", title, new SQLResultGetter<Tag>() {
			@Override
			public Tag get(ResultSet rs, String propertyName)
					throws SQLException {
				return getTagFromID(rs.getInt("tagID"));
			}
		});
    	// Try to create tag
    	if (tag == null) {
    		tag = createTag(title);
    	}
    	// Failed-that means someone else created it
    	if (tag == null) {
    		tag = getTagFromTitle(title);
    	}
    	
    	return tag;
    }
}
