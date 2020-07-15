package com.tiagohs.script_reader.dagger.modules

import com.tiagohs.domain.interactor.contract.*
import com.tiagohs.domain.presenter.*
import com.tiagohs.domain.presenter.contract.*
import dagger.Module
import dagger.Provides

@Module
class PresenterModule {

    @Provides
    fun providerHomePresenter(homeInteractor: HomeInteractor): HomePresenter =
        HomePresenterImpl(homeInteractor)

    @Provides
    fun providerReaderPresenter(readerInteractor: ReaderInteractor): ReaderPresenter =
        ReaderPresenterImpl(readerInteractor)

    @Provides
    fun providerCategoryPresenter(categoryInteractor: CategoryInteractor): CategoryPresenter =
        CategoryPresenterImpl(categoryInteractor)

    @Provides
    fun providerSearchPresenter(searchInteractor: SearchInteractor): SearchPresenter =
        SearchPresenterImpl(searchInteractor)

    @Provides
    fun providerScriptDetailsPresenter(scriptDetailsInteractor: ScriptDetailsInteractor): ScriptDetailsPresenter =
        ScriptDetailsPresenterImpl(
            scriptDetailsInteractor
        )
}