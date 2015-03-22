package quizzlr.backend.UnitTests;

import java.util.Set;

import junit.framework.Assert;

import org.junit.*;

import quizzlr.backend.*;

public class CategoryTest {
	User user;
	Quiz quiz1;
	Quiz quiz2;
	Quiz quiz3;
	Category category1;
	Category category2;
	Category mathsCategory;

	@Before
	public void setup() {
		user = User.createUser("user", "password");
		
		category1 = Category.createCategory("category1", null);
		category2 = Category.createCategory("category2", "a cool category");
		mathsCategory = Category.createCategory("maths", "some other description");

		quiz1 = Quiz.createQuiz("quiz1", "quiz description", category1, user, false, false, false, false);
		quiz2 = Quiz.createQuiz("quiz2", "quiz description", category1, user, false, false, false, false);
		quiz3 = Quiz.createQuiz("quiz3", "quiz description", category2, user, false, false, false, false);
	}

	@After
	public void teardown() {
		User.deleteUser(user.getUserID());

		quiz1.delete();
		quiz2.delete();
		quiz3.delete();

		category1.delete();
		category2.delete();
		mathsCategory.delete();
	}

	@Test
	public void testProperties() {
		Assert.assertEquals("category1", category1.getTitle());
		Assert.assertNull(category1.getDescription());
		Assert.assertEquals("category2", category2.getTitle());
		Assert.assertEquals("a cool category", category2.getDescription());
	}

	@Test
	public void testCategorization() {
		// Category 1: quiz1, quiz2
		category1.categorizeQuiz(quiz1);
		category1.categorizeQuiz(quiz2);

		// Category 2: quiz3
		category2.categorizeQuiz(quiz3);

		// Test categorization
		Assert.assertTrue(category1.getQuizzes().contains(quiz1));
		Assert.assertTrue(category1.getQuizzes().contains(quiz2));
		Assert.assertFalse(category1.getQuizzes().contains(quiz3));
		Assert.assertFalse(category2.getQuizzes().contains(quiz1));
		Assert.assertFalse(category2.getQuizzes().contains(quiz2));
		Assert.assertTrue(category2.getQuizzes().contains(quiz3));
	}

	@Test
	public void testSearch() {
		Set<Category> matches = Category.matchingTitles("category");
		Assert.assertTrue(matches.contains(category1));
		Assert.assertTrue(matches.contains(category2));
		Assert.assertFalse(matches.contains(mathsCategory));

		matches = Category.matchingDescriptions("category");
		Assert.assertFalse(matches.contains(category1));
		Assert.assertTrue(matches.contains(category2));
		Assert.assertFalse(matches.contains(mathsCategory));
	}

	@Test
	public void testGetAllCategories() {
		Set<Category> categories = Category.getAllCategories();
		Assert.assertTrue(categories.contains(category1));
		Assert.assertTrue(categories.contains(category2));
		Assert.assertTrue(categories.contains(mathsCategory));
	}
}
