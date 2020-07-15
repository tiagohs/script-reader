package com.tiagohs.script_reader.dagger.modules

import com.tiagohs.domain.services.ScriptSlugService
import com.tiagohs.helpers.utils.RetrofitUtil
import dagger.Module
import dagger.Provides

@Module
class ServiceModule {

    @Provides
    fun providerScriptSlugService(): ScriptSlugService =
        ScriptSlugService(RetrofitUtil.scriptSlugBuild())
}