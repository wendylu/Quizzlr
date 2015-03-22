package quizzlr.backend.UnitTests;

import junit.framework.Assert;

import org.junit.*;

import quizzlr.backend.*;

public class TagTest {
	User user;
	Quiz quiz1;
	Quiz quiz2;
	Quiz quiz3;
	Tag tag1;
	Tag tag2;
	Tag mathsTag;
	Category category1;

	@Before
	public void setup() {
		user = User.createUser("user", "password");
		
		category1 = Category.createCategory("category1", null);

		quiz1 = Quiz.createQuiz("quiz1", "quiz description", category1, user, false, false, false, false);
		quiz2 = Quiz.createQuiz("quiz2", "quiz description", category1, user, false, false, false, false);
		quiz3 = Quiz.createQuiz("quiz3", "quiz description", category1, user, false, false, false, false);

		tag1 = Tag.createTag("tag1");
		tag2 = Tag.createTag("tag2");
		mathsTag = Tag.createTag("maths");
	}

	@After
	public void teardown() {
		User.deleteUser(user.getUserID());

		quiz1.delete();
		quiz2.delete();
		quiz3.delete();

		tag1.delete();
		tag2.delete();
		mathsTag.delete();
		
		category1.delete();
	}

	@Test
	public void testProperties() {
		Assert.assertEquals("tag1", tag1.getTitle());
		Assert.assertEquals("tag2", tag2.getTitle());
	}

	@Test
	public void testTagging() {
		// Tag 1: quiz1, quiz2
		tag1.tagQuiz(quiz1);
		tag1.tagQuiz(quiz2);

		// Tag 2: quiz2, quiz3
		tag2.tagQuiz(quiz2);
		tag2.tagQuiz(quiz3);

		// Test tagging
		Assert.assertTrue(tag1.getQuizzes().contains(quiz1));
		Assert.assertTrue(tag1.getQuizzes().contains(quiz2));
		Assert.assertFalse(tag1.getQuizzes().contains(quiz3));
		Assert.assertFalse(tag2.getQuizzes().contains(quiz1));
		Assert.assertTrue(tag2.getQuizzes().contains(quiz2));
		Assert.assertTrue(tag2.getQuizzes().contains(quiz3));

		// Remove quiz2 from tag2
		tag2.untagQuiz(quiz2);

		// Test tagging
		Assert.assertTrue(tag1.getQuizzes().contains(quiz1));
		Assert.assertTrue(tag1.getQuizzes().contains(quiz2));
		Assert.assertFalse(tag1.getQuizzes().contains(quiz3));
		Assert.assertFalse(tag2.getQuizzes().contains(quiz1));
		Assert.assertFalse(tag2.getQuizzes().contains(quiz2));
		Assert.assertTrue(tag2.getQuizzes().contains(quiz3));
	}
}
