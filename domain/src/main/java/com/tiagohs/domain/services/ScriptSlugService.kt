package com.tiagohs.domain.services

import com.tiagohs.domain.services.base.BaseService
import com.tiagohs.domain.services.retrofit.ScriptSlugServiceRetrofit
import com.tiagohs.entities.Category
import com.tiagohs.entities.Script
import com.tiagohs.entities.home.CategoryCell
import com.tiagohs.entities.home.GenreCell
import com.tiagohs.entities.home.HomeCell
import com.tiagohs.entities.home.ListDefaultCell
import com.tiagohs.helpers.R
import com.tiagohs.helpers.extensions.asJsoup
import io.reactivex.rxjava3.core.Observable
import org.jsoup.nodes.Document
import retrofit2.Retrofit
import java.io.InputStream

class ScriptSlugService(serviceBuild: Retrofit): BaseService(serviceBuild) {

    fun fetchHomeData(): Observable<List<HomeCell>> =
        build(ScriptSlugServiceRetrofit::class.java).fetchMovies("/")
            .map { mapDocumentToHomeData(it.asJsoup()) }

    fun fetchScriptsByCategory(categoryUrl: String): Observable<List<Script>> =
        build(ScriptSlugServiceRetrofit::class.java).fetchMoviesByCategory(categoryUrl)
            .map { mapDocumentToScriptList(it.asJsoup()) }

    fun searchScripts(query: String): Observable<List<Script>> =
        build(ScriptSlugServiceRetrofit::class.java).searchMovie(query)
            .map { mapDocumentToScriptList(it.asJsoup()) }

    fun fetchScriptPageDetails(url: String): Observable<Script> =
        build(ScriptSlugServiceRetrofit::class.java).fetchScriptPageDetails(url)
            .map { Script.fromDetails(it.asJsoup()) }

    fun fetchPDFFromUrl(url: String): Observable<InputStream> =
        build(ScriptSlugServiceRetrofit::class.java).fetchScriptPageDetails(url)
            .map { it.body()?.byteStream() }

    private fun mapDocumentToHomeData(responseBody: Document): List<HomeCell> = listOf(
        GenreCell(),
        mapDocumentToLatestCell(responseBody),
        mapDocumetToCategoriesCell(responseBody)
    )

    private fun mapDocumentToLatestCell(document: Document): ListDefaultCell =
        ListDefaultCell(
            title = R.string.home_list_latest_title,
            list = mapDocumentToScriptList(document)
        )

    private fun mapDocumentToScriptList(document: Document): List<Script> =
                    document.select(".site-main .scripts-list.js-scripts-list .scripts.js-scripts .script.js-script")
                            ?.mapNotNull { Script.fromList(it) } ?: emptyList()

    private fun mapDocumetToCategoriesCell(document: Document): CategoryCell = CategoryCell().apply {
        list = document.select(".scripts-browse .scripts-browse__wrap .script-categories-list .script-categories-list__wrap a")
                    ?.mapNotNull { Category.from(it) }
                    ?.sortedBy { it.title } ?: emptyList()
    }

}