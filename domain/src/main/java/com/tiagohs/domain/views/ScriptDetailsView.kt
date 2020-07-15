package com.tiagohs.domain.views

import com.tiagohs.domain.views.base.IView
import com.tiagohs.entities.Script

interface ScriptDetailsView : IView {
    fun setupContentView()
    fun shareScript(scriptUrl: String?)

    fun setupArguments()
    fun setTitle(title: String?)

    fun showScriptDetails(script: Script)

    fun showLoading()
    fun hideLoading()
}