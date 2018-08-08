package ea.dao;

import java.util.ArrayList;

import ea.beans.Oeuvre;
import ea.beans.Utilisateur;
import ea.exceptions.DAOException;

public interface OeuvreDao {
    void creer( Oeuvre oeuvre ) throws DAOException;
    
    void modifier( Oeuvre oeuvre, Long l ) throws DAOException;

	ArrayList<Oeuvre> chargerBddOeuvre() throws DAOException;

	ArrayList<Oeuvre> chargerBddOeuvreUtilisateur(Utilisateur utilisateur) throws DAOException;
	
	void supprimer(Long idOeuvreToSuppr);
	
	Oeuvre trouver(Long id) throws DAOException;
}
