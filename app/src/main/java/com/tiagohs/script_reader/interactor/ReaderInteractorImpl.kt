package com.tiagohs.script_reader.interactor

import com.tiagohs.script_reader.interactor.base.BaseInteractor
import com.tiagohs.script_reader.interactor.contract.CategoryInteractor
import com.tiagohs.script_reader.interactor.contract.ReaderInteractor
import com.tiagohs.script_reader.services.ScriptSlugService
import io.reactivex.Observable
import java.io.InputStream
import javax.inject.Inject

class ReaderInteractorImpl
@Inject constructor(
    val scriptSlugService: ScriptSlugService
) : BaseInteractor(), ReaderInteractor {

    override fun fetchScriptPDF(scriptUrl: String): Observable<InputStream> = scriptSlugService.fetchPDFFromUrl(scriptUrl)
}