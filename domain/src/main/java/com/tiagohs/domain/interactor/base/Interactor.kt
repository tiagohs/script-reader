package com.tiagohs.domain.interactor.base

import io.reactivex.rxjava3.disposables.CompositeDisposable

abstract class BaseInteractor: IInteractor {

    private var subscribers: CompositeDisposable = CompositeDisposable()

    override fun onDestroy() {
        subscribers.clear()
    }
}