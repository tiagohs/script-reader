package com.tiagohs.helpers.utils

import com.tiagohs.helpers.tools.RxErrorHandlingCallAdapterFactory
import com.google.gson.FieldNamingStrategy
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.lang.reflect.Field
import java.util.*
import java.util.concurrent.TimeUnit

object RetrofitUtil {

    private val loggingInterceptor: HttpLoggingInterceptor
        get() {
            val logging = HttpLoggingInterceptor()
            logging.level = HttpLoggingInterceptor.Level.BODY
            return logging
        }

    fun scriptSlugBuild(): Retrofit {
        return build("https://www.scriptslug.com")
    }

    private fun build(url: String): Retrofit {
        val retrofitBuilder = Retrofit.Builder()
            .baseUrl(url)
            .addConverterFactory(GsonConverterFactory.create(gsonBuilder()))
            .addCallAdapterFactory(RxErrorHandlingCallAdapterFactory.create())
        for (client in clients()) {
            retrofitBuilder.client(client)
        }
        return retrofitBuilder.build()
    }

    private fun clients(): List<OkHttpClient> {
        val clients: MutableList<OkHttpClient> = ArrayList()
        clients.add(clientDefault())

        return clients
    }


    private fun clientDefault(): OkHttpClient {
        val httpClient = OkHttpClient.Builder()

        httpClient.connectTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(30, TimeUnit.SECONDS)

        httpClient.addInterceptor { chain ->
            val original = chain.request()
            val url = original.url.toString()

            val request = original.newBuilder()
                .header("Accept", "application/json")
                .method(original.method, original.body)
                .build()
            chain.proceed(request)
        }

        httpClient.addInterceptor(loggingInterceptor)

        return httpClient.build()
    }

    private fun gsonBuilder(): Gson {
        //https://futurestud.io/tutorials/gson-builder-basics-naming-policies
        val customPolicy = FieldNamingStrategy { f: Field -> f.name.toLowerCase() }
        return GsonBuilder()
            .setFieldNamingStrategy(customPolicy)
            .create()
    }

}