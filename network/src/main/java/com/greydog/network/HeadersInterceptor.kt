package com.greydog.network

import okhttp3.Interceptor

fun headersInterceptor() = Interceptor { chain ->
    chain.proceed(
            chain.request().newBuilder()
                    .addHeader("Accept", "application/json")
                    .addHeader("Content-Type", "application/json")
                    .build()
    )
}