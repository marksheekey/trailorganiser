package co.uk.happyapper.trailorganiser.komoot

import android.content.Context
import android.content.SharedPreferences

private const val KOMOOT_USER_TOKEN = "komoot_user_token"
private const val KOMOOT_USER_NAME = "komoot_user_name"
private const val KOMOOT_REFESH_TOKEN = "komoot_refresh_token"

class KomootData(val context: Context): KomootDataInterface {
    private val preferences: SharedPreferences = context.getSharedPreferences("komoot_local_data", 0)
    override var userToken: String?
    get() = preferences.getString(KOMOOT_USER_TOKEN,null)
    set(value) = preferences.edit().putString(KOMOOT_USER_TOKEN, value).apply()
    override var userName: String?
        get() = preferences.getString(KOMOOT_USER_NAME,null)
        set(value) = preferences.edit().putString(KOMOOT_USER_NAME, value).apply()
    override var refresh_token: String?
        get() = preferences.getString(KOMOOT_REFESH_TOKEN,null)
        set(value) = preferences.edit().putString(KOMOOT_REFESH_TOKEN, value).apply()
    override fun clear() = preferences.edit().clear().commit()
}