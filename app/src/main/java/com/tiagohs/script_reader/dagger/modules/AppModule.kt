package com.tiagohs.script_reader.dagger.modules

import android.app.Application
import android.content.Context
import dagger.Module
import dagger.Provides

@Module
class AppModule(
    val application: Application
) {

    @Provides
    fun providesApplication(): Application {
        return application
    }

    @Provides
    fun providerApplicationContext(): Context {
        return application.applicationContext
    }
}