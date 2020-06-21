package com.tiagohs.script_reader

import android.app.Application
import com.tiagohs.script_reader.dagger.AppComponent
import com.tiagohs.script_reader.dagger.DaggerAppComponent
import com.tiagohs.script_reader.dagger.modules.AppModule

class App: Application() {
    var appComponent: AppComponent? = null

    override fun onCreate() {
        super.onCreate()

        configureDagger()

        // MobileAds.initialize(this, BuildConfig.ADMOB_APP_ID);
    }

    @Suppress("DEPRECATION")
    private fun configureDagger() {
        appComponent = DaggerAppComponent.builder()
            .appModule(AppModule(this))
            .build()
    }

    companion object {

    }
}