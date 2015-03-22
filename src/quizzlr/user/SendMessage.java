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
import quizzlr.backend.*;

/**
 * Servlet implementation class SendMessage
 */
@WebServlet("/SendMessage")
public class SendMessage extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SendMessage() {
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
		
		int recipientID = Integer.parseInt(request.getParameter("recipient"));
		User to = User.getUserFromID(recipientID);
		int messageType = 0;
		String messageContent = "";
		String nextPage = "/";
		if (request.getParameter("type").equals("friendRequest")) {
			List<Message> curMessages = to.getMessages();
			for (Message message : curMessages) {
				if (message.getMessageType() == 1 && message.getFromUser().equals(user)) {
					RequestDispatcher dispatch = request.getRequestDispatcher("pending.jsp");
					dispatch.forward(request, response);
					return;
				}
			}
			curMessages = user.getMessages();
			for (Message message : curMessages) {
				if (message.getMessageType() == 1 && message.getFromUser().equals(to)) {
					RequestDispatcher dispatch = request.getRequestDispatcher("pending.jsp");
					dispatch.forward(request, response);
					return;
				}
			}
			messageType = 1;	
			messageContent = "";
			nextPage = "requestSent.jsp";
		} else if (request.getParameter("type").equals("note")) {
			messageType = 3;
			
			messageContent = request.getParameter("content");
			nextPage = "noteSent.jsp";
		} 
		Message.sendMessage(user, to, messageType, messageContent);
		RequestDispatcher dispatch = request.getRequestDispatcher(nextPage);
		dispatch.forward(request, response);
	}

}
