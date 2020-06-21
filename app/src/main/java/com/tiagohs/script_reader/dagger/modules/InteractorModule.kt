package com.tiagohs.script_reader.dagger.modules

import com.tiagohs.script_reader.interactor.HomeInteractorImpl
import com.tiagohs.script_reader.interactor.contract.HomeInteractor
import com.tiagohs.script_reader.services.ScriptSlugService
import dagger.Module
import dagger.Provides

@Module
class InteractorModule {

    @Provides
    fun providerHomeInteractor(scriptSlugService: ScriptSlugService): HomeInteractor = HomeInteractorImpl(scriptSlugService)
}