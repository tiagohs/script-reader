package com.tiagohs.domain.presenter.base

import com.tiagohs.domain.views.base.IView
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.disposables.Disposable

abstract class BasePresenter<V : IView, I: com.tiagohs.domain.interactor.base.IInteractor>(
    protected open val view: V,
    protected open val interactor: I) :
    IPresenter {

    protected var subscribers: CompositeDisposable = CompositeDisposable()

    override fun start() {}

    override fun onDestroy() {
        subscribers.clear()
    }

    fun add(disposable: Disposable) {
        subscribers.add(disposable)
    }
}
