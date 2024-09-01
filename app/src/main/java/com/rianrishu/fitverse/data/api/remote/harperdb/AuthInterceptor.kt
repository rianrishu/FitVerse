package com.rianrishu.fitverse.data.api.remote.harperdb

import com.rianrishu.fitverse.BuildConfig
import okhttp3.Credentials
import okhttp3.Interceptor

class AuthInterceptor: Interceptor {

    override fun intercept(chain: Interceptor.Chain) =
        chain.proceed(
            chain.request().newBuilder()
                .addHeader(
                    "Authorization",
                    Credentials.basic(BuildConfig.USERNAME, BuildConfig.PASSWORD)
                )
                .build()
        )
}