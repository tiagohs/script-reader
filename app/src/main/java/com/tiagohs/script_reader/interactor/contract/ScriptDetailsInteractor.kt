package com.tiagohs.script_reader.interactor.contract

import com.tiagohs.script_reader.entities.Script
import com.tiagohs.script_reader.entities.home.HomeCell
import com.tiagohs.script_reader.interactor.base.IInteractor
import io.reactivex.Observable

interface ScriptDetailsInteractor : IInteractor {
    fun fetchScriptDetails(pageUrl: String): Observable<Script>
}