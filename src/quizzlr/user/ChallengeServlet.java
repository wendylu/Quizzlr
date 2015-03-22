package quizzlr.user;

import java.io.IOException;
import java.util.Set;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import quizzlr.backend.Message;
import quizzlr.backend.User;

/**
 * Servlet implementation class ChallengeServlet
 */
@WebServlet("/ChallengeServlet")
public class ChallengeServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ChallengeServlet() {
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
		HttpSession userSession = request.getSession();
		User user = (User) userSession.getAttribute("User");
		if (user == null) return;
		
		Set<User> allFriends = user.getFriends();
		for (User friend : allFriends) {
			int friendID = friend.getUserID();
			if (request.getParameter(Integer.toString(friendID)) != null) {
				Message.sendMessage(user, User.getUserFromID(friendID), 2, request.getParameter("quizID"));
			}
		}
		RequestDispatcher dispatch = request.getRequestDispatcher("challengeSent.jsp");
		dispatch.forward(request, response);
	}

}
