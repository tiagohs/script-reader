package com.tiagohs.script_reader.ui.views.base

import com.tiagohs.script_reader.helpers.enums.MessageType

interface IView {

    fun isInternetConnected(): Boolean
    fun isAdded(): Boolean

    fun showMessage(ex: Throwable?, messageType: MessageType, message: Int = 0, onTryAgainClicked: (() -> Unit)? = null)
    fun showMessage(ex: Throwable?, messageType: MessageType, message: String, onTryAgainClicked: (() -> Unit)? = null)
}