package com.tiagohs.script_reader.dagger.modules

import com.tiagohs.script_reader.interactor.CategoryInteractorImpl
import com.tiagohs.script_reader.interactor.HomeInteractorImpl
import com.tiagohs.script_reader.interactor.ReaderInteractorImpl
import com.tiagohs.script_reader.interactor.SearchInteractorImpl
import com.tiagohs.script_reader.interactor.contract.CategoryInteractor
import com.tiagohs.script_reader.interactor.contract.HomeInteractor
import com.tiagohs.script_reader.interactor.contract.ReaderInteractor
import com.tiagohs.script_reader.interactor.contract.SearchInteractor
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
}