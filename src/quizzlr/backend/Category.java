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

/**
 * Class that represents a Quiz Category from the database.
 * All manipulations performed will be immediately reflected in the database,
 * and all property gets will use data from the database.
 * @author David Lo
 *
 */
public class Category {
	private int categoryID;

	/**
	 * Creates a Category object that references the database
	 * @param categoryID
	 */
	private Category(int categoryID) {
		this.categoryID = categoryID;
	}

    // Getters
    /**
     * @return the category ID
     */
    public int getCategoryID() {
		return categoryID;
	}

    /**
     * @return the title
     */
    public String getTitle() {
		return DB.getStringProperty("categories", "title", "categoryID", getCategoryID());
	}

    /**
     * @return the description
     */
    public String getDescription() {
    	return DB.getStringProperty("categories", "description", "categoryID", getCategoryID());
	}

    // Setters
    /**
     * Sets the title of the category
     * @param newTitle
     */
    public void setTitle(String newTitle) {
    	DB.setStringProperty("categories", "title", "categoryID", getCategoryID(), newTitle);
	}

    /**
     * Sets the description of the category
     * @param newDescription
     */
    public void setDescription(String newDescription) {
    	DB.setStringProperty("categories", "title", "categoryID", getCategoryID(), newDescription);
	}

    // Make Category hashable
    /* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + categoryID;
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
		if (!(obj instanceof Category)) {
			return false;
		}
		Category other = (Category) obj;
		if (categoryID != other.categoryID) {
			return false;
		}
		return true;
	}

	// Object convenience methods
    /**
     * Gets a set of all quizzes that fall into this category
     * @return
     */
    public Set<Quiz> getQuizzes() {
    	return DB.getMatchedSetFiltered("quizzes", "categoryID", getCategoryID(), "isLive=1", new ObjectIDTranslator<Quiz>() {
			@Override
			public Quiz translate(ResultSet rs) throws SQLException {
				return Quiz.getQuizFromID(rs.getInt("quizID"));
			}
		});
	}

    /**
     * Puts a quiz into this category
     * @param quiz
     */
    public void categorizeQuiz(Quiz quiz) {
    	DB.setIntProperty("quizzes", "categoryID", "quizID", quiz.getQuizID(), getCategoryID());
	}

    /**
     * Deletes this category from the database
     */
    public void delete() {
    	DB.deleteItem("categories", "categoryID", getCategoryID());
    }

    // Static convenience methods
    /**
     * Gets all categories with a title that contains searchStr
     * as a substring
     * @param searchStr
     * @return set of categories with matching title
     */
    public static Set<Category> matchingTitles(String searchStr) {
    	HashSet<Category> results = new HashSet<Category>();

    	PreparedStatement stmt = null;
		try {
			stmt = DB.getPreparedStatement("SELECT categoryID FROM categories WHERE title LIKE ?");
			stmt.setString(1, "%" + searchStr + "%");
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				results.add(getCategoryFromID(rs.getInt("categoryID")));
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
     * Gets all categories with a description that contains searchStr
     * as a substring
     * @param searchStr
     * @return set of categories with matching title
     */
    public static Set<Category> matchingDescriptions(String searchStr) {
    	HashSet<Category> results = new HashSet<Category>();

    	PreparedStatement stmt = null;
		try {
			stmt = DB.getPreparedStatement("SELECT categoryID FROM categories WHERE description LIKE ?");
			stmt.setString(1, "%" + searchStr + "%");
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				results.add(getCategoryFromID(rs.getInt("categoryID")));
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
     * Creates a new category
     * @param title
     * @param description
     * @return
     */
    public static Category createCategory(String title, String description) {
    	PreparedStatement stmt = null;
		try {
			stmt = DB.getPreparedStatement("INSERT INTO categories (title, description) VALUES(?,?)");
			stmt.setString(1, title);
			stmt.setString(2, description);
			stmt.executeUpdate();

			ResultSet rs = stmt.getGeneratedKeys();
			if (rs.next()) {
				int newCategoryID = rs.getInt(1);
				return new Category(newCategoryID);
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
     * Gets all categories in the system
     * @return
     */
    public static Set<Category> getAllCategories() {
    	return DB.getMatchedSet("categories", "1", 1, new ObjectIDTranslator<Category>() {
			@Override
			public Category translate(ResultSet rs) throws SQLException {
				return Category.getCategoryFromID(rs.getInt("categoryID"));
			}
		});
    }

    /**
     * Gets a category by its ID. Returns null if no such category exists.
     * @param categoryID
     * @return
     */
    public static Category getCategoryFromID(int categoryID) {
    	if (DB.doesItemExist("categories", "categoryID", categoryID)) {
    		return new Category(categoryID);
    	}

		return null;
	}
}
