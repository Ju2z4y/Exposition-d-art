package ea.servlets;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;

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
import ea.forms.AjoutOeuvreForm;
import ea.forms.ModifOeuvreForm;

/**
 * Servlet implementation class ValidationModifOeuvreControler
 */
@WebServlet("/ValidationModifOeuvreControler")
public class ValidationModifOeuvreControler extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	private static final String VUE_MODIF_OEUVRE_UTILISATEUR = "/WEB-INF/modifOeuvreUtilisateur.jsp";
	
	private static final String ATT_ID_OEUVRE_MODIF = "modifIdOeuvre";
	private static final String ATT_OEUVRE = "oeuvre";
	private static final String ATT_OEUVRES = "oeuvres";
	private static final String ATT_FORM = "aForm";
	private static final String ATT_OEUVRES_UTILISATEUR = "oeuvresUtilisateur";
	private static final String ATT_SESSION_USER 			= "sessionUtilisateur";
	
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
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String chemin = this.getServletConfig().getInitParameter( "chemin" );
		
		String stringIdOeuvreToModif = request.getParameter("modifIdOeuvre");
		Long idOeuvreToModif = Long.parseLong(stringIdOeuvreToModif);
		
		/*
		 * FONCTION DE MODIFICATION OEUVRE
		 */
		HttpSession session = request.getSession();
		
		ModifOeuvreForm form = new ModifOeuvreForm();
		Oeuvre oeuvreModifiee = form.ajouterOeuvre(request, chemin);
		oeuvreModifiee.setId_oeuvre(idOeuvreToModif);
		if (oeuvreModifiee.getCheminFichier() == null) {
			oeuvreModifiee.setCheminFichier(oeuvreDao.trouver(idOeuvreToModif).getCheminFichier());
		}
		request.setAttribute(ATT_OEUVRE, oeuvreModifiee);
		request.setAttribute(ATT_FORM, form);
		
		if (form.getErreurs().isEmpty()) {

			oeuvreDao.modifier(oeuvreModifiee, idOeuvreToModif);
			
			ArrayList<Oeuvre> oeuvresUtilisateur = oeuvreDao.chargerBddOeuvreUtilisateur(((Utilisateur)session.getAttribute(ATT_SESSION_USER)));
			ArrayList<Oeuvre> oeuvres =  oeuvreDao.chargerBddOeuvre();
			session.setAttribute(ATT_OEUVRES, oeuvres);
			session.setAttribute(ATT_OEUVRES_UTILISATEUR, oeuvresUtilisateur);
		}
		this.getServletContext().getRequestDispatcher( VUE_MODIF_OEUVRE_UTILISATEUR ).forward( request, response );
	}

}
