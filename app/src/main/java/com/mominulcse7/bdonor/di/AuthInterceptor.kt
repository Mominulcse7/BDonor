package com.mominulcse7.bdonor.di

import com.mominulcse7.bdonor.sessions.LoginPreference
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response
import javax.inject.Inject

class AuthInterceptor @Inject constructor(val pref: LoginPreference) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val builder = request.newBuilder()
            .addHeader("Accept", "application/json")
            .addHeader("X-Requested-With", "XMLHttpRequest")
            .addHeader("Authorization", "Bearer " + pref.gApiToken())
        val newRequest: Request = builder.build()
        return chain.proceed(newRequest)
    }
}