package ea.forms;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.Part;

import ea.beans.Oeuvre;
import eu.medsea.mimeutil.MimeUtil;

public class ModifOeuvreForm {
	private static final String CHAMP_TITRE = "titre";
	private static final String CHAMP_TECHNIQUE = "technique";
	private static final String CHAMP_SUPPORT = "support";
	private static final String CHAMP_ANNEE = "annee";
	private static final String CHAMP_TAILLE_H = "hauteur";
	private static final String CHAMP_TAILLE_L = "largeur";
	private static final String CHAMP_TAILLE = "taille";
	private static final String CHAMP_PRIX = "prix";
	private static final String CHAMP_PHOTO = "photoOeuvre";
	
	private static final int    TAILLE_TAMPON     = 10240;
	

	private String              resultat;
    private Map<String, String> erreurs      = new HashMap<String, String>();
    

	public Oeuvre ajouterOeuvre (HttpServletRequest request, String chemin) {
		Oeuvre oeuvre = new Oeuvre();
		
		String titre = getValeurChamp(request, CHAMP_TITRE);
		String technique = getValeurChamp(request, CHAMP_TECHNIQUE);
		String support = getValeurChamp(request, CHAMP_SUPPORT);
		String cheminPhoto = "Pas de photo";
		
		int annee = 0;
		int hauteur = 0;
		int largeur = 0;
		int prix = 0;
		
		try {
			hauteur = conversionStringToInt(getValeurChamp(request, CHAMP_TAILLE_H));
			largeur = conversionStringToInt(getValeurChamp(request, CHAMP_TAILLE_L));
		} catch (Exception e) {
			setErreur(CHAMP_TAILLE, e.getMessage());
		}
		oeuvre.setHauteur(hauteur);
		oeuvre.setLargeur(largeur);
		
		try {
			prix = conversionStringToInt(getValeurChamp(request, CHAMP_PRIX));
		} catch (Exception e) {
			setErreur(CHAMP_PRIX, e.getMessage());
		}
		oeuvre.setPrix(prix);
		
		try {
			validationChamps(titre);
		} catch (Exception e) {
			setErreur(CHAMP_TITRE, e.getMessage());
		}
		oeuvre.setTitre(titre);
		
		try {
			validationChamps(technique);
		} catch (Exception e) {
			setErreur(CHAMP_TECHNIQUE, e.getMessage());
		}
		oeuvre.setTechnique(technique);
		
		try {
			validationChamps(support);
		} catch (Exception e) {
			setErreur(CHAMP_SUPPORT, e.getMessage());
		}
		oeuvre.setSupport(support);
		
		try {
			annee = validationAnnee(getValeurChamp(request, CHAMP_ANNEE));
		} catch (Exception e) {
			setErreur(CHAMP_ANNEE, e.getMessage());
		}
		oeuvre.setAnnee(annee);
		
		try {
			cheminPhoto = validationPhoto(request, chemin);
		} catch (Exception e) {
			setErreur("PhotoOeuvre", e.getMessage());
		}
		oeuvre.setCheminFichier(cheminPhoto);
		
		if (erreurs.isEmpty()) {
			resultat = "Succes de la modification";
		} else {
			resultat = "Echec de la modification";
		}
		
		return oeuvre;
		
	}
	
    private String validationPhoto(HttpServletRequest request, String chemin) {
		String photoAValider = null;
		InputStream contenuFichier = null;
	    try {
			Part part = request.getPart(CHAMP_PHOTO);
			
			photoAValider = getNomFichier(part);
			
			if (photoAValider != null || !photoAValider.isEmpty()) {
				photoAValider = photoAValider.substring(photoAValider.lastIndexOf('/') + 1).substring(photoAValider.lastIndexOf('\\') + 1);
				contenuFichier = part.getInputStream();
			}
        } catch ( IllegalStateException e ) {
            /*
             * Exception retournée si la taille des données dépasse les limites
             * définies dans la section <multipart-config> de la déclaration de
             * notre servlet d'upload dans le fichier web.xml
             */
            e.printStackTrace();
            setErreur( CHAMP_PHOTO, "Le volume de la photo dépasse 1Mo." );
		} catch (IOException e1) {
            /*
             * Exception retournée si une erreur au niveau des répertoires de
             * stockage survient (répertoire inexistant, droits d'accès
             * insuffisants, etc.)
             */
            e1.printStackTrace();
            setErreur( CHAMP_PHOTO, "Erreur de configuration du serveur." );
		} catch (ServletException e1) {
            /*
             * Exception retournée si la requête n'est pas de type
             * multipart/form-data. Cela ne peut arriver que si l'utilisateur
             * essaie de contacter la servlet d'upload par un formulaire
             * différent de celui qu'on lui propose... pirate ! :|
             */
            e1.printStackTrace();
            setErreur( CHAMP_PHOTO, "Ce type de requête n'est pas supporté, merci d'utiliser le formulaire prévu pour envoyer votre fichier." );
		}
	    
        if ( photoAValider != null && contenuFichier != null && !photoAValider.equalsIgnoreCase("")) {
            /* Extraction du type MIME du fichier depuis l'InputStream nommé "contenu" */
            MimeUtil.registerMimeDetector( "eu.medsea.mimeutil.detector.MagicMimeMimeDetector" );
            Collection<?> mimeTypes = MimeUtil.getMimeTypes( contenuFichier );
            /*
             * Si le fichier est bien une image, alors son en-tête MIME
             * commence par la chaîne "image"
             */
            if ( mimeTypes.toString().startsWith( "image" ) ) {
                try {
                    ecrireFichier( contenuFichier, photoAValider, chemin );
                } catch ( Exception e ) {
                    setErreur( CHAMP_PHOTO, "Erreur lors de l'écriture du fichier sur le disque." );
                }

            } else {
            	setErreur( CHAMP_PHOTO, "Ce fichier n'est pas une image.");
            }
        } else {
        	return null;
        }
        photoAValider = photoAValider;
        return photoAValider;
	}
    
	private String getNomFichier(Part part) {
    	for (String contentDisposition : part.getHeader("content-disposition").split(";")) {
    		if (contentDisposition.trim().startsWith("filename")) {
    			return contentDisposition.substring(contentDisposition.indexOf("=") + 1).trim().replace("\"", "");
    		}
    	}
		return null;
	}
	
	private void ecrireFichier( InputStream contenu, String nomFichier, String chemin ) throws Exception {
        /* Prépare les flux. */
        BufferedInputStream entree = null;
        BufferedOutputStream sortie = null;
        try {
            /* Ouvre les flux. */
            entree = new BufferedInputStream( contenu, TAILLE_TAMPON );
            sortie = new BufferedOutputStream( new FileOutputStream( new File( chemin + nomFichier ) ),
                    TAILLE_TAMPON );

            /*
             * Lit le fichier reçu et écrit son contenu dans un fichier sur le
             * disque.
             */
            byte[] tampon = new byte[TAILLE_TAMPON];
            int longueur = 0;
            while ( ( longueur = entree.read( tampon ) ) > 0 ) {
                sortie.write( tampon, 0, longueur );	
            }
        } finally {
            try {
                sortie.close();
            } catch ( IOException ignore ) {
            }
            try {
                entree.close();
            } catch ( IOException ignore ) {
            }
        }
    }

	private int validationAnnee (String annee) throws Exception {
    	int res;
    	if (!(annee != null) || !(annee.trim().length() > 3) || !(annee.trim().length() < 5)) {
    		throw new Exception("Merci de remplir ce champs avec 4 caractères");
    	} else {
    		res = conversionStringToInt(annee);
    		return res;
    	}
	}

	@SuppressWarnings("null")
	private void validationChamps(String titre) throws Exception {
    	if ((titre == null) || (titre.trim().length() < 3)) {
    		throw new Exception("Merci de saisir au moins 3 caractères");
    	}
	}

	private static String getValeurChamp( HttpServletRequest request, String nomChamp ) {
        String valeur = request.getParameter( nomChamp );
        if ( valeur == null || valeur.trim().length() == 0 ) {
            return null;
        } else {
            return valeur;
        }
    }
    
    private static int conversionStringToInt (String string) throws Exception {
		int res;
		try {
			res = Integer.parseInt(string);
		} catch (NumberFormatException e) {
			throw new Exception( "Ne pas entrer de lettre(s)" );
		}
		if (res > 0){
        	return res;
		} else {
			throw new Exception( "Entrer un nombre positif" );
		}
    }
    
    private void setErreur( String champ, String message ) {
        erreurs.put( champ, message );
    }
    
    public String getResultat() {
		return resultat;
	}
	public void setResultat(String resultat) {
		this.resultat = resultat;
	}
	public Map<String, String> getErreurs() {
		return erreurs;
	}
	public void setErreurs(Map<String, String> erreurs) {
		this.erreurs = erreurs;
	}
}
