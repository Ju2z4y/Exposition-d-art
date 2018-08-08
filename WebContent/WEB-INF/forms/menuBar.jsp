	<div id="menuNav">
		<div>
			<h1>Exposition d'art</h1>
			<button><a href="<c:url value="/collectionPeintures" />">Collection de peintures</a></button>
		</div>
					<c:choose>
						<c:when test="${ empty sessionScope.sessionUtilisateur }">
							<div class="right">
								<button><a href="<c:url value="/inscription" />">S'inscrire</a></button>
								<button><a href="<c:url value="/connexion" />">Se connecter</a></button>
							</div>
						</c:when>
						<c:otherwise>
							<div class="right">
								<button><a href="<c:url value="/gestion/accueil" />">Gestion</a></button>
								<button><a href="<c:url value="/deconnexion" />">Se déconnecter</a></button>
							</div>
						</c:otherwise>
					</c:choose>
	</div>
