package com.tiagohs.script_reader.services

import com.tiagohs.script_reader.entities.Category
import com.tiagohs.script_reader.entities.Script
import com.tiagohs.script_reader.entities.home.CategoryCell
import com.tiagohs.script_reader.entities.home.HomeCell
import com.tiagohs.script_reader.entities.home.LastestCell
import com.tiagohs.script_reader.helpers.extensions.asJsoup
import com.tiagohs.script_reader.services.base.BaseService
import com.tiagohs.script_reader.services.retrofit.ScriptSlugServiceRetrofit
import io.reactivex.Observable
import org.jsoup.nodes.Document
import retrofit2.Retrofit
import java.io.File
import java.io.InputStream
import java.lang.Exception

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
        mapDocumetToCategoriesCell(responseBody),
        mapDocumentToLatestCell(responseBody)
    )

    private fun mapDocumentToLatestCell(document: Document): LastestCell = LastestCell().apply {
        list = mapDocumentToScriptList(document)
    }

    private fun mapDocumentToScriptList(document: Document): List<Script> =
                    document.select(".site-main .scripts-list.js-scripts-list .scripts.js-scripts .script.js-script")
                            ?.mapNotNull { Script.fromList(it) } ?: emptyList()


    private fun mapDocumetToCategoriesCell(document: Document): CategoryCell = CategoryCell().apply {
        list = document.select(".scripts-browse .scripts-browse__wrap .script-categories-list .script-categories-list__wrap a")
                    ?.mapNotNull { Category.from(it) }
                    ?.sortedBy { it.title } ?: emptyList()
    }

}