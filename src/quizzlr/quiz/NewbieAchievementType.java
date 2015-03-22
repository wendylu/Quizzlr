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
public class NewbieAchievementType extends AchievementType {
	/**
	 * The achievement ID for this achievement
	 */
	public static final int AchievementID = 0;

	private static NewbieAchievementType instance = new NewbieAchievementType();

	/**
	 * Constructor
	 */
	private NewbieAchievementType() {
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

	public static NewbieAchievementType getInstance() {
		return instance;
	}

}
