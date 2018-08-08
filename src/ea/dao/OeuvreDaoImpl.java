package ea.dao;

import static ea.dao.DAOUtilitaire.fermeturesSilencieuses;
import static ea.dao.DAOUtilitaire.initialisationRequetePreparee;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import ea.beans.Oeuvre;
import ea.beans.Utilisateur;
import ea.exceptions.DAOException;

public class OeuvreDaoImpl implements OeuvreDao {
	private static final String SQL_INSERT           = "INSERT INTO oeuvre (id_utilisateur, technique, titre, hauteur, largeur, support"
			+ ", annee, prix, cheminFichier) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
	private static final String SQL_SELECT_ALL = "SELECT * FROM oeuvre";
	private static final String SQL_SELECT_ID = "SELECT * FROM oeuvre WHERE id_oeuvre = ?";
	private static final String SQL_SELECT_ALL_UTILISATEUR = "SELECT * FROM oeuvre WHERE id_utilisateur = ?";
	private static final String SQL_DELETE_OEUVRE_ID = "DELETE FROM oeuvre WHERE id_oeuvre = ?";
	private static final String SQL_UPDATE_ID = "UPDATE oeuvre SET technique = ?, titre = ?, hauteur = ?, largeur = ?"
			+ ", support = ?, annee = ?, prix = ?, cheminFichier = ? WHERE id_oeuvre = ?";
	
    private DAOFactory          daoFactory;

    OeuvreDaoImpl( DAOFactory daoFactory ) {
        this.daoFactory = daoFactory;
    }

	@Override
	public void creer(Oeuvre oeuvre) throws DAOException {
        Connection connexion = null;
        PreparedStatement preparedStatement = null;
        ResultSet valeursAutoGenerees = null;

        try {
            connexion = daoFactory.getConnection();
            preparedStatement = initialisationRequetePreparee( connexion, SQL_INSERT, true, oeuvre.getUtilisateur().getId(), oeuvre.getTechnique(), 
            		oeuvre.getTitre(), oeuvre.getHauteur(), oeuvre.getLargeur(), oeuvre.getSupport(), oeuvre.getAnnee(), oeuvre.getPrix(), oeuvre.getCheminFichier() );
            int statut = preparedStatement.executeUpdate();
            if ( statut == 0 ) {
                throw new DAOException( "Echec de la création de l'oeuvre, aucune ligne ajoutée dans la table." );
            }
            valeursAutoGenerees = preparedStatement.getGeneratedKeys();
            if ( valeursAutoGenerees.next() ) {
                oeuvre.setId_oeuvre( valeursAutoGenerees.getLong( 1 ) );
                System.out.println("Oeuvre créée avec succes");
            } else {
                throw new DAOException( "Echec de la création de l'oeuvre en base, aucun ID auto-généré retourné." );
            }
        } catch ( SQLException e ) {
            throw new DAOException( e );
        } finally {
            fermeturesSilencieuses( valeursAutoGenerees, preparedStatement, connexion );
        }
	}

	@Override
	public ArrayList<Oeuvre> chargerBddOeuvre() throws DAOException {
		ArrayList<Oeuvre> oeuvres = new ArrayList<Oeuvre>();
		
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		
		try {
			connection = daoFactory.getConnection();
			preparedStatement = initialisationRequetePreparee(connection, SQL_SELECT_ALL, false);
			resultSet = preparedStatement.executeQuery();
			while (resultSet.next()) {
				oeuvres.add(mapOeuvre(resultSet));
			}
			System.out.println("BDD chargée en session : succès");
		} catch ( SQLException e ){
			throw new DAOException(e);
		} finally {
			fermeturesSilencieuses(resultSet, preparedStatement, connection);
		}
		return oeuvres;
	}
	
	private Oeuvre mapOeuvre(ResultSet resultSet) throws SQLException {
		Oeuvre oeuvre = new Oeuvre();
		UtilisateurDao utilisateurDao = daoFactory.getUtilisateurDao();
		
		oeuvre.setId_oeuvre( resultSet.getLong( "id_oeuvre" ) );
        oeuvre.setUtilisateur( utilisateurDao.trouver(resultSet.getLong( "id_utilisateur" )) );
        oeuvre.setTechnique( resultSet.getString( "technique" ) );
        oeuvre.setTitre( resultSet.getString( "titre" ) );
        oeuvre.setHauteur( resultSet.getInt( "hauteur" ) );
        oeuvre.setLargeur( resultSet.getInt( "largeur" ) );
        oeuvre.setSupport( resultSet.getString( "support" ) );
        oeuvre.setAnnee( resultSet.getInt( "annee" ) );
        oeuvre.setPrix( resultSet.getInt( "prix" ) );
        oeuvre.setCheminFichier( resultSet.getString( "cheminFichier" ) );
        return oeuvre;
	}

	@Override
	public ArrayList<Oeuvre> chargerBddOeuvreUtilisateur(Utilisateur utilisateur) throws DAOException {
		ArrayList<Oeuvre> oeuvresUtilisateur = new ArrayList<Oeuvre>();
		
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		
		try {
			connection = daoFactory.getConnection();
			preparedStatement = initialisationRequetePreparee(connection, SQL_SELECT_ALL_UTILISATEUR, false, utilisateur.getId());
			resultSet = preparedStatement.executeQuery();
			while (resultSet.next()) {
				oeuvresUtilisateur.add(mapOeuvre(resultSet));
			}
			System.out.println("Oeuvres Utilisateur chargée en session : succès");
		} catch ( SQLException e ){
			throw new DAOException(e);
		} finally {
			fermeturesSilencieuses(resultSet, preparedStatement, connection);
		}
		return oeuvresUtilisateur;
	}

	@Override
	public void supprimer(Long idOeuvreToSuppr) {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		int res;
		
		try {
			connection = daoFactory.getConnection();
			preparedStatement = initialisationRequetePreparee(connection, SQL_DELETE_OEUVRE_ID, false, idOeuvreToSuppr);
			res = preparedStatement.executeUpdate();
			if (res == 1) {
				System.out.println("Entrée supprimée");
			} else {
				System.out.println("Echec de la suppression");
			}
		} catch ( SQLException e ){
			throw new DAOException(e);
		} finally {
			fermeturesSilencieuses(preparedStatement, connection);
		}
	}

	@Override
	public Oeuvre trouver(Long id) throws DAOException {
        Connection connexion = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        Oeuvre oeuvre = null;

        try {
            /* RÃ©cupÃ©ration d'une connexion depuis la Factory */
            connexion = daoFactory.getConnection();
            /*
             * PrÃ©paration de la requÃªte avec les objets passÃ©s en arguments
             * (ici, uniquement une adresse email) et exÃ©cution.
             */
            preparedStatement = initialisationRequetePreparee( connexion, SQL_SELECT_ID, false, id);
            resultSet = preparedStatement.executeQuery();
            /* Parcours de la ligne de donnÃ©es retournÃ©e dans le ResultSet */
            if ( resultSet.next() ) {
                oeuvre = mapOeuvre( resultSet );
                System.out.println("Recherche Oeuvre : Found!");
            }
        } catch ( SQLException e ) {
            throw new DAOException( e );
        } finally {
            fermeturesSilencieuses( resultSet, preparedStatement, connexion );
        }

        return oeuvre;
	}

	@Override
	public void modifier(Oeuvre oeuvre, Long l) throws DAOException {
        Connection connexion = null;
        PreparedStatement preparedStatement = null;
        ResultSet valeursAutoGenerees = null;

        try {
            connexion = daoFactory.getConnection();
            preparedStatement = initialisationRequetePreparee( connexion, SQL_UPDATE_ID, true, oeuvre.getTechnique(), oeuvre.getTitre(), 
            		oeuvre.getHauteur(), oeuvre.getLargeur(), oeuvre.getSupport(), oeuvre.getAnnee(), oeuvre.getPrix(), oeuvre.getCheminFichier(), l );
            if (preparedStatement.executeUpdate() == 0 ) {
                throw new DAOException( "Echec de la modification de l'oeuvre, aucune ligne modifiée dans la table." );
            } else {
            	System.out.println("Oeuvre modifié avec succes");
            }
        } catch ( SQLException e ) {
            throw new DAOException( e );
        } finally {
            fermeturesSilencieuses( valeursAutoGenerees, preparedStatement, connexion );
        }
	}
	
}
