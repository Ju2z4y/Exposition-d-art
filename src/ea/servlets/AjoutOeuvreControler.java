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
import ea.forms.AjoutOeuvreForm;
import ea.forms.InscriptionForm;

/**
 * Servlet implementation class AjoutOeuvreControler
 */
@WebServlet("/AjoutOeuvreControler")
public class AjoutOeuvreControler extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private static final String ATT_OEUVRES = "oeuvres";
	private static final String ATT_OEUVRES_UTILISATEUR = "oeuvresUtilisateur";
	
	private static final String VUE_AJOUT_OEUVRE = "/WEB-INF/ajoutOeuvre.jsp";
	
	private static String CONF_DAO_FACTORY = "daofactory";
	
    private OeuvreDao     oeuvreDao;

    public void init() throws ServletException {
        /* Récupération d'une instance de notre DAO Utilisateur */
        this.oeuvreDao = ( (DAOFactory) getServletContext().getAttribute( CONF_DAO_FACTORY ) ).getOeuvreDao();
    }
       
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		this.getServletContext().getRequestDispatcher( VUE_AJOUT_OEUVRE ).forward( request, response );
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String chemin = this.getServletConfig().getInitParameter( "chemin" );
		
		HttpSession session = request.getSession();
		Utilisateur utilisateur = ((Utilisateur)session.getAttribute("sessionUtilisateur"));
		
		AjoutOeuvreForm aForm = new AjoutOeuvreForm();
		Oeuvre oeuvre = aForm.ajouterOeuvre(request, chemin);
		
		request.setAttribute("aForm", aForm);
		request.setAttribute("oeuvre", oeuvre);
		
		if (aForm.getErreurs().isEmpty()) {
			oeuvre.setUtilisateur(utilisateur);
			oeuvreDao.creer(oeuvre);
			
			ArrayList<Oeuvre> oeuvres;
			
    		if ((session.getAttribute(ATT_OEUVRES) == null) || (((ArrayList)(session.getAttribute(ATT_OEUVRES))).size() < 1)) {
    			oeuvres = new ArrayList<Oeuvre>();
    		} else {
    			oeuvres = (ArrayList<Oeuvre>) session.getAttribute(ATT_OEUVRES);
    		}
    		
    		ArrayList<Oeuvre> oeuvresUtilisateur;
    		
    		if ((session.getAttribute(ATT_OEUVRES_UTILISATEUR) == null) || (((ArrayList)(session.getAttribute(ATT_OEUVRES_UTILISATEUR))).size() < 1)) {
    			oeuvresUtilisateur = new ArrayList<Oeuvre>();
    		} else {
    			oeuvresUtilisateur = (ArrayList<Oeuvre>) session.getAttribute(ATT_OEUVRES_UTILISATEUR);
    		}
			
			oeuvres.add(oeuvre);
			oeuvresUtilisateur.add(oeuvre);
			session.setAttribute(ATT_OEUVRES, oeuvres);
			session.setAttribute(ATT_OEUVRES_UTILISATEUR, oeuvresUtilisateur);
			
			this.getServletContext().getRequestDispatcher( VUE_AJOUT_OEUVRE ).forward( request, response );
		} else {
			this.getServletContext().getRequestDispatcher( VUE_AJOUT_OEUVRE ).forward( request, response );
		}
		
	}

}
