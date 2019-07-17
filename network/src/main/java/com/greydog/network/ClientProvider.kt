package com.greydog.network

import android.content.Context
import android.preference.PreferenceManager
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.squareup.moshi.Moshi
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit

class ClientProvider(val context: Context, private val authorizationInterceptor: AuthorizationInterceptor) {
    private val okHttpClient: OkHttpClient = makeHttpClient()
    val client: Retrofit = createClient()

    private fun createClient(): Retrofit {
        val moshiBuilder = Moshi.Builder()

        val prefs = PreferenceManager.getDefaultSharedPreferences(context)

        //Could create a host interceptor class and pass that in to the list of interceptors below
        val baseURL = prefs.getString("environment", "https://demo9646056.mockable.io/")

        return Retrofit.Builder()
            .addConverterFactory(
                MoshiConverterFactory.create(moshiBuilder.build())
            )
            .addCallAdapterFactory(CoroutineCallAdapterFactory())
            .client(okHttpClient)
                .baseUrl(baseURL)
            .build()
    }

    private fun makeHttpClient(): OkHttpClient {
        val interceptors = listOf(
                headersInterceptor(),
                authorizationInterceptor
        )

        return OkHttpClient.Builder()
                .connectTimeout(60, TimeUnit.SECONDS)
                .readTimeout(60, TimeUnit.SECONDS)
                .apply { interceptors().addAll(interceptors) }
                .build()
    }
}