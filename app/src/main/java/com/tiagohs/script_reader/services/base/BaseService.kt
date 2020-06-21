package com.tiagohs.script_reader.services.base

import retrofit2.Retrofit

abstract class BaseService(private val buildRetrofit: Retrofit) {

    fun <T> build(mClass: Class<T>): T {
        return buildRetrofit.create(mClass)
    }
}