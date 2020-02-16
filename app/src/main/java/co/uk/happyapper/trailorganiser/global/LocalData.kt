package co.uk.happyapper.trailorganiser.global

import android.content.Context
import android.content.SharedPreferences

private const val FIREBASE_TOKEN = "firebase_token"
private const val KOMOOT_USER_TOKEN = "kommot_user_token"

class LocalData(val context: Context): LocalDataInterface {
    private val preferences: SharedPreferences = context.getSharedPreferences("local_data", 0)
    override var firebaseToken: String
        get() = preferences.getString(FIREBASE_TOKEN,"").orEmpty()
        set(value) = preferences.edit().putString(FIREBASE_TOKEN, value).apply()
    override var komootUserToken: String?
        get() = preferences.getString(KOMOOT_USER_TOKEN,null)
        set(value) = preferences.edit().putString(KOMOOT_USER_TOKEN, value).apply()
    override fun clear() = preferences.edit().clear().commit()
}