package com.tiagohs.script_reader.presenter

import com.tiagohs.script_reader.R
import com.tiagohs.script_reader.entities.Script
import com.tiagohs.script_reader.helpers.enums.MessageType
import com.tiagohs.script_reader.interactor.contract.ReaderInteractor
import com.tiagohs.script_reader.presenter.base.BasePresenter
import com.tiagohs.script_reader.presenter.contract.ReaderPresenter
import com.tiagohs.script_reader.ui.views.ReaderView
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.io.InputStream
import javax.inject.Inject

class ReaderPresenterImpl
@Inject constructor(
    interactor: ReaderInteractor
) : BasePresenter<ReaderView, ReaderInteractor>(interactor), ReaderPresenter {

    private var script: Script? = null

    override fun onBindView(view: ReaderView) {
        super.onBindView(view)

        this.view?.let {
            it.setupArguments()
            it.setTitle(script?.title)
            it.setupContentView()
        }

        fetchScriptPDF()
    }

    override fun setArgument(script: Script) {
        this.script = script
    }

    override fun onOpenWithClicked() {
        val url = script?.scriptURL ?: return

        this.view?.openReaderWith(url)
    }

    private fun fetchScriptPDF() {
        val interactor = interactor ?: return
        val scriptURL = script?.scriptURL ?: return

        view?.showLoadingMessage()

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
        view?.hideLoadingMessage()
        view?.showPDF(file)
    }

    private fun onSearchScriptsError(error: Throwable) {
        view?.hideLoadingMessage()
        view?.showMessage(error, MessageType.ERROR, R.string.unknown_error) {
            val view = view ?: return@showMessage

            onBindView(view)
        }
    }
}