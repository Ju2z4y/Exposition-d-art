
		<div class="userMenu">
			<h3>Bienvenue ${ sessionScope.sessionUtilisateur.nom }</h3>
			<button><a href="<c:url value="/gestion/listeOeuvreUtilisateur" />">Gérer mes oeuvres</a></button>
			<button><a href="<c:url value="/gestion/ajoutOeuvre" />">Ajouter une oeuvre</a></button>
			<button><a href="<c:url value="/gestion/modificationProfilUtilisateur" />">Modifier mon profil</a></button>
		</div>