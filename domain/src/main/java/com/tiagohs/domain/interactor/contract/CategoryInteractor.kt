package com.tiagohs.domain.interactor.contract

import com.tiagohs.domain.interactor.base.IInteractor
import com.tiagohs.entities.Script
import io.reactivex.rxjava3.core.Observable

interface CategoryInteractor: IInteractor {
    fun fetchScriptsByCategory(url: String, currentPage: Int): Observable<List<Script>>
}