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
public class ProdigiousAuthorAchievementType extends AchievementType {
	/**
	 * The achievement ID for this achievement
	 */
	public static final int AchievementID = 4;

	private static ProdigiousAuthorAchievementType instance = new ProdigiousAuthorAchievementType();

	/**
	 * Constructor
	 */
	private ProdigiousAuthorAchievementType() {
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

	public static ProdigiousAuthorAchievementType getInstance() {
		return instance;
	}

}
