package com.tiagohs.script_reader.dagger.modules

import com.tiagohs.script_reader.interactor.SearchInteractorImpl
import com.tiagohs.script_reader.interactor.contract.CategoryInteractor
import com.tiagohs.script_reader.interactor.contract.HomeInteractor
import com.tiagohs.script_reader.interactor.contract.ReaderInteractor
import com.tiagohs.script_reader.interactor.contract.SearchInteractor
import com.tiagohs.script_reader.presenter.CategoryPresenterImpl
import com.tiagohs.script_reader.presenter.contract.HomePresenter
import com.tiagohs.script_reader.presenter.HomePresenterImpl
import com.tiagohs.script_reader.presenter.ReaderPresenterImpl
import com.tiagohs.script_reader.presenter.SearchPresenterImpl
import com.tiagohs.script_reader.presenter.contract.CategoryPresenter
import com.tiagohs.script_reader.presenter.contract.ReaderPresenter
import com.tiagohs.script_reader.presenter.contract.SearchPresenter
import dagger.Module
import dagger.Provides

@Module
class PresenterModule {

    @Provides
    fun providerHomePresenter(homeInteractor: HomeInteractor): HomePresenter = HomePresenterImpl(homeInteractor)

    @Provides
    fun providerReaderPresenter(readerInteractor: ReaderInteractor): ReaderPresenter = ReaderPresenterImpl(readerInteractor)

    @Provides
    fun providerCategoryPresenter(categoryInteractor: CategoryInteractor): CategoryPresenter = CategoryPresenterImpl(categoryInteractor)

    @Provides
    fun providerSearchPresenter(searchInteractor: SearchInteractor): SearchPresenter = SearchPresenterImpl(searchInteractor)
}