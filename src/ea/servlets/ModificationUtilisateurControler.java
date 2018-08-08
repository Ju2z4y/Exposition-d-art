package ea.servlets;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import ea.beans.Utilisateur;
import ea.dao.DAOFactory;
import ea.dao.UtilisateurDao;
import ea.forms.InscriptionForm;

/**
 * Servlet implementation class ModificationUtilisateurControler
 */
@WebServlet("/ModificationUtilisateurControler")
public class ModificationUtilisateurControler extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private static String ATT_FORM = "form";
	private static String ATT_USER = "utilisateur";
	
	private static String CONF_DAO_FACTORY = "daofactory";
	
	private static final String VUE_MODIFICATION = "/WEB-INF/modificationUtilisateur.jsp";
	private static final String VUE_GESTION = "/WEB-INF/gestionIndex.jsp";
	
    private UtilisateurDao     utilisateurDao;	

    public void init() throws ServletException {
        /* Récupération d'une instance de notre DAO Utilisateur */
        this.utilisateurDao = ( (DAOFactory) getServletContext().getAttribute( CONF_DAO_FACTORY ) ).getUtilisateurDao();
    }
    
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		request.getRequestDispatcher(VUE_MODIFICATION).forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        InscriptionForm form = new InscriptionForm();

        /* Traitement de la requête et récupération du bean en résultant */
        Utilisateur utilisateur = form.inscrireUtilisateur( request );
        
        
        HttpSession session = request.getSession();
        Utilisateur sessionUtilisateur = (Utilisateur) session.getAttribute("sessionUtilisateur");

        /* Stockage du formulaire et du bean dans l'objet request */
        request.setAttribute( ATT_FORM, form );
        request.setAttribute( ATT_USER, utilisateur );
        
        if ( form.getErreurs().isEmpty()) {
        	Long IdUtilisateur = sessionUtilisateur.getId();
        	utilisateurDao.modifier(utilisateur, IdUtilisateur);
        	session.setAttribute("sessionUtilisateur", utilisateurDao.trouver(utilisateur.getEmail()));
        	this.getServletContext().getRequestDispatcher( VUE_GESTION ).forward( request, response );
        } else {
        	this.getServletContext().getRequestDispatcher( VUE_MODIFICATION ).forward( request, response );
        }
	}

}
