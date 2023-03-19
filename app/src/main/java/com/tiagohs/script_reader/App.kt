package com.tiagohs.script_reader

import android.app.Application
import com.tiagohs.script_reader.ui.Injection
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

class App: Application() {
    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidLogger(Level.NONE)
            androidContext(this@App)
            modules(Injection.modules)
        }

        // MobileAds.initialize(this, BuildConfig.ADMOB_APP_ID);
    }

    companion object {

    }
}