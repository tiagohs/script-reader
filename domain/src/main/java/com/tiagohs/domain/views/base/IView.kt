package com.tiagohs.domain.views.base

import com.tiagohs.components.alert_snackbar.enums.MessageType

interface IView {

    fun isInternetConnected(): Boolean
    fun isAdded(): Boolean

    fun showMessage(ex: Throwable?, messageType: com.tiagohs.components.alert_snackbar.enums.MessageType, message: Int = 0, onTryAgainClicked: (() -> Unit)? = null)
    fun showMessage(ex: Throwable?, messageType: com.tiagohs.components.alert_snackbar.enums.MessageType, message: String, onTryAgainClicked: (() -> Unit)? = null)
}