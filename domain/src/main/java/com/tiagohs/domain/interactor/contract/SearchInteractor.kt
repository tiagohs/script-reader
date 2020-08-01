package com.tiagohs.domain.interactor.contract

import com.tiagohs.domain.interactor.base.IInteractor
import com.tiagohs.entities.Script
import io.reactivex.rxjava3.core.Observable

interface SearchInteractor : IInteractor {
    fun searchScripts(query: String): Observable<List<Script>>
}