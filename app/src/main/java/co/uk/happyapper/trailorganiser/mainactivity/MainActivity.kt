package co.uk.happyapper.trailorganiser.mainactivity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import co.uk.happyapper.trailorganiser.R
import com.google.android.gms.auth.api.signin.GoogleSignIn
import org.koin.android.ext.android.inject

class MainActivity : AppCompatActivity() {

    val viewModel: MainActivityViewModel by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

    }

    override fun onStart() {
        super.onStart()
        viewModel.setGoogleAccount(GoogleSignIn.getLastSignedInAccount(this))
    }
}
