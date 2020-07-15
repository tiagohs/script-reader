package com.tiagohs.domain.interactor

import com.tiagohs.domain.interactor.base.BaseInteractor
import com.tiagohs.domain.interactor.contract.ReaderInteractor
import com.tiagohs.domain.services.ScriptSlugService
import io.reactivex.Observable
import java.io.InputStream
import javax.inject.Inject

class ReaderInteractorImpl
@Inject constructor(
    val scriptSlugService: ScriptSlugService
) : BaseInteractor(),
    ReaderInteractor {

    override fun fetchScriptPDF(scriptUrl: String): Observable<InputStream> = scriptSlugService.fetchPDFFromUrl(scriptUrl)
}