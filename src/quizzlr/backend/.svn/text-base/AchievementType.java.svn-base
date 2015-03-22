/**
 * Quizzlr
 */
package quizzlr.backend;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashSet;
import java.util.Set;

import quizzlr.backend.DB.ObjectIDTranslator;
import quizzlr.quiz.AmateurAuthorAchievementType;
import quizzlr.quiz.IAmTheGreatestAchievementType;
import quizzlr.quiz.NewbieAchievementType;
import quizzlr.quiz.PottyMouthAchievementType;
import quizzlr.quiz.PracticeMakesPerfectAchievementType;
import quizzlr.quiz.ProdigiousAuthorAchievementType;
import quizzlr.quiz.ProlificAuthorAchievementType;
import quizzlr.quiz.QuizMachineAchievementType;

/**
 * Class that represents an Achievement Type from the database.
 * All achievement types should be derived from this class. Each achievement
 * type should also be stored explicitly in the database (in the table
 * achievementTypes).
 * The derivation of child AchievementType is to facilitate easy achievement
 * giving (see {@link quizzlr.backend.AchievementType#AchievementType})
 * All manipulations performed will be immediately reflected in the database,
 * and all property gets will use data from the database.
 * @author David Lo
 *
 */
public abstract class AchievementType {
	private int achievementTypeID;

	// Constructor
	/**
	 * This is used by child classes for their own private constructors
	 * Each child class should also implement a static getInstance() method
	 * that produces the instance of that achievement type (singleton model)
	 * @param achievementTypeID
	 */
	protected AchievementType(int achievementTypeID) {
		this.achievementTypeID = achievementTypeID;
	}

	// Getters
	/**
	 * @return achievement type ID
	 */
	public final int getAchievementTypeID() {
		return achievementTypeID;
	}

	/**
	 * @return achievement name
	 */
	public final String getName() {
		return DB.getStringProperty("achievementTypes", "name", "achievementTypeID", getAchievementTypeID());
	}

	/**
	 * @return description of achievement
	 */
	public final String getDescription() {
		return DB.getStringProperty("achievementTypes", "description", "achievementTypeID", getAchievementTypeID());
	}

	/**
	 * @return image icon for achievement
	 */
	public final String getImageIcon() {
		return DB.getStringProperty("achievementTypes", "imageIcon", "achievementTypeID", getAchievementTypeID());
	}

	// Setters
	/**
	 * Sets the name for this achievement
	 * @param newName
	 */
	public final void setName(String newName) {
		DB.setStringProperty("achievementTypes", "name", "achievementTypeID", getAchievementTypeID(), newName);
	}

	/**
	 * Sets the descriptoin for this achievement
	 * @param newDescription
	 */
	public final void setDescription(String newDescription) {
		DB.setStringProperty("achievementTypes", "name", "achievementTypeID", getAchievementTypeID(), newDescription);
	}

	/**
	 * Sets the image icon for this achievement
	 * @param newImageIcon
	 */
	public final void setImageIcon(String newImageIcon) {
		DB.setStringProperty("achievementTypes", "name", "achievementTypeID", getAchievementTypeID(), newImageIcon);
	}

	// Make AchievementType hashable
	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public final int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + achievementTypeID;
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
		if (!(obj instanceof AchievementType)) {
			return false;
		}
		AchievementType other = (AchievementType) obj;
		if (achievementTypeID != other.achievementTypeID) {
			return false;
		}
		return true;
	}

	// Object convenience methods
	/**
	 * Gets a set of all users that have gotten this achievement
	 * @return
	 */
	public final Set<User> getUsersWithAchievement() {
		return DB.getMatchedSet("userAchievements", "achievementTypeID", getAchievementTypeID(), new ObjectIDTranslator<User>() {
			@Override
			public User translate(ResultSet rs) throws SQLException {
				return User.getUserFromID(rs.getInt("userID"));
			}
		});
	}

	/**
	 * Gets the progress a user has progressed towards this achievement
	 * getUserProgress only if we do achievement progress display
	 * @param user
	 * @return
	 */
	public abstract AchievementProgress getUserProgress(User user);

	// Static convenience methods
	/**
	 * Gets an AchievementType object from its integer ID
	 * @param achievementTypeID
	 * @return
	 */
	public final static AchievementType getAchievementTypeFromID(int achievementTypeID) {
		switch (achievementTypeID) {
			case NewbieAchievementType.AchievementID: return NewbieAchievementType.getInstance();
			case IAmTheGreatestAchievementType.AchievementID: return IAmTheGreatestAchievementType.getInstance();
			case AmateurAuthorAchievementType.AchievementID: return AmateurAuthorAchievementType.getInstance();
			case ProlificAuthorAchievementType.AchievementID: return ProlificAuthorAchievementType.getInstance();
			case ProdigiousAuthorAchievementType.AchievementID: return ProdigiousAuthorAchievementType.getInstance();
			case PracticeMakesPerfectAchievementType.AchievementID: return PracticeMakesPerfectAchievementType.getInstance();
			case QuizMachineAchievementType.AchievementID: return QuizMachineAchievementType.getInstance();
			case PottyMouthAchievementType.AchievementID: return PottyMouthAchievementType.getInstance();
			default: return null;
		}
	}

	/**
	 * Gets a set of all AchievementTypes in the system
	 * @return
	 */
	public final static Set<AchievementType> getAllAchievementTypes() {
		Set<AchievementType> results = new HashSet<AchievementType>();
		Statement stmt = null;
		try {
			stmt = DB.getStatement();
			ResultSet rs = stmt.executeQuery("SELECT achievementTypeID FROM achievementTypes");
			while (rs.next()) {
				results.add(AchievementType.getAchievementTypeFromID(rs.getInt("achievementTypeID")));
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
}
