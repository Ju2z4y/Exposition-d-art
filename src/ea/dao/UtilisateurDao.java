package ea.dao;

import ea.beans.Utilisateur;
import ea.exceptions.DAOException;

public interface UtilisateurDao {

    void creer( Utilisateur utilisateur ) throws DAOException;

    Utilisateur trouver( String email ) throws DAOException;
    
    void modifier( Utilisateur utilisateur, Long l ) throws DAOException;

	Utilisateur trouver(Long id) throws DAOException;

}