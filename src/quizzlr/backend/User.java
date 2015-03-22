/**
 * Quizzlr
 */
package quizzlr.backend;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

import quizzlr.backend.DB.ObjectIDTranslator;

/**
 * Class that represents a User from the database.
 * All manipulations performed will be immediately reflected in the database,
 * and all property gets will use data from the database.
 * @author David Lo
 *
 */
public class User {
	private static Random rng = new Random();
	private int userID;

	// Private constructor
	/**
	 * Creates a User object that references the database
	 * @param userID
	 */
	private User(int userID) {
		this.userID = userID;
	}

    // Getters
    /**
     * @return the user ID
     */
    public int getUserID() {
    	return userID;
    }

    /**
     * @return the username
     */
    public String getUsername() {
    	return DB.getStringProperty("users", "username", "userID", getUserID());
	}

    /**
     * @return the password hash
     */
    public String getPasswordHash() {
    	return DB.getStringProperty("users", "passwordHash", "userID", getUserID());
	}

    /**
     * @return the password salt
     */
    public String getPasswordSalt() {
    	return DB.getStringProperty("users", "passwordSalt", "userID", getUserID());
	}

    /**
     * @return if the user has admin privileges
     */
    public boolean getIsAdmin() {
    	return DB.getBooleanProperty("users", "isAdmin", "userID", getUserID());
	}

    /**
     * @return if the user is banned
     */
    public boolean getIsBanned() {
    	return DB.getBooleanProperty("users", "isBanned", "userID", getUserID());
	}

    /**
     * @return the last time the user logged in
     */
    public Timestamp getLastLoginTime() {
    	return DB.getTimestampProperty("users", "lastLoginTime", "userID", getUserID());
	}

    // Setters
    /**
     * Sets a new password hash
     * @param newPasswordHash
     */
    public void setPasswordHash(String newPasswordHash) {
    	DB.setStringProperty("users", "passwordHash", "userID", getUserID(), newPasswordHash);
	}

    /**
     * Sets if the user has admin privileges
     * @param newIsAdmin
     */
    public void setIsAdmin(boolean newIsAdmin) {
    	DB.setBooleanProperty("users", "isAdmin", "userID", getUserID(), newIsAdmin);
	}

    /**
     * Sets if the user is banned
     * @param newIsBanned
     */
    public void setIsBanned(boolean newIsBanned) {
    	DB.setBooleanProperty("users", "isBanned", "userID", getUserID(), newIsBanned);
	}

    /**
     * Sets a new last log in time
     * @param newLastLogin
     */
    public void setLastLoginTime(Timestamp newLastLogin) {
    	DB.setTimestampProperty("users", "lastLoginTime", "userID", getUserID(), newLastLogin);
	}

    // Methods that make User hashable
    /* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + userID;
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
		if (!(obj instanceof User)) {
			return false;
		}
		User other = (User) obj;
		if (userID != other.userID) {
			return false;
		}
		return true;
	}

	// Object convenience methods
    /**
     * Checks to see if a user is a friend of another User
     * Please use isFriendOf to check friendship as opposed to
     * getFriends().contains
     * @param otherUser
     * @return
     */
    public boolean isFriendOf(User otherUser) {
    	PreparedStatement stmt = null;
		try {
			stmt = DB.getPreparedStatement("SELECT * FROM friends WHERE x=? AND y=?");
			stmt.setInt(1, getUserID());
			stmt.setInt(2, otherUser.getUserID());
			ResultSet rs = stmt.executeQuery();
			return rs.next();
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
     * Adds another user as a friend
     * @param friend
     */
    public void addFriend(User friend) {
    	PreparedStatement stmt = null;
		try {
			// Put x->y edge
			stmt = DB.getPreparedStatement("INSERT INTO friends (x, y) VALUES(?,?)");
			stmt.setInt(1, getUserID());
			stmt.setInt(2, friend.getUserID());
			stmt.executeUpdate();

			// Put y->x edge
			stmt = DB.getPreparedStatement("INSERT INTO friends (y, x) VALUES(?,?)");
			stmt.setInt(1, getUserID());
			stmt.setInt(2, friend.getUserID());
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
     * Defriends another user
     * @param friend
     */
    public void removeFriend(User friend) {
    	PreparedStatement stmt = null;
		try {
			// Remove x->y edge
			stmt = DB.getPreparedStatement("DELETE FROM friends WHERE x=? AND y=?");
			stmt.setInt(1, getUserID());
			stmt.setInt(2, friend.getUserID());
			stmt.executeUpdate();

			// Remove y->x edge
			stmt = DB.getPreparedStatement("DELETE FROM friends WHERE y=? AND x=?");
			stmt.setInt(1, getUserID());
			stmt.setInt(2, friend.getUserID());
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
     * Deletes a message in this user's inbox
     * @param message
     */
    public void deleteMessage(Message message) {
    	message.delete();
	}

    /**
     * Gets all messages this user has received
     * @return
     */
    public List<Message> getMessages() {
    	return DB.getMatchedListSorted("messages", "toUserID", getUserID(), "sentTimestamp DESC", new ObjectIDTranslator<Message>() {
			@Override
			public Message translate(ResultSet rs) throws SQLException {
				return Message.getMessageFromID(rs.getInt("messageID"));
			}
		});
	}

    /**
     * Gets all friends of this user
     * @return
     */
    public Set<User> getFriends() {
		return DB.getMatchedSet("friends", "x", getUserID(), new ObjectIDTranslator<User>() {
			@Override
			public User translate(ResultSet rs) throws SQLException {
				return User.getUserFromID(rs.getInt("y"));
			}
		});
	}

    /**
     * Gets a set of all achievements this user has gotten
     * @return
     */
    public Set<Achievement> getAchievements() {
    	return DB.getMatchedSet("userAchievements", "userID", getUserID(), new ObjectIDTranslator<Achievement>() {
			@Override
			public Achievement translate(ResultSet rs) throws SQLException {
				AchievementType type = AchievementType.getAchievementTypeFromID(rs.getInt("achievementTypeID"));
				return new Achievement(User.this, type, rs.getTimestamp("timestamp"));
			}
		});
	}

    /**
     * Checks to see if user has already gotten an achievement type
     * @param achievementType
     * @return
     */
    public boolean hasAchievement(AchievementType achievementType) {
    	PreparedStatement stmt = null;
		try {
    		stmt = DB.getPreparedStatement("SELECT * FROM userAchievements WHERE userID=? AND achievementTypeID=?");
			stmt.setInt(1, getUserID());
			stmt.setInt(2, achievementType.getAchievementTypeID());
    		ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
            	return true;
            }

            return false;
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
     * Adds a new achievement type that this user has gotten
     * @param achievementType
     */
    public void addAchievement(AchievementType achievementType) {
    	PreparedStatement stmt = null;
		try {
			stmt = DB.getPreparedStatement("INSERT IGNORE INTO userAchievements (userID,achievementTypeID) VALUES (?,?)");
			stmt.setInt(1, getUserID());
			stmt.setInt(2, achievementType.getAchievementTypeID());
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
     * Removes an achievement type from the achievements this user has gotten
     * @param achievementType
     */
    public void removeAchievement(AchievementType achievementType) {
    	PreparedStatement stmt = null;
		try {
			stmt = DB.getPreparedStatement("DELETE FROM userAchievements WHERE userID=? AND achievementTypeID=?");
			stmt.setInt(1, getUserID());
			stmt.setInt(2, achievementType.getAchievementTypeID());
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
     * Removes an achievement that this user has gotten
     * @param achievement
     */
    public void removeAchievement(Achievement achievement) {
    	removeAchievement(achievement.getAchievementType());
	}

    /**
     * Gets a set of all quizzes this user has created and finished
     * @return
     */
    public Set<Quiz> getQuizzesCreated() {
    	return DB.getMatchedSetFiltered("quizzes", "creatorID", getUserID(), "isLive=1", new ObjectIDTranslator<Quiz>() {
			@Override
			public Quiz translate(ResultSet rs) throws SQLException {
				return Quiz.getQuizFromID(rs.getInt("quizID"));
			}
		});
	}
    
    /**
     * Gets a set of all quizzes this user has created, but hasn't finished
     * @return
     */
    public Set<Quiz> getHalfCreatedQuizzes() {
    	return DB.getMatchedSetFiltered("quizzes", "creatorID", getUserID(), "isLive=0", new ObjectIDTranslator<Quiz>() {
			@Override
			public Quiz translate(ResultSet rs) throws SQLException {
				return Quiz.getQuizFromID(rs.getInt("quizID"));
			}
		});
    }

    /**
     * Gets a set of all quizzes this user has taken
     * @return
     */
    public Set<Quiz> getQuizzesTaken() {
    	return DB.getMatchedSet("quizResults", "userID", getUserID(), new ObjectIDTranslator<Quiz>() {
			@Override
			public Quiz translate(ResultSet rs) throws SQLException {
				return Quiz.getQuizFromID(rs.getInt("quizID"));
			}
		});
	}

    /**
     * Gets a list of QuizResults of quizzes this user has taken
     * @return
     */
    public List<QuizResult> getQuizHistory() {
    	return DB.getMatchedListSorted("quizResults", "userID", getUserID(), "timestamp DESC", new ObjectIDTranslator<QuizResult>() {
			@Override
			public QuizResult translate(ResultSet rs) throws SQLException {
				return QuizResult.getQuizResultFromID(rs.getInt("quizResultID"));
			}
		});
	}
    
    /**
     * Gets a list of QuizResults of quizzes this user's friends have taken
     * @return
     */
	public List<QuizResult> getFriendQuizHistory() {
    	List<QuizResult> results = DB.getListSorted("quizResults", "timestamp DESC", new ObjectIDTranslator<QuizResult>() {
			@Override
			public QuizResult translate(ResultSet rs) throws SQLException {
				return QuizResult.getQuizResultFromID(rs.getInt("quizResultID"));
			}
		});
    	
    	List<QuizResult> output = new ArrayList<QuizResult>();
    	for (QuizResult qr : results) {
    		if (getFriends().contains(qr.getUser())) output.add(qr);
    	}
    	return output;
	}
    

    // Static convenience methods
    /**
     * Creates a new user and returns the newly created user
     * @param username
     * @param password
     * @return newly created user
     */
    public static User createUser(String username, String password) {
    	PreparedStatement stmt = null;
		try {
			Integer passwordSalt = rng.nextInt();
			String passwordHash = Util.getSHA1Hash(password + passwordSalt);

			stmt = DB.getPreparedStatement("INSERT INTO users (username, passwordHash, passwordSalt) VALUES(?,?,?)");
			stmt.setString(1, username);
			stmt.setString(2, passwordHash);
			stmt.setString(3, passwordSalt.toString());
			stmt.executeUpdate();

			ResultSet rs = stmt.getGeneratedKeys();
			if (rs.next()) {
				int newUserID = rs.getInt(1);
				return new User(newUserID);
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
     * Deletes a user by its user ID
     * @param userID
     */
    public static void deleteUser(int userID) {
    	DB.deleteItem("users", "userID", userID);
    }

    /**
     * Gets all users with a username that contains searchStr
     * as a substring
     * @param searchStr
     * @return set of users with matching username
     */
    public static Set<User> matchingUsernames(String searchStr) {
    	HashSet<User> results = new HashSet<User>();

    	PreparedStatement stmt = null;
		try {
			stmt = DB.getPreparedStatement("SELECT userID FROM users WHERE username LIKE ?");
			stmt.setString(1, "%" + searchStr + "%");
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				results.add(getUserFromID(rs.getInt("userID")));
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
     * Gets a user from its ID. If no such user exists, returns null
     * @param userID
     * @return
     */
    public static User getUserFromID(int userID) {
    	if (DB.doesItemExist("users", "userID", userID)) {
    		return new User(userID);
    	}

		return null;
	}

    /**
     * Gets a user from its username. If no such user exists, returns null
     * @param username
     * @return
     */
    public static User getUserFromUsername(String username) {
    	Integer userID = DB.getIntProperty("users", "userID", "username", username);
    	if (userID != null) {
    		return getUserFromID(userID);
    	}

		return null;
	}
    
    /**
     * Gets all banned users in the system
     * @return
     */
    public static Set<User> getBannedUsers() {
    	return DB.getMatchedSet("users", "isBanned", 1, new ObjectIDTranslator<User>() {
			@Override
			public User translate(ResultSet rs) throws SQLException {
				return User.getUserFromID(rs.getInt("userID"));
			}
		});
    }
}
