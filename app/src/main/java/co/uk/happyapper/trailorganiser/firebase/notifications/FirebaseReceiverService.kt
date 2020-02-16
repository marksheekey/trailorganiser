package co.uk.happyapper.trailorganiser.firebase.notifications


import co.uk.happyapper.trailorganiser.global.LocalDataInterface
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import org.koin.android.ext.android.inject

class FirebaseReceiverService : FirebaseMessagingService() {

    val localdata: LocalDataInterface by inject()

    override fun onMessageReceived(p0: RemoteMessage) {
        super.onMessageReceived(p0)
    }

    override fun onNewToken(token: String) {
        //sendRegistrationToServer(token)
        localdata.firebaseToken = token
    }

}
