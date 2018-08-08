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
		<form method="post" action="modificationProfilUtilisateur">
            <fieldset>
                <legend>Modification</legend>
                <p>Vous pouvez vous inscrire via ce formulaire.</p>

                <label for="email">Adresse email</label>
                <input type="email" id="email" name="email" value="<c:out value="${ sessionScope.sessionUtilisateur.email }" /> " size="20" maxlength="60" />
                <span class="requis">${ form.erreurs['email'] } </span>
                <br />

                <label for="motdepasse">Mot de passe</label>
                <input type="password" id="motdepasse" name="motdepasse" value="" size="20" maxlength="20" />
                <span class="requis">${form.erreurs['motdepasse'] }</span>
                <br />

                <label for="confirmation">Confirmation du mot de passe</label>
                <input type="password" id="confirmation" name="confirmation" value="" size="20" maxlength="20" />
                <br />

                <label for="nom">Nom d'utilisateur</label>
                <input type="text" id="nom" name="nom" value="<c:out value="${sessionScope.sessionUtilisateur.nom }" />" size="20" maxlength="20" />
                <span class="requis">${form.erreurs['nom']}</span>
                <br />

                <input type="submit" value="Modification" class="sansLabel" />
                <br />
                
                <p class="${empty form.erreurs ? 'succes' : 'requis'}">${form.resultat}</p>
            </fieldset>
        </form>
	</body>
</html>