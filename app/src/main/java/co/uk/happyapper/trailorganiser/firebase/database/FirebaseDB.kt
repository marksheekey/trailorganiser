package co.uk.happyapper.trailorganiser.firebase.database

import com.google.firebase.firestore.FirebaseFirestore

class FirebaseDB(val database: FirebaseFirestore) : FirebaseDBInterface {
    val reference = database.collection(co.uk.happyapper.trailorganiser.BuildConfig.FIREBASE_DB)
    override fun addUser(user: FirebaseUser) {
        reference.document("users")
        reference.add(user)
    }
}