package com.tiagohs.domain.presenter.base

import com.tiagohs.domain.views.base.IView


interface IPresenter<V : IView> {

    fun onBindView(view: V)
    fun onDestroy()
}