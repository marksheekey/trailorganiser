package co.uk.happyapper.trailorganiser.komoot

interface KomootDataInterface {
    var userToken: String?
    var userName: String?
    var refresh_token: String?
    fun clear(): Boolean
}