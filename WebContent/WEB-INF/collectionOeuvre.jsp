<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>Exposition d'art - Accueil</title>
		<link rel="stylesheet" type="text/css" href="css/style.css" />
	</head>
	<body>
		<c:import url="forms/menuBar.jsp"></c:import>
		<h3>Collection de peinture</h3>
		<c:choose>
			<c:when test="${!empty sessionScope.oeuvres }">
				<div class="container">
				<c:forEach items="${ sessionScope.oeuvres }" var="oeuvre" varStatus="status">
					<div class="oeuvre">
						<div class="photoOeuvre"><a href="<c:url value="/images/${ oeuvre.cheminFichier }" />" ><img  width ="200px" src="<c:url value="/images/${ oeuvre.cheminFichier }" />" /></a></div>
						<div class="descriptionOeuvre">
							<h4>Fiche Technique</h4>
							<p>Titre : <c:out value="${ oeuvre.titre }" /></p>
							<p>Technique : <c:out value="${ oeuvre.technique }" /></p>
							<p>Support : <c:out value="${ oeuvre.support }" /></p>
							<p>Dimensions : H:<c:out value="${ oeuvre.hauteur }" />xL:<c:out value="${ oeuvre.largeur }" /> 
							<p>Année : <c:out value="${ oeuvre.annee }" /></p>
							<p>Prix : <c:out value="${ oeuvre.prix }" /></p>
							<p>Artiste : <c:out value="${ oeuvre.utilisateur.nom }" /></p>
						</div>

					</div>
				</c:forEach>
				</div>
		</c:when>
		<c:otherwise>
			<p class="requis">Aucune oeuvre enregistrée</p>
		</c:otherwise>
	</c:choose>

	</body>
</html>