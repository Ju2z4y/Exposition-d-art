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

/**
 * Servlet implementation class ModificationOeuvreUtilisateurControler
 */
@WebServlet("/ModificationOeuvreUtilisateurControler")
public class ModificationOeuvreUtilisateurControler extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private static final String VUE_MODIF_OEUVRE_UTILISATEUR = "/WEB-INF/modifOeuvreUtilisateur.jsp";
	
	private static final String ATT_ID_OEUVRE_MODIF = "modifIdOeuvre";
	private static final String ATT_OEUVRE = "oeuvre";
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
		/*
		 * FONCTION DE SUPPRESION DE L'OEUVRE
		 */
		
		String stringIdOeuvreToModif = request.getParameter(ATT_ID_OEUVRE_MODIF);
		Long idOeuvreToModif = Long.parseLong(stringIdOeuvreToModif);
			
		if ((idOeuvreToModif != null)) {
			Oeuvre oeuvre = oeuvreDao.trouver(idOeuvreToModif);
			request.setAttribute(ATT_OEUVRE, oeuvre);
		}
		
		this.getServletContext().getRequestDispatcher( VUE_MODIF_OEUVRE_UTILISATEUR ).forward( request, response );
	}

}
