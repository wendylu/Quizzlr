package quizzlr.backend.UnitTests;

import org.junit.*;
import quizzlr.backend.*;

public class AnnouncementTest {
	User user1;
	User user2;
	Announcement announcement1;
	Announcement announcement2;

	@Before
	public void setup() {
		user1 = User.createUser("user1", "password");
		user2 = User.createUser("user2", "password");

		announcement1 = Announcement.createAnnouncement("announcement1", "content1", user1);
		announcement2 = Announcement.createAnnouncement("announcement2", "content2", user2);
	}

	@After
	public void teardown() {
		User.deleteUser(user1.getUserID());
		User.deleteUser(user2.getUserID());
		announcement1.delete();
		announcement2.delete();
	}

	@Test
	public void testAnnouncements() {
		// Check correct properties
		Assert.assertEquals(announcement1.getTitle(), "announcement1");
		Assert.assertEquals(announcement1.getContent(), "content1");
		Assert.assertEquals(announcement1.getLastEditor(), user1);
		Assert.assertEquals(announcement2.getTitle(), "announcement2");
		Assert.assertEquals(announcement2.getContent(), "content2");
		Assert.assertEquals(announcement2.getLastEditor(), user2);

		// Check correct ordering
		Assert.assertEquals(Announcement.getAllAnnouncements().size(), 2);
		Assert.assertEquals(Announcement.getAllAnnouncements().get(0), announcement2);
		Assert.assertEquals(Announcement.getAllAnnouncements().get(1), announcement1);

		// Check delete
		announcement1.delete();
		Assert.assertEquals(Announcement.getAllAnnouncements().size(), 1);
		Assert.assertEquals(Announcement.getAllAnnouncements().get(0), announcement2);
	}
}
