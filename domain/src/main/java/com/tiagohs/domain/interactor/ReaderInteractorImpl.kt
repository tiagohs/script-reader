package com.tiagohs.domain.interactor

import com.tiagohs.domain.interactor.base.BaseInteractor
import com.tiagohs.domain.interactor.contract.ReaderInteractor
import com.tiagohs.domain.services.ScriptSlugService
import io.reactivex.rxjava3.core.Observable
import java.io.InputStream

class ReaderInteractorImpl(
    val scriptSlugService: ScriptSlugService
) : BaseInteractor(),
    ReaderInteractor {

    override fun fetchScriptPDF(scriptUrl: String): Observable<InputStream> = scriptSlugService.fetchPDFFromUrl(scriptUrl)
}