package ea.servlets;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class DeconnexionControler
 */
@WebServlet("/DeconnexionControler")
public class DeconnexionControler extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private static final String VUE = "/WEB-INF/index.jsp";
	
	public static final String ATT_SESSION_USER 			= "sessionUtilisateur";

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		HttpSession session = request.getSession();
		
		session.setAttribute( ATT_SESSION_USER, null );
		
		this.getServletContext().getRequestDispatcher( VUE ).forward( request, response );
	}

}
