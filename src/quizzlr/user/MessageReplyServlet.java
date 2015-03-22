package quizzlr.user;

import java.io.IOException;
import java.util.List;

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
 * Servlet implementation class MessageReplyServlet
 */
@WebServlet("/MessageReplyServlet")
public class MessageReplyServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public MessageReplyServlet() {
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
		
		String nextPage = null;
		
		Message message = Message.getMessageFromID(Integer.parseInt(request.getParameter("messageID")));
		User fromUser = message.getFromUser();
		if (message.getMessageType() == 1) {
			if (request.getParameter("accept").equals("yes") && !user.isFriendOf(fromUser)) {
				user.addFriend(fromUser);
			} 
			//remove this and all previous friend requests from same user
			List<Message> messages = user.getMessages();
			for (Message m : messages) {
				if (m.getMessageType() == 1 && m.getFromUser().equals(fromUser)) {
					m.delete();
				}
			}
			nextPage = "inbox.jsp";
		} else if (message.getMessageType() == 3) {
			if (request.getParameter("action").equals("delete")) {
				message.delete();
				nextPage = "inbox.jsp";
			}
		} else if (message.getMessageType() == 2) {
			message.delete();
			nextPage = "inbox.jsp";
		}

		RequestDispatcher dispatch = request.getRequestDispatcher(nextPage);
		dispatch.forward(request, response);
		
	}

}
