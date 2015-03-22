package quizzlr.user;

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
 * Servlet implementation class CreateAnnouncement
 */
@WebServlet("/CreateAnnouncement")
public class CreateAnnouncement extends HttpServlet {
	private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public CreateAnnouncement() {
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
		User author = (User) userSession.getAttribute("User");
		if (author == null || !author.getIsAdmin() || request.getParameter("content") == null || request.getParameter("title") == null) {
			RequestDispatcher dispatch = request.getRequestDispatcher("loginHome.jsp");
			dispatch.forward(request, response);
			return;
		}
		String title = request.getParameter("title");
		String content = request.getParameter("content");
		Announcement.createAnnouncement(title, content, author);
		RequestDispatcher dispatch = request.getRequestDispatcher("announcementCreated.jsp");
		dispatch.forward(request, response);
	}

}
