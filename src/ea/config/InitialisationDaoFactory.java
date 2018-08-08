package ea.config;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import ea.exceptions.DAOConfigurationException;
import ea.dao.DAOFactory;

public class InitialisationDaoFactory implements ServletContextListener {
    private static final String ATT_DAO_FACTORY = "daofactory";

    private DAOFactory          daoFactory;

	@Override
	public void contextInitialized(ServletContextEvent event) {
        /* Récupération du ServletContext lors du chargement de l'application */
        ServletContext servletContext = event.getServletContext();
        /* Instanciation de notre DAOFactory */
        try {
        	this.daoFactory = DAOFactory.getInstance();
        	System.out.println("DAOFactory chargé avec succes");
        } catch (DAOConfigurationException e) {
        	System.out.println(e.getMessage());
        }
        /* Enregistrement dans un attribut ayant pour portée toute l'application */
        servletContext.setAttribute( ATT_DAO_FACTORY, this.daoFactory );
	}


	@Override
	public void contextDestroyed(ServletContextEvent event) {
		// TODO Auto-generated method stub

	}
}
