package co.uk.happyapper.trailorganiser.global

import androidx.fragment.app.Fragment
import co.uk.happyapper.trailorganiser.mainactivity.MainActivityViewModel
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

open class BaseFragment : Fragment() {
    val viewModel: MainActivityViewModel by sharedViewModel()
}
