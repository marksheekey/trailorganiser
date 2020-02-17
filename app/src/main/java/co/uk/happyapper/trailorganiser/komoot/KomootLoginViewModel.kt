package co.uk.happyapper.trailorganiser.komoot

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class KomootLoginViewModel(val komootData: KomootDataInterface, val komootAuthService: KomootAuthServiceInterface) :
    ViewModel() {

    fun gotUserToken(token: String?) {
        komootData.userToken = null
        komootData.userName = null
        token?.let {
            CoroutineScope(Dispatchers.IO).launch {

                val result = komootAuthService.getAccessToken(code = token).await().body()
                komootData.userName = result?.username
                komootData.userToken = result?.access_token
                komootData.refresh_token = result?.refresh_token
            }
        }
    }
}



