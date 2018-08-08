package ea.servlets;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class CollectionPeinturesControler
 */
@WebServlet("/CollectionPeinturesControler")
public class CollectionPeinturesControler extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private static final String VUE_COLLECTION_OEUVRE = "/WEB-INF/collectionOeuvre.jsp";
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CollectionPeinturesControler() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		this.getServletContext().getRequestDispatcher( VUE_COLLECTION_OEUVRE).forward( request, response );
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
