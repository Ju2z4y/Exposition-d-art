package ea.servlets;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import ea.beans.Oeuvre;
import ea.beans.Utilisateur;
import ea.dao.DAOFactory;
import ea.dao.OeuvreDao;
import ea.dao.UtilisateurDao;
import ea.forms.ConnexionForm;

/**
 * Servlet implementation class ConnexionControler
 */
@WebServlet("/ConnexionControler")
public class ConnexionControler extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private static String CONF_DAO_FACTORY = "daofactory";
	
	private static final String VUE_CONNEXION="/WEB-INF/connexion.jsp";
	private static final String LIEN_ACCUEIL="/gestion/accueil";
	
    public static final String ATT_USER         			= "utilisateur";
    public static final String ATT_FORM        				= "form";
	public static final String ATT_OEUVRES_UTILISATEUR = "oeuvresUtilisateur";
    
    public static final String ATT_SESSION_USER 			= "sessionUtilisateur";
       
    private UtilisateurDao     utilisateurDao;
    private OeuvreDao     oeuvreDao;

    public void init() throws ServletException {
        /* Récupération d'une instance de notre DAO Utilisateur */
        this.utilisateurDao = ( (DAOFactory) getServletContext().getAttribute( CONF_DAO_FACTORY ) ).getUtilisateurDao();
        this.oeuvreDao = ( (DAOFactory) getServletContext().getAttribute( CONF_DAO_FACTORY ) ).getOeuvreDao();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		request.getRequestDispatcher(VUE_CONNEXION).forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
        ConnexionForm form = new ConnexionForm();
        /* Traitement de la requête et récupération du bean en résultant */
        Utilisateur utilisateur = form.connecterUtilisateur( request, utilisateurDao );
        /* Récupération de la session depuis la requête */
        HttpSession session = request.getSession();
        /**
         * Si aucune erreur de validation n'a eu lieu, alors ajout du bean
         * Utilisateur à la session, sinon suppression du bean de la session.
         */
        if ( form.getErreurs().isEmpty() ) {
            ArrayList<Oeuvre> oeuvresUtilisateur = oeuvreDao.chargerBddOeuvreUtilisateur(utilisateur);
            
            session.setAttribute( ATT_SESSION_USER, utilisateur );
            session.setAttribute( ATT_OEUVRES_UTILISATEUR, oeuvresUtilisateur );
            
            response.sendRedirect(request.getContextPath() + LIEN_ACCUEIL);
        } else {
            session.setAttribute( ATT_SESSION_USER, null );
            request.setAttribute( ATT_FORM, form );
            request.setAttribute( ATT_USER, utilisateur );
            
            this.getServletContext().getRequestDispatcher( VUE_CONNEXION ).forward( request, response );
        }
        


        
	}

}
