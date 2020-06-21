package com.tiagohs.script_reader.interactor.base

import io.reactivex.disposables.CompositeDisposable

abstract class BaseInteractor: IInteractor {

    private var subscribers: CompositeDisposable = CompositeDisposable()

    override fun onDestroy() {
        subscribers.clear()
    }
}