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
		<c:choose>
			<c:when test="${!empty sessionScope.oeuvresUtilisateur }">
<table cellspacing="0" cellpadding="0">
	<div class="trhigh">
	<tr>
		<th width="70px">Numéro</th>
		<th width="70px">Titre</th>
		<th width="70px">Technique</th>
		<th width="70px">Support</th>
		<th width="70px">Taille</th>
		<th width="70px">Année</th>
		<th width="70px">Prix</th>
		<th width="70px">Photo</th>
		<th width="70px">Action</th>
	</tr>
	</div>
<c:forEach items="${ sessionScope.oeuvresUtilisateur }" var="oeuvre" varStatus="status">
	<tr height="100px">
		<td><c:out value="${ status.count }" /></td>
		<td><c:out value="${ oeuvre.titre }" /></td>
		<td><c:out value="${ oeuvre.technique }" /></td>
		<td><c:out value="${ oeuvre.support }" /></td>
		<td><c:out value="${ oeuvre.hauteur }" />x<c:out value="${ oeuvre.largeur }" /></td>
		<td><c:out value="${ oeuvre.annee }" /></td>
		<td><c:out value="${ oeuvre.prix }" /></td>
		<td><a href="<c:url value="/images/${ oeuvre.cheminFichier }" />" ><img  width ="100px" src="<c:url value="/images/${ oeuvre.cheminFichier }" />" /></a></td>
		<td>
			<form method="post" action="listeOeuvreUtilisateur">
				<input type="hidden" id="supprIdOeuvre" name="supprIdOeuvre" value="${ oeuvre.id_oeuvre }">
                <input type="submit" value="Supprimer"  />
            </form>
			<form method="post" action="modificationOeuvreUtilisateur">
				<input type="hidden" id="modifIdOeuvre" name="modifIdOeuvre" value="${ oeuvre.id_oeuvre }">
                <input type="submit" value="Modifier"  />
            </form>
		</td>
	</tr>
</c:forEach>
</table>
		</c:when>
		<c:otherwise>
			<p class="requis">Aucune oeuvre enregistrée</p>
		</c:otherwise>
	</c:choose>

	</body>
</html>