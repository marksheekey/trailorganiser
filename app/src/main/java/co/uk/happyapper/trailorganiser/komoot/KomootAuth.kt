package co.uk.happyapper.trailorganiser.komoot

data class KomootAuth(
    val access_token: String? = null,
    val token_type: String? = null,
    val refresh_type: String? = null,
    val refresh_token: String? = null,
    val expires_in: Int? = null,
    val scope: String? = null,
    val username: String? = null,
    val jti: String? = null
)