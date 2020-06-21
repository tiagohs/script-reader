package com.tiagohs.script_reader.ui.fragments.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.tiagohs.script_reader.dagger.AppComponent
import com.tiagohs.script_reader.ui.activities.base.BaseActivity

abstract class BaseFragment: Fragment() {

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

    protected fun getApplicationComponent(): AppComponent? {
        val activity = activity ?: return null
        return (activity as BaseActivity).getApplicationComponent()
    }

    fun openUrl(url: String?) {
        val activity = activity ?: return

        return (activity as BaseActivity).openUrl(url)
    }

    fun isInternetConnected(): Boolean {
        val activity = activity ?: return false

        return (activity as BaseActivity).isInternetConnected()
    }

    abstract fun getViewID(): Int

    open fun onError(ex: Throwable?, message: Int) {
        val activity = activity ?: return

        return (activity as BaseActivity).onError(ex, message)
    }

    /*fun getConfiguratedAd(adView: AdView) {
        val activity = activity ?: return

        return (activity as BaseActivity).getConfiguratedAd(adView)
    }*/

    abstract fun onErrorAction()
}