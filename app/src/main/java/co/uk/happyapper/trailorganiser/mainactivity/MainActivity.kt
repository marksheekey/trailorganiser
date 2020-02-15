package co.uk.happyapper.trailorganiser.mainactivity

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import co.uk.happyapper.trailorganiser.R
import co.uk.happyapper.trailorganiser.mainactivity.AppStatus.gotUser
import co.uk.happyapper.trailorganiser.mainactivity.AppStatus.noFirebaseUser
import com.facebook.AccessToken
import com.facebook.CallbackManager
import com.facebook.FacebookCallback
import com.facebook.FacebookException
import com.facebook.login.LoginManager
import com.facebook.login.LoginResult
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FacebookAuthProvider
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
import kotlinx.android.synthetic.main.activity_main.*
import org.koin.android.ext.android.inject

class MainActivity : AppCompatActivity() {

    private val viewModel: MainActivityViewModel by inject()
    private var firebaseAuth: FirebaseAuth? = null
    lateinit var mGoogleSignInClient: GoogleSignInClient
    lateinit var mGoogleSignInOptions: GoogleSignInOptions
    val callbackManager = CallbackManager.Factory.create()
    private val GOOGLE_SIGN_IN = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        login_buttons.visibility = View.GONE
        firebaseAuth = FirebaseAuth.getInstance()
        viewModel.setFirebaseUser(firebaseAuth?.currentUser)
        configureGoogleSignIn()
        startObserving()
    }

    private fun startObserving(){
        viewModel.appStatus.observe(this, Observer {
            when(it){
                is noFirebaseUser -> login()
                is gotUser -> loggedIn(it.user)
            }
        })
    }

    private fun login() {
        login_buttons.visibility = View.VISIBLE
        sign_out_button.visibility = View.GONE
        google_sign_in_button.setOnClickListener {
            val signInIntent: Intent = mGoogleSignInClient.signInIntent
            startActivityForResult(signInIntent, GOOGLE_SIGN_IN)
        }

        facebook_login_button.setReadPermissions("email", "public_profile")
        facebook_login_button.registerCallback(callbackManager, object :
            FacebookCallback<LoginResult> {
            override fun onSuccess(loginResult: LoginResult) {
                handleFacebookAccessToken(loginResult.accessToken)
            }

            override fun onCancel() {
                Log.i("error","cancel")
            }

            override fun onError(error: FacebookException) {
                Log.i("error",error.message+"")
            }
        })


    }

    private fun loggedIn(user: FirebaseUser){
        login_buttons.visibility = View.GONE
        sign_out_button.visibility = View.VISIBLE
        sign_out_button.setOnClickListener{
            firebaseAuth?.signOut()
            LoginManager.getInstance().logOut()
            viewModel.setFirebaseUser(firebaseAuth?.currentUser)
        }
    }

    private fun configureGoogleSignIn() {
        mGoogleSignInOptions = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()
        mGoogleSignInClient = GoogleSignIn.getClient(this, mGoogleSignInOptions)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == GOOGLE_SIGN_IN) {
            val task: Task<GoogleSignInAccount> = GoogleSignIn.getSignedInAccountFromIntent(data)
            try {
                val account = task.getResult(ApiException::class.java)
                account?.let{
                    firebaseAuthWithGoogle(it)
                }

            } catch (e: ApiException) {
                Toast.makeText(this, "Google sign in failed:(", Toast.LENGTH_LONG).show()
            }
        }else{
            callbackManager.onActivityResult(requestCode, resultCode, data);
        }
    }

    private fun firebaseAuthWithGoogle(acct: GoogleSignInAccount) {
        val credential = GoogleAuthProvider.getCredential(acct.idToken, null)
        firebaseAuth?.signInWithCredential(credential)?.addOnCompleteListener {
            if (it.isSuccessful) {
                viewModel.setFirebaseUser(firebaseAuth?.currentUser)
            } else {
                Toast.makeText(this, "Google sign in failed:(", Toast.LENGTH_LONG).show()
            }
        }
    }
    private fun handleFacebookAccessToken(token: AccessToken) {

        val credential = FacebookAuthProvider.getCredential(token.token)
        firebaseAuth?.signInWithCredential(credential)
            ?.addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    viewModel.setFirebaseUser(firebaseAuth?.currentUser)
                } else {
                    Toast.makeText(baseContext, "Authentication failed.",
                        Toast.LENGTH_SHORT).show()
                }
            }
    }


}
