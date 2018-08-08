<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>Exposition d'art - Accueil</title>
		<link rel="stylesheet" type="text/css" href="../css/style.css" />
	</head>
	<body>
		<c:import url="forms/menuBar.jsp"></c:import>
		<c:import url="forms/outilsUtilisateur.jsp"></c:import>
		
		<form method="post" action="ajoutOeuvre" enctype="multipart/form-data">
            <fieldset>
                <legend>Ajout d'une oeuvre</legend>
                <p>Remplir ce formulaire pour ajouter une oeuvre à votre collection</p>

                <label for="titre">Titre <span class="requis">* </span> </label>
                <input type="text" id="titre" name="titre" value="<c:out value="${ oeuvre.titre }"/>" size="20" maxlength="60" />
                <span class="requis">${ aForm.erreurs['titre'] } </span>
                <br />

                <label for="technique">Technique <span class="requis">* </span> </label>
                <input type="text" id="technique" name="technique" value="<c:out value="${ oeuvre.technique }"/>" size="20" maxlength="20" />
                <span class="requis">${ aForm.erreurs['technique'] }</span>
                <br />

                <label for="support">Support <span class="requis">* </span> </label>
                <input type="text" id="support" name="support" value="<c:out value="${ oeuvre.support }"/>" size="20" maxlength="20" />
                <span class="requis">${ aForm.erreurs['support'] }</span>
                <br />

                <label for="taille">Taille (cm) <span class="requis">* </span> </label>
                L: <input class="tailleOeuvre" type="text" id="taille" name="largeur" value="<c:out value="${ oeuvre.largeur }"/>" size="20" maxlength="20" />
                x H: <input class="tailleOeuvre" type="text" id="taille" name="hauteur" value="<c:out value="${ oeuvre.hauteur }"/>" size="20" maxlength="20" />
                <span class="requis">${ aForm.erreurs['taille']}</span>
                <br />
                
                <label for="annee">Année <span class="requis">* </span> </label>
                <input type="text" id="annee" name="annee" value="<c:out value="${ oeuvre.annee }"/>" size="20" maxlength="20" />
                <span class="requis">${ aForm.erreurs['annee'] }</span>
                <br />
                
                <label for="prix">Prix <span class="requis">* </span> </label>
                <input type="text" id="prix" name="prix" value="<c:out value="${ oeuvre.prix }"/>" size="20" maxlength="20" />
                <span class="requis">${ aForm.erreurs['prix'] }</span>
                <br />
                
                <label for="photoOeuvre">Photo client <span class="requis">* </span> </label>
			    <input type="file" id="photoOeuvre" name="photoOeuvre" value="<c:out value="${ oeuvre.cheminFichier }"/>" size="20" maxlength="60" />
			    <span class="requis">${ aForm.erreurs['photoOeuvre'] }</span>
			    <br /><br />
			    
			    <span class="requis">*</span> Champs obligatoires pour la validation du formulaire.<br />

                <input class="bouton" type="submit" value="Ajout" class="sansLabel" />
                <br />
                
                <p class="${empty aForm.erreurs ? 'succes' : 'requis'}">${aForm.resultat}</p>
            </fieldset>
        </form>
	</body>
</html>