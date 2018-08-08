package ea.forms;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.jasypt.util.password.ConfigurablePasswordEncryptor;

import ea.beans.Utilisateur;
import ea.dao.UtilisateurDao;

public final class ConnexionForm {
    private static final String CHAMP_EMAIL  = "email";
    private static final String CHAMP_PASS   = "motdepasse";
    
    private static final String ALGO_CHIFFREMENT = "SHA-256";

    private String              resultat;
    private Map<String, String> erreurs      = new HashMap<String, String>();
    
    private Utilisateur utilisateurBdd = new Utilisateur();

    private UtilisateurDao utilisateurDao;

    public String getResultat() {
        return resultat;
    }

    public Map<String, String> getErreurs() {
        return erreurs;
    }

    public Utilisateur connecterUtilisateur( HttpServletRequest request, UtilisateurDao utilisateurDao ) {
        /* Récupération des champs du formulaire */
    	this.utilisateurDao = utilisateurDao;
        String email = getValeurChamp( request, CHAMP_EMAIL );
        String motDePasse = getValeurChamp( request, CHAMP_PASS );

        Utilisateur utilisateur = new Utilisateur();
        
        

        /* Validation du champ email. */
        try {
            validationEmail( email );
        } catch ( Exception e ) {
            setErreur( CHAMP_EMAIL, e.getMessage() );
        }
        utilisateur.setEmail( email );
        
        try {
        	utilisateurBdd = utilisateurDao.trouver(email);
        } catch (Exception e) {
        	setErreur( CHAMP_EMAIL, "" );
        }

        /* Validation du champ mot de passe. */
        try {
            validationMotDePasse( motDePasse );
        } catch ( Exception e ) {
            setErreur( CHAMP_PASS, e.getMessage() );
        }
        utilisateur.setMotDePasse( motDePasse );

        /* Initialisation du résultat global de la validation. */
        if ( erreurs.isEmpty() ) {
        	try {
        		comparaisonMotDePasse( utilisateur );
        	} catch (Exception e) {
        		setErreur( CHAMP_PASS, e.getMessage() );
        	}
        	
        	if ( erreurs.isEmpty() ) {
        		resultat = "Succès de la connexion.";
        		utilisateur = utilisateurBdd;
        		return utilisateur;
        	} else {
        		resultat = "Échec de la connexion.";
                return utilisateur;
        	}
        	

        } else {
            resultat = "Échec de la connexion.";
            return utilisateur;
        }
    }

    private void comparaisonMotDePasse(Utilisateur utilisateur) throws Exception {
    	
    	/* Validation de la comparaison du mot de passe */
    	
        ConfigurablePasswordEncryptor passwordEncryptor = new ConfigurablePasswordEncryptor();
        passwordEncryptor.setAlgorithm( ALGO_CHIFFREMENT );
        passwordEncryptor.setPlainDigest( false );
        
        if (!passwordEncryptor.checkPassword( utilisateur.getMotDePasse(), utilisateurBdd.getMotDePasse() )) {
        	  throw new Exception ("Mauvais mot de passe saisi pour cette adresse e-mail");
        	}
	}

	/**
     * Valide l'adresse email saisie.
     */
    private void validationEmail( String email ) throws Exception {
        if ( email == null || !email.matches( "([^.@]+)(\\.[^.@]+)*@([^.@]+\\.)+([^.@]+)" ) ) {
            throw new Exception( "Merci de saisir une adresse mail valide." );
        } else {
        	utilisateurBdd = utilisateurDao.trouver(email);
        	if (utilisateurBdd == null) {
        		throw new Exception("Cette adresse email n'est pas enregistrée");
        	}
        }
    }

    /**
     * Valide le mot de passe saisi.
     */
    private void validationMotDePasse( String motDePasse ) throws Exception {
        if ( motDePasse != null ) {
            if ( motDePasse.length() < 3 ) {
                throw new Exception( "Le mot de passe doit contenir au moins 3 caractères." );
            }
        } else {
            throw new Exception( "Merci de saisir votre mot de passe." );
        }
    }

    /*
     * Ajoute un message correspondant au champ spécifié à la map des erreurs.
     */
    private void setErreur( String champ, String message ) {
        erreurs.put( champ, message );
    }

    /*
     * Méthode utilitaire qui retourne null si un champ est vide, et son contenu
     * sinon.
     */
    private static String getValeurChamp( HttpServletRequest request, String nomChamp ) {
        String valeur = request.getParameter( nomChamp );
        if ( valeur == null || valeur.trim().length() == 0 ) {
            return null;
        } else {
            return valeur;
        }
    }
}