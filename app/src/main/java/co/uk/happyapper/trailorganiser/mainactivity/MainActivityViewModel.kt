package co.uk.happyapper.trailorganiser.mainactivity

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import co.uk.happyapper.trailorganiser.mainactivity.AppStatus.gotUser
import co.uk.happyapper.trailorganiser.mainactivity.AppStatus.noFirebaseUser
import com.google.firebase.auth.FirebaseUser

class MainActivityViewModel: ViewModel(){
    private val firebaseUser: FirebaseUser? = null
    val appStatus: MutableLiveData<AppStatus> = MutableLiveData()

    fun setFirebaseUser(user: FirebaseUser?){
        if(user == null){
            appStatus.postValue(noFirebaseUser)
        }else{
            appStatus.postValue(gotUser(user))
        }
    }
}

sealed class AppStatus{
    object noFirebaseUser: AppStatus()
    data class gotUser(val user: FirebaseUser): AppStatus()
}


