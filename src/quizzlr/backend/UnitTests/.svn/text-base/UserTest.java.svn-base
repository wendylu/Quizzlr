package quizzlr.backend.UnitTests;

import java.sql.Timestamp;
import java.util.Date;
import java.util.Set;

import junit.framework.Assert;

import quizzlr.backend.*;
import org.junit.*;

public class UserTest {
	User user0;
	User user1;
	User user2;
	User user3;
	User user4;
	User tempUser;

	@Before
	public void setUp() throws Exception {
		user0 = User.createUser("user0000000000", "password");
		user1 = User.createUser("user0000000001", "password");
		user2 = User.createUser("user0000000002", "password");
		user3 = User.createUser("user0000000003", "password");
		user4 = User.createUser("user0000000004", "password");
		tempUser = User.createUser("tempuser", "temppasswd");
	}

	@After
	public void tearDown() throws Exception {
		User.deleteUser(user0.getUserID());
		User.deleteUser(user1.getUserID());
		User.deleteUser(user2.getUserID());
		User.deleteUser(user3.getUserID());
		User.deleteUser(user4.getUserID());
		User.deleteUser(tempUser.getUserID());
	}

	@Test
	public void testGetByUsername() {
		User user = User.getUserFromUsername(user0.getUsername());
		Assert.assertEquals(user0, user);
	}

	@Test
	public void testPropertyGetSet() {
		user0.setIsAdmin(true);
		user0.setIsBanned(true);
		user0.setPasswordHash("newhash");
		Timestamp now = new Timestamp(new Date().getTime());
		user0.setLastLoginTime(now);

		Assert.assertEquals(user0.getIsAdmin(), true);
		Assert.assertEquals(user0.getIsBanned(), true);
		Assert.assertEquals(user0.getPasswordHash(), "newhash");
		// Allow for clock skew
		long difference = user0.getLastLoginTime().getTime() - now.getTime();
		Assert.assertTrue(difference < 1000);
	}

	@Test
	public void testFriends() {
		// Add friends
		user0.addFriend(user1);
		user0.addFriend(user2);
		user1.addFriend(user3);
		user4.addFriend(user1);

		// User 0 edges
		Assert.assertTrue(user0.isFriendOf(user1));
		Assert.assertTrue(user0.isFriendOf(user2));
		Assert.assertFalse(user0.isFriendOf(user3));
		Assert.assertFalse(user0.isFriendOf(user4));

		// User1 edges
		Assert.assertTrue(user1.isFriendOf(user0));
		Assert.assertFalse(user1.isFriendOf(user2));
		Assert.assertTrue(user1.isFriendOf(user3));
		Assert.assertTrue(user1.isFriendOf(user4));

		// User 2 edges
		Assert.assertTrue(user2.isFriendOf(user0));
		Assert.assertFalse(user2.isFriendOf(user1));
		Assert.assertFalse(user2.isFriendOf(user3));
		Assert.assertFalse(user2.isFriendOf(user4));

		// User 3 edges
		Assert.assertFalse(user3.isFriendOf(user0));
		Assert.assertTrue(user3.isFriendOf(user1));
		Assert.assertFalse(user3.isFriendOf(user2));
		Assert.assertFalse(user3.isFriendOf(user4));

		// User 4 edges
		Assert.assertFalse(user4.isFriendOf(user0));
		Assert.assertTrue(user4.isFriendOf(user1));
		Assert.assertFalse(user4.isFriendOf(user2));
		Assert.assertFalse(user4.isFriendOf(user3));

		// Delete an edge
		user0.removeFriend(user1);

		Assert.assertFalse(user0.isFriendOf(user1));
		Assert.assertFalse(user1.isFriendOf(user0));
	}

	@Test
	public void testAchievements() {
		// TODO
	}

	@Test
	public void testQuizzes() {
		// TODO
	}

	@Test
	public void testMessages() {
		// Send a message from user0->user1
		Message.sendMessage(user0, user1, 0, "hi there!");

		// Make sure user1 gets it
		Assert.assertEquals(user1.getMessages().size(), 1);
		Message message = user1.getMessages().get(0);

		// Delete message
		user1.deleteMessage(message);

		// Make sure its gone now
		Assert.assertEquals(user1.getMessages().size(), 0);
	}

	@Test
	public void testDeleteUser() {
		User newUser = User.createUser("someuser", "somepass");
		User.deleteUser(newUser.getUserID());
		Assert.assertNull(User.getUserFromID(newUser.getUserID()));
	}
	
	@Test
	public void testUsernameSearch() {
		Set<User> matches = User.matchingUsernames("user0");
		Assert.assertTrue(matches.contains(user0));
		Assert.assertTrue(matches.contains(user1));
		Assert.assertTrue(matches.contains(user2));
		Assert.assertTrue(matches.contains(user3));
		Assert.assertTrue(matches.contains(user4));
		Assert.assertFalse(matches.contains(tempUser));
	}
}
