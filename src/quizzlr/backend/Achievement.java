/**
 * Quizzlr
 */
package quizzlr.backend;

import java.sql.Timestamp;

/**
 * Class that represents an Achievement from the database.
 * All manipulations performed will be immediately reflected in the database,
 * and all property gets will use data from the database.
 * @author David Lo
 *
 */
public class Achievement {
	private User user;
	private AchievementType achievementType;
	private Timestamp timestamp;

	// Constructor
	/**
	 * Creates new achievement for a user
	 * @param user
	 * @param achievementType
	 * @param timestamp
	 */
	protected Achievement(User user, AchievementType achievementType, Timestamp timestamp) {
		this.user = user;
		this.achievementType = achievementType;
		this.timestamp = timestamp;
	}

	// Getters
	/**
	 * @return user
	 */
	public User getUser() {
		return user;
	}

	/**
	 * @return achievement type
	 */
	public AchievementType getAchievementType() {
		return achievementType;
	}

	/**
	 * @return timestamp
	 */
	public Timestamp getTimestamp() {
		return timestamp;
	}

	// Setters

	// Object convenience methods

	// Static convenience methods
}
