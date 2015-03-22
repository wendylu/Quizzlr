package quizzlr.quiz;

import java.io.IOException;
import java.util.Date;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import quizzlr.backend.GeneratedQuiz;
import quizzlr.backend.Quiz;
import quizzlr.backend.QuizQuestion;
import quizzlr.backend.QuizResult;
import quizzlr.backend.User;

/**
 * Servlet implementation class GradeOnePageServlet
 */
@WebServlet("/GradeOnePageServlet")
public class GradeOnePageServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public GradeOnePageServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		GeneratedQuiz generatedQuiz = (GeneratedQuiz)session.getAttribute("generatedQuiz");
		Quiz curQuiz = generatedQuiz.getTemplate();
		int totalNumQuestions = curQuiz.getQuestions().size();
		User taker = (User)session.getAttribute("User");
		
		for (int questionNum = 1; questionNum <= totalNumQuestions; questionNum++) {
			QuizQuestion curQuestion = generatedQuiz.getQuestionAt(questionNum-1);
			Object answerResponse = QuizUtil.getUserResponse(curQuestion, request);
			generatedQuiz.setResponseAt(questionNum-1, answerResponse);
			// Award potty mouth achievement
			if (QuizUtil.checkPottyMouthAchievement(answerResponse) && !taker.hasAchievement(PottyMouthAchievementType.getInstance())) {
				taker.addAchievement(PottyMouthAchievementType.getInstance());
			}
		}

		session.removeAttribute("generatedQuiz");

		// Grade the quiz
		Date startTime = (Date)session.getAttribute("startTime");
		Date finishTime = new Date();
		long elapsedTime = finishTime.getTime() - startTime.getTime();
		int previousHighScore = generatedQuiz.getTemplate().getHighScore();
		QuizResult result = generatedQuiz.grade(taker, elapsedTime);

		// Award achievement if practice mode
		if (generatedQuiz.isPractice() && !taker.hasAchievement(PracticeMakesPerfectAchievementType.getInstance())) {
			taker.addAchievement(PracticeMakesPerfectAchievementType.getInstance());
		}

		// Award achievement if not practice, but highest score
		if (!generatedQuiz.isPractice() && result.getScore() > previousHighScore && !taker.hasAchievement(IAmTheGreatestAchievementType.getInstance())) {
			taker.addAchievement(IAmTheGreatestAchievementType.getInstance());
		}

		RequestDispatcher dispatch = request.getRequestDispatcher("quizResult.jsp");
		request.setAttribute("generatedQuiz", generatedQuiz);
		request.setAttribute("result", result);
		dispatch.forward(request, response);
	}

}
