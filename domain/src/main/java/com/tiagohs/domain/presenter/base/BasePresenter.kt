package com.tiagohs.domain.presenter.base

import com.tiagohs.domain.views.base.IView
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.disposables.Disposable

abstract class BasePresenter<V : IView, I: com.tiagohs.domain.interactor.base.IInteractor>(
    protected var interactor: I? = null) :
    IPresenter<V> {

    protected var view: V? = null
    protected var subscribers: CompositeDisposable = CompositeDisposable()

    override fun onBindView(view: V) {
        this.view = view
    }

    override fun onDestroy() {

        if (view != null)
            view = null

        if (interactor != null)
            interactor = null

        subscribers.clear()
    }

    fun add(disposable: Disposable) {
        subscribers.add(disposable)
    }
}
