package com.tiagohs.script_reader.ui

import com.tiagohs.domain.interactor.*
import com.tiagohs.domain.interactor.contract.*
import com.tiagohs.domain.presenter.*
import com.tiagohs.domain.presenter.contract.*
import com.tiagohs.domain.services.ScriptSlugService
import com.tiagohs.domain.views.*
import com.tiagohs.helpers.utils.RetrofitUtil
import org.koin.dsl.module

object Injection {
    val modules = listOf(
        module {
            factory {
                ScriptSlugService(RetrofitUtil.scriptSlugBuild())
            }

            //Home
            factory<HomePresenter> { (view: HomeView) ->
                HomePresenterImpl(view, get())
            }

            factory<HomeInteractor> {
                HomeInteractorImpl(get())
            }

            //Category
            factory<CategoryPresenter> { (view: CategoryView) ->
                CategoryPresenterImpl(view, get())
            }

            factory<CategoryInteractor> {
                CategoryInteractorImpl(get())
            }

            //Reader
            factory<ReaderPresenter> { (view: ReaderView) ->
                ReaderPresenterImpl(view, get())
            }

            factory<ReaderInteractor> {
                ReaderInteractorImpl(get())
            }

            //Script Details
            factory<ScriptDetailsPresenter> { (view: ScriptDetailsView) ->
                ScriptDetailsPresenterImpl(view, get())
            }

            factory<ScriptDetailsInteractor> {
                ScriptDetailsInteractorImpl(get())
            }

            //Search
            factory<SearchPresenter> { (view: SearchView) ->
                SearchPresenterImpl(view, get())
            }

            factory<SearchInteractor> {
                SearchInteractorImpl(get())
            }
        }
    )
}