package co.uk.happyapper.trailorganiser

import co.uk.happyapper.trailorganiser.firebase.database.FirebaseDB
import co.uk.happyapper.trailorganiser.firebase.database.FirebaseDBInterface
import co.uk.happyapper.trailorganiser.global.LocalData
import co.uk.happyapper.trailorganiser.global.LocalDataInterface
import co.uk.happyapper.trailorganiser.mainactivity.MainActivityViewModel
import com.google.firebase.firestore.FirebaseFirestore
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module{
    single<LocalDataInterface> { LocalData(androidContext()) }
    single<FirebaseDBInterface> { FirebaseDB(FirebaseFirestore.getInstance()) }
    viewModel { MainActivityViewModel(get(), get()) }
}