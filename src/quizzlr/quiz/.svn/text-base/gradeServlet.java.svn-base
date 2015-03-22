package quizzlr.quiz;


import java.io.IOException;
import java.sql.Time;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import quizzlr.backend.*;
import quizzlr.backend.GeneratedQuiz.QuestionResult;
import quizzlr.quiz.ResponseQuestion;

/**
 * Servlet implementation class gradeServlet
 */
@WebServlet("/gradeServlet")
public class gradeServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public gradeServlet() {
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
		HttpSession session = request.getSession();
		GeneratedQuiz generatedQuiz = (GeneratedQuiz)session.getAttribute("generatedQuiz");
		Quiz curQuiz = generatedQuiz.getTemplate();
		int questionNum = Integer.decode(request.getParameter("q"));
		int totalNumQuestions = curQuiz.getQuestions().size();
		QuizQuestion curQuestion = generatedQuiz.getQuestionAt(questionNum-1);
		User taker = (User)session.getAttribute("User");

		Object answerResponse = QuizUtil.getUserResponse(curQuestion, request);
		generatedQuiz.setResponseAt(questionNum-1, answerResponse);
		// Award potty mouth achievement
		if (QuizUtil.checkPottyMouthAchievement(answerResponse) && !taker.hasAchievement(PottyMouthAchievementType.getInstance())) {
			taker.addAchievement(PottyMouthAchievementType.getInstance());
		}

		// Is quiz finished?
		if(questionNum == totalNumQuestions)
		{
			session.removeAttribute("generatedQuiz");
			session.removeAttribute("score");

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
			return;
		}
		// Give feedback if is immediate correction quiz
		else if (curQuiz.getIsImmediateCorrection())
		{
			QuestionResult feedback = generatedQuiz.gradeQuestionAt(questionNum-1);
			int pastScore = 0;
			if(request.getSession().getAttribute("score") != null)
				pastScore = ((Integer) session.getAttribute("score"));
			session.setAttribute("score", pastScore + feedback.Score);

			// Redirect to pretty page
			RequestDispatcher dispatch = request.getRequestDispatcher("immediateFeedback.jsp");
			request.setAttribute("generatedQuiz", generatedQuiz);
            request.setAttribute("feedback", feedback);
            request.setAttribute("nextQuestionNum", questionNum+1);
            request.setAttribute("totalScore", pastScore + feedback.Score);
			dispatch.forward(request, response);
			return;
		}
		else
		{
			RequestDispatcher dispatch = request.getRequestDispatcher("takequiz.jsp?q="+Integer.toString(questionNum+1));
			dispatch.forward(request, response);
			request.setAttribute("generatedQuiz", generatedQuiz);
			return;
		}
	}

}
