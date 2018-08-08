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
 * Servlet implementation class ListeOeuvresUtilisateurControler
 */
@WebServlet("/ListeOeuvresUtilisateurControler")
public class ListeOeuvresUtilisateurControler extends HttpServlet {
	private static final long serialVersionUID = 1L;
    
	private static final String VUE_LISTE_OEUVRE_UTILISATEUR = "/WEB-INF/listeOeuvreUtilisateur.jsp";
	
	private static final String ATT_ID_OEUVRE_SUPRR = "supprIdOeuvre";
	private static final String ATT_OEUVRES = "oeuvres";
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
		// TODO Auto-generated method stub
		this.getServletContext().getRequestDispatcher( VUE_LISTE_OEUVRE_UTILISATEUR ).forward( request, response );
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		/*
		 * FONCTION DE SUPPRESION DE CLIENT
		 */
		HttpSession session = request.getSession();
		
		String stringIdOeuvreToSuppr = request.getParameter(ATT_ID_OEUVRE_SUPRR);
		Long idOeuvreToSuppr = Long.parseLong(stringIdOeuvreToSuppr);
			
		if ((idOeuvreToSuppr != null)) {
			oeuvreDao.supprimer(idOeuvreToSuppr);
			ArrayList<Oeuvre> oeuvresUtilisateur = oeuvreDao.chargerBddOeuvreUtilisateur(((Utilisateur)session.getAttribute(ATT_SESSION_USER)));
			ArrayList<Oeuvre> oeuvres =  oeuvreDao.chargerBddOeuvre();
			session.setAttribute(ATT_OEUVRES, oeuvres);
			session.setAttribute(ATT_OEUVRES_UTILISATEUR, oeuvresUtilisateur);
		}
		
		this.getServletContext().getRequestDispatcher( VUE_LISTE_OEUVRE_UTILISATEUR ).forward( request, response );
	}

}
