package no.nav.poao_tilgang.test_util

import no.nav.poao_tilgang.service.AuthService
import no.nav.security.mock.oauth2.MockOAuth2Server

open class MockOAuthServer {

	private val azureAdIssuer = "azuread"

	companion object {
		private val server = MockOAuth2Server()
	}

	init {
		server.start()
	}

	fun getDiscoveryUrl(issuer: String = azureAdIssuer): String {
		return server.wellKnownUrl(issuer).toString()
	}

	fun shutdownMockServer() {
		server.shutdown()
	}

	fun issueAzureAdToken(
		subject: String = "test",
		audience: String = "test",
		claims: Map<String, Any> = emptyMap()
	): String {
		return server.issueToken(azureAdIssuer, subject, audience, claims).serialize()
	}

	fun issueAzureAdM2MToken(
		subject: String = "test",
		audience: String = "test",
		claims: Map<String, Any> = emptyMap()
	): String {
		val claimsWithRoles = claims.toMutableMap()
		claimsWithRoles["roles"] = arrayOf(AuthService.ACCESS_AS_APPLICATION_ROLE)

		return server.issueToken(azureAdIssuer, subject, audience, claimsWithRoles).serialize()
	}

}
