package com.tiagohs.domain.presenter.contract

import com.tiagohs.domain.presenter.base.IPresenter
import com.tiagohs.domain.views.ReaderView
import com.tiagohs.entities.Script

interface ReaderPresenter :
    IPresenter {
    fun setArgument(script: Script)
    fun onOpenWithClicked()
}