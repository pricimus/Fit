package com.greydog.network

import android.content.Context
import android.preference.PreferenceManager
import okhttp3.Interceptor
import okhttp3.Response

class AuthorizationInterceptor(private val context: Context) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response? {
        val token = getToken()

        val builder = chain.request().newBuilder()

        if (!token.isNullOrEmpty())
            builder.addHeader("Authorization", token)

        return chain.proceed(builder.build())
    }

    private fun getToken(): String {
        val prefs = PreferenceManager.getDefaultSharedPreferences(context)
        return prefs.getString("token", "")
    }
}