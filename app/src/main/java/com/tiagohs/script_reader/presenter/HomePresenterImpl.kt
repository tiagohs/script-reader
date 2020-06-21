package com.tiagohs.script_reader.presenter

import com.tiagohs.script_reader.interactor.contract.HomeInteractor
import com.tiagohs.script_reader.presenter.base.BasePresenter
import com.tiagohs.script_reader.presenter.contract.HomePresenter
import com.tiagohs.script_reader.ui.views.HomeView
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class HomePresenterImpl
@Inject constructor(
    interactor: HomeInteractor
) : BasePresenter<HomeView, HomeInteractor>(interactor), HomePresenter {

    override fun onBindView(view: HomeView) {
        super.onBindView(view)
        val interactor = interactor ?: return

        interactor.fetchMovies()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                    {

                    this.view?.showPDF(it)
                    },
                    {

                    }
                )
    }
}