package ea.servlets;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ea.dao.DAOFactory;
import ea.dao.UtilisateurDao;
import ea.beans.Utilisateur;
import ea.forms.InscriptionForm;

/**
 * Servlet implementation class InscriptionControler
 */
@WebServlet("/InscriptionControler")
public class InscriptionControler extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private static String CONF_DAO_FACTORY = "daofactory";
	
	private static String VUE_INSCRIPTION="/WEB-INF/inscription.jsp";
	private static String VUE_INSCRIPTION_VALIDEE="/WEB-INF/inscriptionValidee.jsp";
	
	private static String ATT_FORM = "form";
	private static String ATT_USER = "utilisateur";
	
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
		request.getRequestDispatcher(VUE_INSCRIPTION).forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        /* Préparation de l'objet formulaire */
        InscriptionForm form = new InscriptionForm();

        /* Traitement de la requête et récupération du bean en résultant */
        Utilisateur utilisateur = form.inscrireUtilisateur( request );

        /* Stockage du formulaire et du bean dans l'objet request */
        request.setAttribute( ATT_FORM, form );
        request.setAttribute( ATT_USER, utilisateur );
        
        if ( form.getErreurs().isEmpty()) {
        	utilisateurDao.creer(utilisateur);
        	this.getServletContext().getRequestDispatcher( VUE_INSCRIPTION_VALIDEE ).forward( request, response );
        } else {
        	this.getServletContext().getRequestDispatcher( VUE_INSCRIPTION ).forward( request, response );
        }
	}

}
