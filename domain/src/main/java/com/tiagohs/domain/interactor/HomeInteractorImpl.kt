package com.tiagohs.domain.interactor

import com.tiagohs.domain.interactor.base.BaseInteractor
import com.tiagohs.domain.interactor.contract.HomeInteractor
import com.tiagohs.domain.services.ScriptSlugService
import com.tiagohs.entities.home.HomeCell
import io.reactivex.Observable
import javax.inject.Inject

class HomeInteractorImpl
@Inject constructor(
    val scriptSlugService: ScriptSlugService
) : BaseInteractor(),
    HomeInteractor {

    override fun fetchHomeContent(): Observable<List<HomeCell>> {
        return scriptSlugService.fetchHomeData()
    }

}