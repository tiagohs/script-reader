package com.tiagohs.script_reader.presenter

import com.tiagohs.script_reader.R
import com.tiagohs.script_reader.entities.Script
import com.tiagohs.script_reader.helpers.enums.MessageType
import com.tiagohs.script_reader.interactor.contract.ScriptDetailsInteractor
import com.tiagohs.script_reader.presenter.base.BasePresenter
import com.tiagohs.script_reader.presenter.contract.ScriptDetailsPresenter
import com.tiagohs.script_reader.ui.views.ScriptDetailsView
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class ScriptDetailsPresenterImpl
@Inject constructor(
    interactor: ScriptDetailsInteractor
) : BasePresenter<ScriptDetailsView, ScriptDetailsInteractor>(interactor), ScriptDetailsPresenter {

    private var script: Script? = null
    private var scriptUrl: String? = null

    override fun onBindView(view: ScriptDetailsView) {
        super.onBindView(view)

        this.view?.let {
            it.setupArguments()
            it.setTitle(script?.title)
            it.setupContentView()
            it.showLoading()
        }

        fetchScriptDetailsContent()
    }

    override fun setArgument(script: Script) {
        this.script = script
    }

    override fun onSharedClicked() {
        val scriptUrl = scriptUrl ?: return

        view?.shareScript(scriptUrl)
    }

    private fun fetchScriptDetailsContent() {
        val interactor = interactor ?: return

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

        view?.hideLoading()
        view?.showScriptDetails(script)
    }

    private fun onFetchScriptDetailsContentError(error: Throwable) {
        view?.hideLoading()
        view?.showMessage(error, MessageType.ERROR, R.string.unknown_error) {
            val view = view ?: return@showMessage

            onBindView(view)
        }
    }
}