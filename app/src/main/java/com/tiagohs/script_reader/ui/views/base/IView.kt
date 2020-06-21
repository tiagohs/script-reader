package com.tiagohs.script_reader.ui.views.base

interface IView {

    fun isInternetConnected(): Boolean
    fun isAdded(): Boolean

    fun onError(ex: Throwable?, message: Int = 0)
    fun onError(ex: Throwable?, message: String)
}