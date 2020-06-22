package com.tiagohs.script_reader.interactor.contract

import com.tiagohs.script_reader.entities.Script
import com.tiagohs.script_reader.interactor.base.IInteractor
import io.reactivex.Observable
import java.io.InputStream

interface SearchInteractor: IInteractor {
    fun searchScripts(query: String): Observable<List<Script>>
}