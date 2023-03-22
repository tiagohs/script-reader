package com.tiagohs.domain.interactor

import com.tiagohs.domain.interactor.base.BaseInteractor
import com.tiagohs.domain.interactor.contract.SearchInteractor
import com.tiagohs.domain.services.ScriptSlugService
import com.tiagohs.entities.Script
import io.reactivex.rxjava3.core.Observable

class SearchInteractorImpl(
    val scriptSlugService: ScriptSlugService
) : BaseInteractor(),
    SearchInteractor {

    override fun searchScripts(query: String, currentPage: Int): Observable<List<Script>> =
        scriptSlugService.searchScripts(query, currentPage)
}