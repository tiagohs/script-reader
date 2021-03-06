package com.tiagohs.script_reader.dagger

import com.tiagohs.script_reader.dagger.modules.AppModule
import com.tiagohs.script_reader.dagger.modules.InteractorModule
import com.tiagohs.script_reader.dagger.modules.PresenterModule
import com.tiagohs.script_reader.dagger.modules.ServiceModule
import com.tiagohs.script_reader.ui.activities.*
import dagger.Component
import javax.inject.Singleton

@Component(modules = arrayOf(
    AppModule::class,
    PresenterModule::class,
    InteractorModule::class,
    ServiceModule::class
))
@Singleton
interface AppComponent {

    fun inject(homeActivity: HomeActivity)
    fun inject(categoryActivity: CategoryActivity)
    fun inject(readerActivity: ReaderActivity)
    fun inject(searchActivity: SearchActivity)
    fun inject(scriptDetailsActivity: ScriptDetailsActivity)
}