package com.tiagohs.script_reader.interactor

import com.tiagohs.script_reader.entities.Script
import com.tiagohs.script_reader.entities.home.HomeCell
import com.tiagohs.script_reader.interactor.base.BaseInteractor
import com.tiagohs.script_reader.interactor.contract.HomeInteractor
import com.tiagohs.script_reader.interactor.contract.ScriptDetailsInteractor
import com.tiagohs.script_reader.services.ScriptSlugService
import io.reactivex.Observable
import javax.inject.Inject

class ScriptDetailsInteractorImpl
@Inject constructor(
    val scriptSlugService: ScriptSlugService
) : BaseInteractor(), ScriptDetailsInteractor {

    override fun fetchScriptDetails(pageUrl: String): Observable<Script> = scriptSlugService.fetchScriptPageDetails(pageUrl)
}