package quizzlr.user;

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

import quizzlr.backend.Quiz;
import quizzlr.backend.Tag;
import quizzlr.backend.User;

/**
 * Servlet implementation class SearchServlet
 */
@WebServlet("/SearchServlet")
public class SearchServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public SearchServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");
		PrintWriter out= response.getWriter();
		String searchTerm = request.getParameter("term");
		int itr = 0;
		Set<User> matches = User.matchingUsernames(searchTerm);
		out.print("[");
		boolean first=true;
		for(User u:matches)
		{
			itr++;
			if(itr==5)
				break;
			if(first)first=false;
			else
				out.print(",");
			out.print("{ \"link\": \"userPage.jsp?id=" + u.getUserID() + "\", \"label\": \"User: " + u.getUsername()+ "\", \"value\": \""+u.getUsername()+"\" }");
		}
		itr = 0;
		Set<Quiz> results = new HashSet<Quiz>();
		Set<Quiz> quizzes = Quiz.matchingTitles(searchTerm);
		for(Quiz q:quizzes)
		{
			results.add(q);
			itr++;
			if(itr==5)
				break;
		}
		
		itr = 0;
		Set<Quiz> quizzes2 = Quiz.matchingTitles(searchTerm);
		for(Quiz q:quizzes2)
		{
			itr++;
			if(itr==5)
				break;
			results.add(q);
		}
		for(Quiz q:results)
		{
			if(first)first=false;
			else
				out.print(",");
			out.print("{ \"link\": \"quiz.jsp?id=" + q.getQuizID() + "\", \"label\": \"Quiz: " + q.getTitle() + "\", \"value\": \""+ q.getTitle() +"\" }");
		}
		itr = 0;
		Set<Tag> tags = Tag.matchingTitles(searchTerm);
		for(Tag t : tags)
		{
			itr++;
			if(itr==3)
				break;	
			if(first) first=false;
			else
				out.print(",");
			out.print("{ \"link\": \"tag.jsp?id=" + t.getTagID() + "\", \"label\": \"Tag: " + t.getTitle() + "\", \"value\": \""+ t.getTitle() +"\" }");
		}
		out.print("]");
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String searchTerm = request.getParameter("search");

		Set<User> userMatches = User.matchingUsernames(searchTerm);
		request.setAttribute("userMatches", userMatches);

		Set<Quiz> quizMatches = Quiz.matchingTitles(searchTerm);
		quizMatches.addAll(Quiz.matchingDescriptions(searchTerm));
		request.setAttribute("quizMatches", quizMatches);


		Set<Tag> tags = Tag.matchingTitles(searchTerm);
		request.setAttribute("tagMatches", tags);
		
		RequestDispatcher dispatch = request.getRequestDispatcher("searchResults.jsp");
		dispatch.forward(request, response);
	}

}
