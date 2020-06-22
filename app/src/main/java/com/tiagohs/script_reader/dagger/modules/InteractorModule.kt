package com.tiagohs.script_reader.dagger.modules

import com.tiagohs.script_reader.interactor.*
import com.tiagohs.script_reader.interactor.contract.*
import com.tiagohs.script_reader.services.ScriptSlugService
import dagger.Module
import dagger.Provides

@Module
class InteractorModule {

    @Provides
    fun providerHomeInteractor(scriptSlugService: ScriptSlugService): HomeInteractor = HomeInteractorImpl(scriptSlugService)

    @Provides
    fun providerCategoryInteractor(scriptSlugService: ScriptSlugService): CategoryInteractor = CategoryInteractorImpl(scriptSlugService)

    @Provides
    fun providerReaderInteractor(scriptSlugService: ScriptSlugService): ReaderInteractor = ReaderInteractorImpl(scriptSlugService)

    @Provides
    fun providerSearchInteractor(scriptSlugService: ScriptSlugService): SearchInteractor = SearchInteractorImpl(scriptSlugService)

    @Provides
    fun providerScriptDetailsInteractor(scriptSlugService: ScriptSlugService): ScriptDetailsInteractor = ScriptDetailsInteractorImpl(scriptSlugService)
}