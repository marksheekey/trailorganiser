package co.uk.happyapper.trailorganiser.firebase.database

import com.google.firebase.firestore.FirebaseFirestore

class FirebaseDB(val database: FirebaseFirestore) : FirebaseDBInterface {

    override fun addUser(user: FirebaseUser) {
        val reference = database.collection("users")
        reference.document(user.id).set(user)
    }
}