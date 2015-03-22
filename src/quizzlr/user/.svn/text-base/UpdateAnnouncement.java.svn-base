package quizzlr.user;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import quizzlr.backend.Announcement;
import quizzlr.backend.User;

/**
 * Servlet implementation class UpdateAnnouncement
 */
@WebServlet("/UpdateAnnouncement")
public class UpdateAnnouncement extends HttpServlet {
	private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public UpdateAnnouncement() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession userSession = request.getSession();
		User author = (User) userSession.getAttribute("User");
		String action = request.getParameter("action");
		String id = request.getParameter("announcementID");
		if (author == null || !author.getIsAdmin() || action == null || id == null) {
			RequestDispatcher dispatch = request.getRequestDispatcher("loginHome.jsp");
			dispatch.forward(request, response);
			return;
		}

		int announcementID = Integer.parseInt(id);
		Announcement announcement = Announcement.getAnnouncementByID(announcementID);

		// Editing the announcement
		if (action.equals("edit")) {
			String title = request.getParameter("title");
			String content = request.getParameter("content");

			if (title != null && content != null) {
				announcement.setTitle(title);
				announcement.setContent(content);
				announcement.setLastEditor(author);
			}
		}
		else if (action.equals("delete")) {
			announcement.delete();
		}

		RequestDispatcher dispatch = request.getRequestDispatcher("admin.jsp");
		dispatch.forward(request, response);
	}

}
