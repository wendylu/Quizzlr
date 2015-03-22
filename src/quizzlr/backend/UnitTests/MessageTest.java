package quizzlr.backend.UnitTests;

import junit.framework.Assert;

import org.junit.*;

import quizzlr.backend.*;

public class MessageTest {
	User user0;
	User user1;

	@Before
	public void setUp() throws Exception {
		user0 = User.createUser("user0000000000", "password");
		user1 = User.createUser("user0000000001", "password");
	}

	@After
	public void tearDown() throws Exception {
		User.deleteUser(user0.getUserID());
		User.deleteUser(user1.getUserID());
	}

	@Test
	public void testMessages() {
		// Send a message from user0->user1
		Message.sendMessage(user0, user1, 0, "hi there!");

		// Make sure received properly
		Message message = user1.getMessages().get(0);
		Assert.assertEquals(message.getMessageType(), 0);
		Assert.assertEquals(message.getMessageContent(), "hi there!");
		Assert.assertEquals(message.getFromUser(), user0);
		Assert.assertEquals(message.getToUser(), user1);
		Assert.assertEquals(message.getIsRead(), false);

		// Set isread
		message.setIsRead(true);
		Assert.assertEquals(message.getIsRead(), true);

		// Test delete
		message.delete();
		Assert.assertEquals(user1.getMessages().size(), 0);
	}
}
