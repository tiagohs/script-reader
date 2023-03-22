package com.tiagohs.domain.services.retrofit

import io.reactivex.rxjava3.core.Observable
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.http.Streaming
import retrofit2.http.Url

interface ScriptSlugServiceRetrofit {

    @GET
    fun fetchMovies(@Url url: String): Observable<Response<ResponseBody>>

    @GET
    fun fetchMoviesByCategory(@Url url: String): Observable<Response<ResponseBody>>

    @GET("/")
    fun searchMovie(
        @Query("q") query: String,
        @Query("pg") currentPage: Int
    ): Observable<Response<ResponseBody>>

    @GET
    fun fetchScriptPageDetails(@Url url: String): Observable<Response<ResponseBody>>

    @GET
    @Streaming
    fun fetchPDFFromUrl(@Url url: String): Observable<Response<ResponseBody>>
}