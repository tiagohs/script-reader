package com.tiagohs.domain.presenter.contract

import com.tiagohs.domain.presenter.base.IPresenter
import com.tiagohs.domain.views.SearchView

interface SearchPresenter :
    IPresenter {
    fun searchScripts(query: String, currentPage: Int, isFirstPage: Boolean)
}