package com.tiagohs.domain.presenter.contract

import com.tiagohs.domain.presenter.base.IPresenter
import com.tiagohs.domain.views.ScriptDetailsView
import com.tiagohs.entities.Script

interface ScriptDetailsPresenter :
    IPresenter<ScriptDetailsView> {
    fun setArgument(script: Script?)

    fun onSharedClicked()
}