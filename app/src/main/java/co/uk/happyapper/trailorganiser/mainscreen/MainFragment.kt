package co.uk.happyapper.trailorganiser.mainscreen


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import co.uk.happyapper.trailorganiser.BuildConfig
import co.uk.happyapper.trailorganiser.R
import co.uk.happyapper.trailorganiser.global.BaseFragment
import kotlinx.android.synthetic.main.main_fragment.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainFragment : BaseFragment() {

    private val thisViewModel: MainFragmentViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.main_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if(thisViewModel.komootUserToken==null) {
            access_button.visibility = View.VISIBLE
        }else{
            access_button.visibility = View.GONE
        }

        access_button.setOnClickListener {
            viewModel.webPage("https://account.komoot.com/authorize?client_id="+ BuildConfig.KomootClientId+"&response_type=code&redirect_uri=YOUR_REDIRECT_URL&scope=profile")
        }
    }

}
