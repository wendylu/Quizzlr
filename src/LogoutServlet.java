

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import quizzlr.backend.User;

/**
 * Servlet implementation class LogoutServlet
 */
@WebServlet("/LogoutServlet")
public class LogoutServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public LogoutServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession userSession = request.getSession();
		userSession.setAttribute("User", null);
		
		//remove cookie
		Cookie[] list = request.getCookies();
		if (list != null) {
			for (Cookie cookie : list) {
				String key = cookie.getName();
				if (key.equals("id")) {
					response.setContentType("text/html");
					cookie.setMaxAge(0);
					cookie.setValue("");
				    response.addCookie(cookie);
				}
				
			}
		}
		
		RequestDispatcher dispatch = request.getRequestDispatcher("/");
		dispatch.forward(request, response);
		return;			}
	
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession userSession = request.getSession();
		userSession.setAttribute("User", null);
		
		//remove cookie
		Cookie[] list = request.getCookies();
		if (list != null) {
			for (Cookie cookie : list) {
				String key = cookie.getName();
				if (key.equals("id")) {
					response.setContentType("text/html");
					cookie.setMaxAge(0);
					cookie.setValue("");
				    response.addCookie(cookie);
				    
				}
				
			}
		}
		
		RequestDispatcher dispatch = request.getRequestDispatcher("/");
		dispatch.forward(request, response);
		return;		
	}
}
