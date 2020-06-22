package com.tiagohs.script_reader.presenter.contract

import com.tiagohs.script_reader.presenter.base.IPresenter
import com.tiagohs.script_reader.ui.views.SearchView

interface SearchPresenter : IPresenter<SearchView> {
    fun searchScripts(query: String)
}