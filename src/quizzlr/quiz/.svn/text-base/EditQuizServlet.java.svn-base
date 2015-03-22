package quizzlr.quiz;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import quizzlr.backend.Category;
import quizzlr.backend.Quiz;
import quizzlr.backend.Tag;

/**
 * Servlet implementation class EditQuizServlet
 */
@WebServlet("/EditQuizServlet")
public class EditQuizServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public EditQuizServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Quiz quiz = Quiz.getQuizFromID(Integer.parseInt(request.getParameter("id")));
		quiz.setTitle(request.getParameter("title"));
		quiz.setDescription(request.getParameter("description"));
		quiz.setCategory(Category.getCategoryFromID(Integer.parseInt(request.getParameter("category"))));
		quiz.clearTags();
		
		String[] tags = request.getParameterValues("tags");
		if (tags != null) {
			for (String tag : tags) {
				quiz.addTag(Tag.getTagFromTitle(tag));
			}
		}
		
		RequestDispatcher dispatch = request.getRequestDispatcher("quiz.jsp?id=" + quiz.getQuizID());
		dispatch.forward(request, response);
	}

}
