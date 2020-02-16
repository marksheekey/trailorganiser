package co.uk.happyapper.trailorganiser.mainactivity

import android.app.Activity
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.navigation.NavController
import androidx.navigation.findNavController
import co.uk.happyapper.trailorganiser.R
import co.uk.happyapper.trailorganiser.R.string
import co.uk.happyapper.trailorganiser.mainactivity.AppStatus.logIn
import co.uk.happyapper.trailorganiser.mainactivity.AppStatus.logOut
import co.uk.happyapper.trailorganiser.mainactivity.AppStatus.mainScreen
import co.uk.happyapper.trailorganiser.mainactivity.AppStatus.welcomeScreen
import com.firebase.ui.auth.AuthUI
import com.firebase.ui.auth.IdpResponse
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_main.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity() {

    private val viewModel: MainActivityViewModel by viewModel()
    private var firebaseAuth: FirebaseAuth? = null
    private val SIGN_IN = 1
    private lateinit var nav: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        firebaseAuth = FirebaseAuth.getInstance()
        nav = findNavController(R.id.nav_host_fragment)
        viewModel.setFirebaseUser(firebaseAuth?.currentUser)

        startObserving()
    }

    private fun startObserving() {
        viewModel.appStatus.observe(this, Observer {
            when (it) {
                is logOut -> logout()
                is welcomeScreen -> welcomeScreen()
                is logIn -> login()
                is mainScreen -> mainScreen()
            }
        })
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.menus, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle presses on the action bar menu items
        when (item.itemId) {
            R.id.sign_out -> {
                viewModel.logout()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun welcomeScreen() {
        nav.popBackStack()
        getSupportActionBar()?.hide()
        nav.navigate(R.id.welcomeScreenFragment)
    }

    private fun mainScreen() {
        nav.popBackStack()
        getSupportActionBar()?.show()
        nav.navigate(R.id.mainFragment)
    }

    private fun login() {
        val providers = arrayListOf(
            AuthUI.IdpConfig.EmailBuilder().build(),
            AuthUI.IdpConfig.GoogleBuilder().build(),
            AuthUI.IdpConfig.FacebookBuilder().build()
        )

        startActivityForResult(
            AuthUI.getInstance()
                .createSignInIntentBuilder()
                .setAvailableProviders(providers)
                .build(),
            SIGN_IN
        )

    }

    private fun logout() {
        val dialogBuilder = AlertDialog.Builder(this)
        dialogBuilder.setMessage(getString(string.logout_question))
            .setCancelable(false)
            .setPositiveButton(getString(string.yes), DialogInterface.OnClickListener {
                    dialog, id ->  AuthUI.getInstance()
                .signOut(this)
                .addOnCompleteListener {
                    viewModel.setFirebaseUser(null)
                }
            })
            .setNegativeButton(getString(string.no), DialogInterface.OnClickListener {
                    dialog, id -> dialog.cancel()
            })
        dialogBuilder.create().show()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == SIGN_IN) {
            val response = IdpResponse.fromResultIntent(data)
            if (resultCode == Activity.RESULT_OK) {
                // Successfully signed in
                val user = FirebaseAuth.getInstance().currentUser
                viewModel.setFirebaseUser(user)
            } else {
                errorMessage(getString(R.string.login_error))
            }
        }
    }


    private fun errorMessage(message: String) {
        Snackbar.make(root_view, message, Snackbar.LENGTH_LONG).show()
    }
}
