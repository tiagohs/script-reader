package com.tiagohs.domain.interactor.contract

import com.tiagohs.domain.interactor.base.IInteractor
import io.reactivex.Observable
import java.io.InputStream

interface ReaderInteractor: IInteractor {
    fun fetchScriptPDF(scriptUrl: String): Observable<InputStream>
}