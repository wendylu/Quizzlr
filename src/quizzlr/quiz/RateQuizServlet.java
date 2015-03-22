package quizzlr.quiz;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import quizzlr.backend.*;

/**
 * Servlet implementation class RateQuizServlet
 */
@WebServlet("/RateQuizServlet")
public class RateQuizServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public RateQuizServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		User user = (User)session.getAttribute("User");
		Quiz quiz = Quiz.getQuizFromID(Integer.parseInt(request.getParameter("quizID")));
		
		QuizRating.createQuizRating(quiz, user, Integer.parseInt(request.getParameter("rating")), request.getParameter("review"));
		
		RequestDispatcher dispatch = request.getRequestDispatcher("quiz.jsp?id="+quiz.getQuizID());
		dispatch.forward(request, response);
	}

}
