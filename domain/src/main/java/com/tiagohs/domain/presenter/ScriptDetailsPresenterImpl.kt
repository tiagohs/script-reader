package com.tiagohs.domain.presenter

import com.tiagohs.domain.R
import com.tiagohs.domain.interactor.contract.ScriptDetailsInteractor
import com.tiagohs.domain.presenter.base.BasePresenter
import com.tiagohs.domain.presenter.contract.ScriptDetailsPresenter
import com.tiagohs.domain.views.ScriptDetailsView
import com.tiagohs.entities.Script
import com.tiagohs.components.alert_snackbar.enums.MessageType
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers

class ScriptDetailsPresenterImpl(
    override val view: ScriptDetailsView,
    override val interactor: ScriptDetailsInteractor
) : BasePresenter<ScriptDetailsView, ScriptDetailsInteractor>(view, interactor),
    ScriptDetailsPresenter {

    private var script: Script? = null
    private var scriptUrl: String? = null

    override fun start() {
        super.start()

        this.view.apply {
            setupArguments()
            setTitle(script?.title)
            setupContentView()
            showLoading()
        }

        fetchScriptDetailsContent()
    }

    override fun setArgument(script: Script?) {
        this.script = script
    }

    override fun onSharedClicked() {
        val scriptUrl = scriptUrl ?: return

        view.shareScript(scriptUrl)
    }

    private fun fetchScriptDetailsContent() {
        scriptUrl = script?.pageUrl ?: return

        val scriptUrl = scriptUrl ?: return
        add(interactor.fetchScriptDetails(scriptUrl)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { onFetchScriptDetailsContentSuccess(it) },
                { onFetchScriptDetailsContentError(it) }
            )
        )
    }

    private fun onFetchScriptDetailsContentSuccess(script: Script) {
        this.script = script

        view.hideLoading()
        view.showScriptDetails(script)
    }

    private fun onFetchScriptDetailsContentError(error: Throwable) {
        view.hideLoading()
        view.showMessage(error, MessageType.ERROR, R.string.unknown_error) {
            start()
        }
    }
}