package quizzlr.user;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import quizzlr.backend.User;

/**
 * Servlet implementation class RemoveConfirm
 */
@WebServlet("/RemoveConfirm")
public class RemoveConfirm extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public RemoveConfirm() {
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
				String idStr = request.getParameter("id");
				
				if (user == null || user.getIsAdmin() == false || idStr == null) {
					RequestDispatcher dispatch = request.getRequestDispatcher("index.jsp");
					dispatch.forward(request, response);
					return;
				}
		User toRemove = User.getUserFromID(Integer.parseInt(idStr));
		if (toRemove == null) {
			RequestDispatcher dispatch = request.getRequestDispatcher("index.jsp");
			dispatch.forward(request, response);
			return;
		}
	
		int id = Integer.parseInt(idStr);
		User.deleteUser(id);
		RequestDispatcher dispatch = request.getRequestDispatcher("userRemoved.jsp");
		dispatch.forward(request, response);
	}

}
