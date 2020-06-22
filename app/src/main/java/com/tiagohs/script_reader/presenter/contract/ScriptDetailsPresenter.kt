package com.tiagohs.script_reader.presenter.contract

import com.tiagohs.script_reader.entities.Script
import com.tiagohs.script_reader.presenter.base.IPresenter
import com.tiagohs.script_reader.ui.views.ScriptDetailsView

interface ScriptDetailsPresenter : IPresenter<ScriptDetailsView> {
    fun setArgument(script: Script)

    fun onSharedClicked()
}