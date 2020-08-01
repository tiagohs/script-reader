package com.tiagohs.helpers.tools

import retrofit2.CallAdapter
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import java.lang.reflect.Type

/**
 * original in http://bytes.babbel.com/en/articles/2016-03-16-retrofit2-rxjava-error-handling.html
 */
class RxErrorHandlingCallAdapterFactory private constructor() : CallAdapter.Factory() {
    private val original: RxJava3CallAdapterFactory = RxJava3CallAdapterFactory.create()

    override fun get(returnType: Type, annotations: Array<Annotation>, retrofit: Retrofit): CallAdapter<*, *> {
        return RxCallAdapterWrapper(annotations, retrofit, original[returnType, annotations, retrofit] as CallAdapter<Any?, Any?>)
    }

    companion object {
        fun create(): CallAdapter.Factory {
            return RxErrorHandlingCallAdapterFactory()
        }
    }

}