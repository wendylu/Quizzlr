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
 * Servlet implementation class BanUserServlet
 */
@WebServlet("/BanUserServlet")
public class BanUserServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public BanUserServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession userSession = request.getSession();
		User user = (User) userSession.getAttribute("User");
		String idStr = request.getParameter("id");
		
		if (user == null || user.getIsAdmin() == false || idStr == null) {
			RequestDispatcher dispatch = request.getRequestDispatcher("index.jsp");
			dispatch.forward(request, response);
			return;
		}
		User toEdit = User.getUserFromID(Integer.parseInt(idStr));
		if (toEdit == null) {
			RequestDispatcher dispatch = request.getRequestDispatcher("index.jsp");
			dispatch.forward(request, response);
			return;
		}
	
		boolean isBanned = request.getParameter("action").equals("Ban");
		toEdit.setIsBanned(isBanned);
		RequestDispatcher dispatch = request.getRequestDispatcher("userBanSet.jsp");
		dispatch.forward(request, response);
	}

}
