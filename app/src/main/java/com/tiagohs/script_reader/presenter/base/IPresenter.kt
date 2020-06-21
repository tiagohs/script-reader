package com.tiagohs.script_reader.presenter.base

import com.tiagohs.script_reader.ui.views.base.IView

interface IPresenter<V : IView> {

    fun onBindView(view: V)
    fun onDestroy()
}