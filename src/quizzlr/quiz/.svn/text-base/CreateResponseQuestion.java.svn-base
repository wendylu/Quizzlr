package quizzlr.quiz;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashSet;
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
 * Servlet implementation class CreateResponseQuestion
 */
@WebServlet("/CreateResponseQuestion")
public class CreateResponseQuestion extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public CreateResponseQuestion() {
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

		String question = (String) request.getParameter("responseQuestion");
		String answer = (String) request.getParameter("answer1_1");
		Set<String> answers = new HashSet<String>();
		int i = 2;
		while (answer != null) {
			answers.add(answer);
			answer = (String) request.getParameter("answer1_" + i);
			i++;
		}

		ResponseQuestion q = new ResponseQuestion(question, answers);

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
