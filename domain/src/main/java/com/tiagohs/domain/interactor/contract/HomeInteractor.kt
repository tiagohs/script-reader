package com.tiagohs.domain.interactor.contract

import com.tiagohs.domain.interactor.base.IInteractor
import com.tiagohs.entities.home.HomeCell
import io.reactivex.Observable

interface HomeInteractor : IInteractor {
    fun fetchHomeContent(): Observable<List<HomeCell>>
}