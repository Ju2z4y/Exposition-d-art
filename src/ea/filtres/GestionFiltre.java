package ea.filtres;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import ea.beans.Oeuvre;
import ea.beans.Utilisateur;
import ea.dao.DAOFactory;
import ea.dao.OeuvreDao;

/**
 * Servlet Filter implementation class GestionFiltre
 */
@WebFilter("/GestionFiltre")
public class GestionFiltre implements Filter {
	public static final String ATT_SESSION_USER 			= "sessionUtilisateur";
	
	public static final String ATT_OEUVRES = "oeuvres";
	public static final String ATT_OEUVRES_UTILISATEUR = "oeuvresUtilisateur";
	public static final String CONF_DAO_FACTORY = "daofactory";
	
	public static final String LIEN_ACCUEIL = "/index";
	
	OeuvreDao oeuvreDao;

	@Override
	public void init(FilterConfig config) throws ServletException {
		this.oeuvreDao = ( (DAOFactory) config.getServletContext().getAttribute( CONF_DAO_FACTORY ) ).getOeuvreDao();
	}
	/**
	 * @see Filter#destroy()
	 */
	public void destroy() {
		// TODO Auto-generated method stub
	}

	/**
	 * @see Filter#doFilter(ServletRequest, ServletResponse, FilterChain)
	 */
	public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) res;

		HttpSession session =  request.getSession();    
		Utilisateur sessionUtilisateur = (Utilisateur) session.getAttribute(ATT_SESSION_USER);
		
		if (sessionUtilisateur == null) {
			response.sendRedirect(request.getContextPath() + LIEN_ACCUEIL);
		} else {
			ArrayList<Oeuvre> oeuvresUtilisateur;
			/*Vérification de l'existence de client dans la session*/
			if ((session.getAttribute(ATT_OEUVRES_UTILISATEUR) == null) || ((ArrayList) (session.getAttribute(ATT_OEUVRES_UTILISATEUR))).size() < 1 ) {
				oeuvresUtilisateur = oeuvreDao.chargerBddOeuvreUtilisateur(sessionUtilisateur);
				session.setAttribute(ATT_OEUVRES_UTILISATEUR, oeuvresUtilisateur);
			}
			
			chain.doFilter(request, response);
		}
	}


}
