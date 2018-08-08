package ea.filtres;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import ea.beans.Oeuvre;
import ea.dao.DAOFactory;
import ea.dao.OeuvreDao;

public class LoadBddFilter implements Filter {
	public static final String ATT_OEUVRES = "oeuvres";
	public static final String ATT_OEUVRES_UTILISATEUR = "oeuvresUtilisateur";
	public static final String CONF_DAO_FACTORY = "daofactory";
	
	OeuvreDao oeuvreDao;

	@Override
	public void destroy() {
		// TODO Auto-generated method stub

	}

	@Override
	public void init(FilterConfig config) throws ServletException {
		this.oeuvreDao = ( (DAOFactory) config.getServletContext().getAttribute( CONF_DAO_FACTORY ) ).getOeuvreDao();
	}

	@Override
	public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
			throws IOException, ServletException {
        /*//////////////////////////
         * Mise à jour de la session
         *//////////////////////////
		HttpSession session = ((HttpServletRequest) req).getSession();
		ArrayList<Oeuvre> oeuvres;
		
		/*Vérification de l'existence de client dans la session*/
		if ((session.getAttribute(ATT_OEUVRES) == null) || ((ArrayList) (session.getAttribute(ATT_OEUVRES))).size() < 1 ) {
			oeuvres = oeuvreDao.chargerBddOeuvre();
			session.setAttribute(ATT_OEUVRES, oeuvres);
		}
		
		chain.doFilter(req, res);
	}


}
