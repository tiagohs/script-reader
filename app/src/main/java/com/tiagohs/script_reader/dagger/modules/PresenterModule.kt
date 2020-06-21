package com.tiagohs.script_reader.dagger.modules

import com.tiagohs.script_reader.interactor.contract.HomeInteractor
import com.tiagohs.script_reader.presenter.contract.HomePresenter
import com.tiagohs.script_reader.presenter.HomePresenterImpl
import dagger.Module
import dagger.Provides

@Module
class PresenterModule {

    @Provides
    fun providerHomePresenter(homeInteractor: HomeInteractor): HomePresenter = HomePresenterImpl(homeInteractor)
}