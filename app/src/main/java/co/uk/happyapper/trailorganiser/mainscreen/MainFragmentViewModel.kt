package co.uk.happyapper.trailorganiser.mainscreen

import androidx.lifecycle.ViewModel
import co.uk.happyapper.trailorganiser.global.LocalDataInterface

class MainFragmentViewModel(val localData: LocalDataInterface) :
    ViewModel() {
    //var  komootUserToken = localData.komootUserToken
    var komootUserToken = "hello"
}



