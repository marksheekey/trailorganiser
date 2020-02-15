package co.uk.happyapper.trailorganiser

import android.app.Application
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class TrailOrganiser : Application() {
    override fun onCreate(){
        super.onCreate()
        // start Koin!
        startKoin {
            // declare used Android context
            androidContext(this@TrailOrganiser)
            // declare modules
            modules(appModule)
        }
    }
}