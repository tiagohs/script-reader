package com.tiagohs.domain.interactor.contract

import com.tiagohs.domain.interactor.base.IInteractor
import com.tiagohs.entities.Script
import io.reactivex.Observable

interface CategoryInteractor: IInteractor {
    fun fetchScriptsByCategory(url: String): Observable<List<Script>>
}