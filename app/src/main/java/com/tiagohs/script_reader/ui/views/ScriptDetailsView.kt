package com.tiagohs.script_reader.ui.views

import com.tiagohs.script_reader.entities.Script
import com.tiagohs.script_reader.ui.views.base.IView

interface ScriptDetailsView : IView {
    fun setupContentView()
    fun shareScript(scriptUrl: String?)

    fun setupArguments()
    fun setTitle(title: String?)

    fun showScriptDetails(script: Script)

    fun showLoading()
    fun hideLoading()
}