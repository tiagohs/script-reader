package com.tiagohs.script_reader.interactor

import com.tiagohs.script_reader.entities.Script
import com.tiagohs.script_reader.interactor.base.BaseInteractor
import com.tiagohs.script_reader.interactor.contract.CategoryInteractor
import com.tiagohs.script_reader.services.ScriptSlugService
import io.reactivex.Observable
import javax.inject.Inject

class CategoryInteractorImpl
@Inject constructor(
    val scriptSlugService: ScriptSlugService
) : BaseInteractor(), CategoryInteractor {

    override fun fetchScriptsByCategory(url: String): Observable<List<Script>> =
        scriptSlugService.fetchScriptsByCategory(url)
}