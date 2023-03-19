package com.tiagohs.script_reader.ui.fragments.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.tiagohs.components.alert_snackbar.enums.MessageType
import com.tiagohs.script_reader.ui.activities.base.BaseActivity
import com.tiagohs.domain.views.base.IView

abstract class BaseFragment: Fragment(), IView {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(getViewID(), container, false)
        return view
    }

    protected fun startFragment(fragmentID: Int, fragment: Fragment) {
        val fm = childFragmentManager
        val f = fm.findFragmentById(fragmentID)

        if (null == f) {
            fm.beginTransaction()
                    .add(fragmentID, fragment)
                    .commitAllowingStateLoss()
        } else {
            fm.beginTransaction()
                    .replace(fragmentID, fragment)
                    .commitAllowingStateLoss()
        }
    }

    fun openUrl(url: String?) {
        (activity as? BaseActivity<*>)?.openUrl(url)
    }

    override fun isInternetConnected(): Boolean {
        return (activity as? BaseActivity<*>)?.isInternetConnected() ?: false
    }

    override fun showMessage(
        ex: Throwable?,
        messageType: MessageType,
        message: Int,
        onTryAgainClicked: (() -> Unit)?
    ) {
        (activity as? BaseActivity<*>)?.showMessage(ex, messageType, message, onTryAgainClicked)
    }

    override fun showMessage(
        ex: Throwable?,
        messageType: MessageType,
        message: String,
        onTryAgainClicked: (() -> Unit)?
    ) {
        (activity as? BaseActivity<*>)?.showMessage(ex, messageType, message, onTryAgainClicked)
    }

    abstract fun getViewID(): Int

    open fun onError(ex: Throwable?, message: Int) {
        val activity = activity ?: return

        return (activity as BaseActivity<*>).onError(ex, message)
    }

    /*fun getConfiguratedAd(adView: AdView) {
        val activity = activity ?: return

        return (activity as BaseActivity).getConfiguratedAd(adView)
    }*/

    abstract fun onErrorAction()
}