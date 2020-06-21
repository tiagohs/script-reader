package com.tiagohs.script_reader.dagger.modules

import com.tiagohs.script_reader.helpers.utils.RetrofitUtil
import com.tiagohs.script_reader.interactor.contract.HomeInteractor
import com.tiagohs.script_reader.presenter.contract.HomePresenter
import com.tiagohs.script_reader.presenter.HomePresenterImpl
import com.tiagohs.script_reader.services.ScriptSlugService
import dagger.Module
import dagger.Provides

@Module
class ServiceModule {

    @Provides
    fun providerScriptSlugService(): ScriptSlugService = ScriptSlugService(RetrofitUtil.scriptSlugBuild())
}