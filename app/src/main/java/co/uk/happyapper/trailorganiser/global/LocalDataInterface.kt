package co.uk.happyapper.trailorganiser.global

interface LocalDataInterface {
    var firebaseToken: String
    var komootUserToken: String?
    fun clear(): Boolean
}