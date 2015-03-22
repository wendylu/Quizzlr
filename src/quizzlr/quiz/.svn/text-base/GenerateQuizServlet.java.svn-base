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

import quizzlr.backend.*;

/**
 * Servlet implementation class GenerateQuizServlet
 */
@WebServlet("/GenerateQuizServlet")
public class GenerateQuizServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public GenerateQuizServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// Generate the quiz
		int quizID = Integer.decode(request.getParameter("id"));
		Quiz quiz = Quiz.getQuizFromID(quizID);

		boolean isPracticeMode = (request.getParameter("isPracticeMode") != null && request.getParameter("isPracticeMode").equals("Yes"));
		GeneratedQuiz generatedQuiz = quiz.generateQuiz(isPracticeMode);

		HttpSession session = request.getSession();
		session.removeAttribute("score");
		session.setAttribute("generatedQuiz", generatedQuiz);
		session.setAttribute("startTime", new Date());

		if (quiz.getIsOnePageFormat()) {
			RequestDispatcher dispatch = request.getRequestDispatcher("takeQuizOnePage.jsp");
			dispatch.forward(request, response);
		}
		else {
			RequestDispatcher dispatch = request.getRequestDispatcher("takequiz.jsp?q=1");
			dispatch.forward(request, response);
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}
