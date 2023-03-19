package com.tiagohs.domain.presenter

import com.tiagohs.domain.R
import com.tiagohs.domain.interactor.contract.ReaderInteractor
import com.tiagohs.domain.presenter.base.BasePresenter
import com.tiagohs.domain.presenter.contract.ReaderPresenter
import com.tiagohs.domain.views.ReaderView
import com.tiagohs.entities.Script
import com.tiagohs.components.alert_snackbar.enums.MessageType
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers
import java.io.InputStream

class ReaderPresenterImpl(
    override val view: ReaderView,
    override val interactor: ReaderInteractor
) : BasePresenter<ReaderView, ReaderInteractor>(view, interactor),
    ReaderPresenter {

    private var script: Script? = null

    override fun start() {
        super.start()

        this.view.apply {
            setupArguments()
            setTitle(script?.title)
            setupContentView()
        }

        fetchScriptPDF()
    }

    override fun setArgument(script: Script) {
        this.script = script
    }

    override fun onOpenWithClicked() {
        val url = script?.scriptURL ?: return

        this.view.openReaderWith(url)
    }

    private fun fetchScriptPDF() {
        val scriptURL = script?.scriptURL ?: return

        view.showLoadingMessage()

        add(interactor.fetchScriptPDF(scriptURL)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { onSearchScriptsSuccess(it) },
                { onSearchScriptsError(it) }
            )
        )
    }

    private fun onSearchScriptsSuccess(file: InputStream) {
        view.hideLoadingMessage()
        view.showPDF(file)
    }

    private fun onSearchScriptsError(error: Throwable) {
        view.hideLoadingMessage()
        view.showMessage(error, MessageType.ERROR, R.string.unknown_error) {
            start()
        }
    }
}