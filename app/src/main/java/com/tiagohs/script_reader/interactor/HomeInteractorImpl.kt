package com.tiagohs.script_reader.interactor

import com.tiagohs.script_reader.entities.home.HomeCell
import com.tiagohs.script_reader.interactor.base.BaseInteractor
import com.tiagohs.script_reader.interactor.contract.HomeInteractor
import com.tiagohs.script_reader.services.ScriptSlugService
import io.reactivex.Observable
import javax.inject.Inject

class HomeInteractorImpl
@Inject constructor(
    val scriptSlugService: ScriptSlugService
) : BaseInteractor(), HomeInteractor {

    override fun fetchHomeContent(): Observable<List<HomeCell>> {
        return scriptSlugService.fetchHomeData()

    }

}