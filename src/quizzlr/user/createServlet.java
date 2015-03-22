package quizzlr.user;

import java.io.IOException;
import java.util.Calendar;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import quizzlr.backend.User;
import quizzlr.quiz.NewbieAchievementType;



/**
 * Servlet implementation class createServlet
 */
@WebServlet("/createServlet")
public class createServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public createServlet() {
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
		String username = request.getParameter("userName");
		String pword = request.getParameter("password");
		String pwordConfirm = request.getParameter("passwordConfirm");
		if (!pword.equals(pwordConfirm)) {
			RequestDispatcher dispatch = request.getRequestDispatcher("passwordMatchError.jsp");
			dispatch.forward(request, response);
		} else if (User.getUserFromUsername(username) == null) {
			User newUser = User.createUser(username, pword);
			newUser.setIsAdmin(false);
			//store user in session
			HttpSession userSession = request.getSession();
			userSession.setAttribute("User", newUser);

			Calendar calendar = Calendar.getInstance();
			// get a java.util.Date from the calendar instance.
			// this date will represent the current instant, or "now".
			java.util.Date now = calendar.getTime();
			// a java current time (now) instance
			java.sql.Timestamp currentTimestamp = new java.sql.Timestamp(now.getTime());
			newUser.setLastLoginTime(currentTimestamp);

			//add cookie
			Cookie cookie = new Cookie("id", Integer.toString(newUser.getUserID()));
			cookie.setMaxAge(365 * 24 * 60 * 60);
			response.addCookie(cookie);

			//add initial achievement
			newUser.addAchievement(NewbieAchievementType.getInstance());

			RequestDispatcher dispatch = request.getRequestDispatcher("/");
			dispatch.forward(request, response);
		} else {
			RequestDispatcher dispatch = request.getRequestDispatcher("inUse.jsp");
			dispatch.forward(request, response);
		}
	}

}
