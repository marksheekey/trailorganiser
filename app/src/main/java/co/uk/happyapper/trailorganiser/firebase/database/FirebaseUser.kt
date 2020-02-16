package co.uk.happyapper.trailorganiser.firebase.database

data class FirebaseUser(
    val id: String,
    val displayName: String?,
    val email: String?,
    val firebaseToken: String?

)