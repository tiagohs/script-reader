package com.tiagohs.script_reader.interactor

import com.tiagohs.script_reader.entities.Script
import com.tiagohs.script_reader.interactor.base.BaseInteractor
import com.tiagohs.script_reader.interactor.contract.SearchInteractor
import com.tiagohs.script_reader.services.ScriptSlugService
import io.reactivex.Observable
import javax.inject.Inject

class SearchInteractorImpl
@Inject constructor(
    val scriptSlugService: ScriptSlugService
) : BaseInteractor(), SearchInteractor {

    override fun searchScripts(query: String): Observable<List<Script>> =
        scriptSlugService.searchScripts(query)
}