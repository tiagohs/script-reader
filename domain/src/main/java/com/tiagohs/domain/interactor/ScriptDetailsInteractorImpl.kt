package com.tiagohs.domain.interactor

import com.tiagohs.domain.interactor.base.BaseInteractor
import com.tiagohs.domain.interactor.contract.ScriptDetailsInteractor
import com.tiagohs.domain.services.ScriptSlugService
import com.tiagohs.entities.Script
import io.reactivex.rxjava3.core.Observable

class ScriptDetailsInteractorImpl(
    val scriptSlugService: ScriptSlugService
) : BaseInteractor(),
    ScriptDetailsInteractor {

    override fun fetchScriptDetails(pageUrl: String): Observable<Script> = scriptSlugService.fetchScriptPageDetails(pageUrl)
}