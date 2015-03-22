package quizzlr.quiz;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import quizzlr.backend.Quiz;

/**
 * Servlet implementation class CreateMultipleResponseQuestion
 */
@WebServlet("/CreateMultipleResponseQuestion")
public class CreateMultipleResponseQuestion extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public CreateMultipleResponseQuestion() {
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

		String question = (String) request.getParameter("multipleResponseQuestion");
		List<Set<String>> answers = new ArrayList<Set<String>>();
		for (int j = 0; request.getParameter("answer" + (j+1) + "_1") != null; j++) {
			String answer = (String) request.getParameter("answer" + (j+1) + "_1");
			Set<String> theseAnswers = new HashSet<String>();
			int i = 2;
			while (answer != null) {
				theseAnswers.add(answer);
				answer = (String) request.getParameter("answer" + (j+1) + "_" + i);
				i++;
			}
			answers.add(theseAnswers);
		}
		boolean ordered = Boolean.parseBoolean((String) request.getParameter("ordered"));

		MultipleResponseQuestion q = new MultipleResponseQuestion(question, answers, ordered);

		HttpSession session = request.getSession();
		Quiz quiz = Quiz.getQuizFromID((Integer) session.getAttribute("quizId"));
		quiz.addQuestion(q);
		RequestDispatcher dispatch;
		if (((String) request.getParameter("submit")) != null) {
			dispatch = request.getRequestDispatcher("createQuestion.jsp");
		} else if (((String) request.getParameter("finish")) != null) {
			quiz.setIsLive(true);
			dispatch = request.getRequestDispatcher("quizCreated.jsp");
		} else {
			dispatch = request.getRequestDispatcher("error.jsp");
		}
		dispatch.forward(request, response);

	}

}
