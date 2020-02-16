package co.uk.happyapper.trailorganiser.welcome_screen


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import co.uk.happyapper.trailorganiser.R
import co.uk.happyapper.trailorganiser.global.BaseFragment
import kotlinx.android.synthetic.main.welcome_screen.*

class WelcomeScreenFragment : BaseFragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.welcome_screen, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        sign_in_button.setOnClickListener { viewModel.login() }
    }

}
