package co.uk.happyapper.trailorganiser.mainactivity

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.android.gms.auth.api.signin.GoogleSignInAccount

class MainActivityViewModel: ViewModel(){
    private val googleAccount: GoogleSignInAccount? = null
    val appStatus: MutableLiveData<AppStatus> = MutableLiveData()

    fun setGoogleAccount(account: GoogleSignInAccount?){
        if(account == null){
            appStatus.postValue(AppStatus.noGoogleAccount)
        }else{
            appStatus.postValue(AppStatus.gotGoogleAccount(account))
        }
    }
}

sealed class AppStatus{
    object noGoogleAccount: AppStatus()
    data class gotGoogleAccount(val account: GoogleSignInAccount): AppStatus()
}


