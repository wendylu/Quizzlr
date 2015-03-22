package quizzlr.quiz;

import java.io.IOException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import quizzlr.backend.Category;
import quizzlr.backend.Quiz;
import quizzlr.backend.Tag;
import quizzlr.backend.User;

/**
 * Servlet implementation class CreateQuiz
 */
@WebServlet("/CreateQuiz")
public class CreateQuiz extends HttpServlet {
	private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public CreateQuiz() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		boolean randomOrder = Boolean.parseBoolean((String) request.getParameter("isRandomOrder"));
		boolean onePageFormat = Boolean.parseBoolean((String) request.getParameter("isOnePageFormat"));
		boolean immediateCorrection = Boolean.parseBoolean((String) request.getParameter("isImmediateCorrection"));
		boolean practiceModeEnabled = Boolean.parseBoolean((String) request.getParameter("isPracticeModeEnabled"));
		
		Category category = Category.getCategoryFromID(Integer.parseInt(request.getParameter("category")));

		HttpSession session = request.getSession();
		User user = (User) session.getAttribute("User");

		String title = (String) request.getParameter("title");
		String description = (String) request.getParameter("description");

		Quiz q = Quiz.createQuiz(title, description, category, user, randomOrder, onePageFormat, immediateCorrection, practiceModeEnabled);
		
		// Add tags
		String[] tags = request.getParameterValues("tags");
		if (tags != null) {
			for (String tag : tags) {
				q.addTag(Tag.getTagFromTitle(tag));
			}
		}
		
		int id = q.getQuizID();

		// XXX: do we want to give achievement when quiz goes live?
		// Give user achievement for creating 1 quiz
		if (!user.hasAchievement(AmateurAuthorAchievementType.getInstance())) {
			user.addAchievement(AmateurAuthorAchievementType.getInstance());
		}

		// Give user achievement for creating 5 quizzes
		if (user.getQuizzesCreated().size() >= 5 && !user.hasAchievement(ProlificAuthorAchievementType.getInstance())) {
			user.addAchievement(ProlificAuthorAchievementType.getInstance());
		}

		// Give user achievement for creating 10 quizzes
		if (user.getQuizzesCreated().size() >= 10 && !user.hasAchievement(ProdigiousAuthorAchievementType.getInstance())) {
			user.addAchievement(ProdigiousAuthorAchievementType.getInstance());
		}

		session.setAttribute("quizId", id);

		RequestDispatcher dispatch = request.getRequestDispatcher("createQuestion.jsp");
		dispatch.forward(request, response);

	}

}
