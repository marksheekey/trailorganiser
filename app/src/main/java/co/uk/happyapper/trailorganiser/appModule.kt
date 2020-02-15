package co.uk.happyapper.trailorganiser

import co.uk.happyapper.trailorganiser.mainactivity.MainActivityViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module{
    viewModel { MainActivityViewModel() }
}