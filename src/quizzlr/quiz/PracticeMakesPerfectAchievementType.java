/**
 *
 */
package quizzlr.quiz;

import quizzlr.backend.AchievementProgress;
import quizzlr.backend.AchievementType;
import quizzlr.backend.User;

/**
 * @author David Lo
 *
 */
public class PracticeMakesPerfectAchievementType extends AchievementType {
	/**
	 * The achievement ID for this achievement
	 */
	public static final int AchievementID = 5;

	private static PracticeMakesPerfectAchievementType instance = new PracticeMakesPerfectAchievementType();

	/**
	 * Constructor
	 */
	private PracticeMakesPerfectAchievementType() {
		super(AchievementID);
	}

	/* (non-Javadoc)
	 * @see quizzlr.backend.AchievementType#getUserProgress(quizzlr.backend.User)
	 */
	@Override
	public AchievementProgress getUserProgress(User user) {
		// TODO Auto-generated method stub
		return null;
	}

	public static PracticeMakesPerfectAchievementType getInstance() {
		return instance;
	}

}
