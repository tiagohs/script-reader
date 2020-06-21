package com.tiagohs.script_reader.presenter.base

import com.tiagohs.script_reader.interactor.base.IInteractor
import com.tiagohs.script_reader.ui.views.base.IView
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

abstract class BasePresenter<V : IView, I: IInteractor>(
    protected var interactor: I? = null) : IPresenter<V> {

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
