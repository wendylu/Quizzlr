package quizzlr.user;


import java.io.IOException;
import java.io.PrintWriter;
import java.util.Set;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import quizzlr.backend.User;

/**
 * Servlet implementation class AutoSearchServlet
 */
@WebServlet("/AutoSearchServlet")
public class AutoSearchServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public AutoSearchServlet() {
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
		Set<User> matches = User.matchingUsernames(searchTerm);
		out.print(searchTerm);
		out.print(Integer.toString(matches.size()));
		out.print("[");
		for(User u:matches)
		{
			out.print("{ \"id\": \"" + u.getUserID() + "\", \"label\": \"" + u.getUsername()+ "\", \"value\": \""+u.getUsername()+"\" },");
		}
		//out.print("[ { \"id\": \"Botaurus stellaris\", \"label\": \"Great Bittern\", \"value\": \"Great Bittern\" }, { \"id\": \"Podiceps nigricollis\", \"label\": \"Black-necked Grebe\", \"value\": \"Black-necked Grebe\" }]");

		out.print("]");
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}
