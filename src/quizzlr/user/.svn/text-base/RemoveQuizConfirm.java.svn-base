package quizzlr.user;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import quizzlr.backend.Quiz;

/**
 * Servlet implementation class RemoveQuizConfirm
 */
@WebServlet("/RemoveQuizConfirm")
public class RemoveQuizConfirm extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public RemoveQuizConfirm() {
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
		// TODO Auto-generated method stub
		if (request.getParameter("id") == null) {
			RequestDispatcher dispatch = request.getRequestDispatcher("/quizzlr");
			dispatch.forward(request, response);
			return;
		} 
		int id = Integer.parseInt(request.getParameter("id"));
		if (Quiz.getQuizFromID(id) == null) {
			RequestDispatcher dispatch = request.getRequestDispatcher("quizNotFound.jsp");
			dispatch.forward(request, response);
			return;
		}
		Quiz quiz = Quiz.getQuizFromID(id);
		quiz.delete();
		RequestDispatcher dispatch = request.getRequestDispatcher("quizRemoved.jsp");
		dispatch.forward(request, response);
	}

}
