package com.tiagohs.domain.interactor

import com.tiagohs.domain.interactor.base.BaseInteractor
import com.tiagohs.domain.interactor.contract.CategoryInteractor
import com.tiagohs.domain.services.ScriptSlugService
import com.tiagohs.entities.Script
import io.reactivex.rxjava3.core.Observable

class CategoryInteractorImpl(
    val scriptSlugService: ScriptSlugService
) : BaseInteractor(),
    CategoryInteractor {

    override fun fetchScriptsByCategory(url: String, currentPage: Int): Observable<List<Script>> =
        scriptSlugService.fetchScriptsByCategory(url, currentPage)
}