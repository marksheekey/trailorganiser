package co.uk.happyapper.trailorganiser.mainactivity

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import co.uk.happyapper.trailorganiser.R
import co.uk.happyapper.trailorganiser.mainactivity.AppStatus.gotUser
import co.uk.happyapper.trailorganiser.mainactivity.AppStatus.noFirebaseUser
import com.firebase.ui.auth.AuthUI
import com.firebase.ui.auth.IdpResponse
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import kotlinx.android.synthetic.main.activity_main.*
import org.koin.android.ext.android.inject

class MainActivity : AppCompatActivity() {

    private val viewModel: MainActivityViewModel by inject()
    private var firebaseAuth: FirebaseAuth? = null
    private val SIGN_IN = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        firebaseAuth = FirebaseAuth.getInstance()
        viewModel.setFirebaseUser(firebaseAuth?.currentUser)
        startObserving()
    }

    private fun startObserving() {
        viewModel.appStatus.observe(this, Observer {
            when (it) {
                is noFirebaseUser -> login()
                is gotUser -> loggedIn(it.user)
            }
        })
    }

    private fun login() {
        login_buttons.visibility = View.VISIBLE
        sign_out_button.visibility = View.GONE
        val providers = arrayListOf(
            AuthUI.IdpConfig.EmailBuilder().build(),
            AuthUI.IdpConfig.GoogleBuilder().build(),
            AuthUI.IdpConfig.FacebookBuilder().build())

        startActivityForResult(
            AuthUI.getInstance()
                .createSignInIntentBuilder()
                .setAvailableProviders(providers)
                .build(),
            SIGN_IN)

    }

    private fun loggedIn(user: FirebaseUser) {
        login_buttons.visibility = View.GONE
        sign_out_button.visibility = View.VISIBLE
        sign_out_button.setOnClickListener {
            AuthUI.getInstance()
                .signOut(this)
                .addOnCompleteListener {
                    viewModel.setFirebaseUser(null)
                }
        }

    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == SIGN_IN) {
            val response = IdpResponse.fromResultIntent(data)
            if (resultCode == Activity.RESULT_OK) {
                // Successfully signed in
                val user = FirebaseAuth.getInstance().currentUser
                viewModel.setFirebaseUser(user)
                // ...
            } else {
               errorMessage(getString(R.string.login_error))
            }
        }
    }


    private fun errorMessage(message: String) {
        Snackbar.make(root_view, message, Snackbar.LENGTH_LONG).show()
    }
}
