package com.tiagohs.script_reader.interactor.contract

import com.tiagohs.script_reader.interactor.base.IInteractor
import io.reactivex.Observable
import java.io.InputStream

interface ReaderInteractor: IInteractor {
    fun fetchScriptPDF(scriptUrl: String): Observable<InputStream>
}