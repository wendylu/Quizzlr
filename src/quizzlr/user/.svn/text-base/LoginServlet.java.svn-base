package quizzlr.user;

import java.io.IOException;
import java.util.Calendar;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import quizzlr.backend.User;
import quizzlr.backend.Util;

import javax.servlet.http.Cookie;

/**
 * Servlet implementation class LoginServlet
 */
@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public LoginServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

	
	private String generateHash(String string) {
		return Util.getSHA1Hash(string);
	}
	
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		/// TODO Auto-generated method stub
		
		String username = request.getParameter("userName");
		String pword = request.getParameter("password");
		
		if (User.getUserFromUsername(username) != null) {
			User user = User.getUserFromUsername(username);
			
			String rightHash = user.getPasswordHash();
			String curHash = this.generateHash(pword + user.getPasswordSalt());
			if (rightHash.equals(curHash)) {
				//store user in session
				HttpSession userSession = request.getSession();
				userSession.setAttribute("User", user);
				
				Calendar calendar = Calendar.getInstance();
				// get a java.util.Date from the calendar instance.
				// this date will represent the current instant, or "now".
				java.util.Date now = calendar.getTime();
				// a java current time (now) instance
				java.sql.Timestamp currentTimestamp = new java.sql.Timestamp(now.getTime());
				user.setLastLoginTime(currentTimestamp);
				
				Cookie cookie = new Cookie("id", Integer.toString(user.getUserID()));
				cookie.setMaxAge(365 * 24 * 60 * 60);
				response.addCookie(cookie);

				RequestDispatcher dispatch = request.getRequestDispatcher("/");
				dispatch.forward(request, response);
				return;
			}
			
		}
		
		RequestDispatcher dispatch = request.getRequestDispatcher("tryAgain.jsp");
		dispatch.forward(request, response);
	}

}
