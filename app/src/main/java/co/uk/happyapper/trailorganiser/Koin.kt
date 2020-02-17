package co.uk.happyapper.trailorganiser

import co.uk.happyapper.trailorganiser.firebase.database.FirebaseDB
import co.uk.happyapper.trailorganiser.firebase.database.FirebaseDBInterface
import co.uk.happyapper.trailorganiser.global.LocalData
import co.uk.happyapper.trailorganiser.global.LocalDataInterface
import co.uk.happyapper.trailorganiser.komoot.KomootAuthService
import co.uk.happyapper.trailorganiser.komoot.KomootData
import co.uk.happyapper.trailorganiser.komoot.KomootDataInterface
import co.uk.happyapper.trailorganiser.komoot.KomootLoginViewModel
import co.uk.happyapper.trailorganiser.mainactivity.MainActivityViewModel
import co.uk.happyapper.trailorganiser.mainscreen.MainFragmentViewModel
import com.google.firebase.firestore.FirebaseFirestore
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module{
    single<LocalDataInterface> { LocalData(androidContext()) }
    single<KomootDataInterface> { KomootData(androidContext()) }
    single<FirebaseDBInterface> { FirebaseDB(FirebaseFirestore.getInstance()) }
    single { KomootAuthService(get()).build }
    single { KomootAuthService(get()) }
    viewModel { MainActivityViewModel(get(), get()) }
    viewModel { MainFragmentViewModel(get())}
    viewModel { KomootLoginViewModel(get(), get())}

}