package com.tiagohs.helpers.tools

import io.reactivex.rxjava3.core.*
import io.reactivex.rxjava3.functions.Function
import retrofit2.Call
import retrofit2.CallAdapter
import retrofit2.Retrofit
import java.lang.reflect.Type

/**
 * original in http://bytes.babbel.com/en/articles/2016-03-16-retrofit2-rxjava-error-handling.html
 */
class RxCallAdapterWrapper internal constructor(
        private val annotations: Array<Annotation>,
        private val retrofit: Retrofit,
        private val callAdapter: CallAdapter<Any?, Any?>
) : CallAdapter<Any?, Any?> {

    override fun responseType(): Type {
        return callAdapter.responseType()
    }

    override fun adapt(call: Call<Any?>): Any? {
        val adaptedCall = callAdapter.adapt(call)

        if (adaptedCall is Observable<*>) {
            return adaptedCall.onErrorResumeNext(Function { Observable.error(handleError(retrofit, it)) })
        }

        if (adaptedCall is Maybe<*>) {
            return adaptedCall.onErrorResumeNext(Function { Maybe.error(handleError(retrofit, it)) })
        }

        if (adaptedCall is Single<*>) {
            return adaptedCall.onErrorResumeNext(Function { Single.error(handleError(retrofit, it)) })
        }

        if (adaptedCall is Flowable<*>) {
            return adaptedCall.onErrorResumeNext(Function { Flowable.error(handleError(retrofit, it)) })
        }

        if (adaptedCall is Completable) {
            return adaptedCall.onErrorResumeNext(Function { Completable.error(handleError(retrofit, it)) })
        }

        return adaptedCall
    }

    private fun handleError(retrofit: Retrofit, throwable: Throwable): Exception {
        return Exception(throwable)
    }
}