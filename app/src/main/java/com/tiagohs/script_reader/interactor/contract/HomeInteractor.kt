package com.tiagohs.script_reader.interactor.contract

import com.tiagohs.script_reader.entities.home.HomeCell
import com.tiagohs.script_reader.interactor.base.IInteractor
import io.reactivex.Observable

interface HomeInteractor : IInteractor {
    fun fetchHomeContent(): Observable<List<HomeCell>>
}