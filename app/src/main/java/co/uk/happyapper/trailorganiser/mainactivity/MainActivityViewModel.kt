package co.uk.happyapper.trailorganiser.mainactivity

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import co.uk.happyapper.trailorganiser.mainactivity.AppStatus.logIn
import co.uk.happyapper.trailorganiser.mainactivity.AppStatus.logOut
import co.uk.happyapper.trailorganiser.mainactivity.AppStatus.mainScreen
import co.uk.happyapper.trailorganiser.mainactivity.AppStatus.welcomeScreen
import com.google.firebase.auth.FirebaseUser

class MainActivityViewModel: ViewModel(){
    private val firebaseUser: FirebaseUser? = null
    val appStatus: MutableLiveData<AppStatus> = MutableLiveData()

    fun setFirebaseUser(user: FirebaseUser?){
        if(user == null){
            appStatus.postValue(welcomeScreen)
        }else{
            appStatus.postValue(mainScreen)
        }
    }

    fun login(){
        appStatus.postValue(logIn)
    }

    fun logout(){
        appStatus.postValue(logOut)
    }
}

sealed class AppStatus{
    object welcomeScreen: AppStatus()
    object logIn: AppStatus()
    object logOut: AppStatus()
    object mainScreen: AppStatus()
}


