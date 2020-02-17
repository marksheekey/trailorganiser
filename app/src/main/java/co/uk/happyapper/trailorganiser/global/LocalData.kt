package co.uk.happyapper.trailorganiser.global

import android.content.Context
import android.content.SharedPreferences

private const val FIREBASE_TOKEN = "firebase_token"

class LocalData(val context: Context): LocalDataInterface {
    private val preferences: SharedPreferences = context.getSharedPreferences("local_data", 0)
    override var firebaseToken: String
        get() = preferences.getString(FIREBASE_TOKEN,"").orEmpty()
        set(value) = preferences.edit().putString(FIREBASE_TOKEN, value).apply()
    override fun clear() = preferences.edit().clear().commit()
}