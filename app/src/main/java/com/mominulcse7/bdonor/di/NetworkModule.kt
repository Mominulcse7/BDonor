package com.mominulcse7.bdonor.di

import com.mominulcse7.bdonor.BuildConfig
import com.mominulcse7.bdonor.network.ApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

@InstallIn(SingletonComponent::class)
@Module
class NetworkModule {

    @Provides
    fun providesRetrofit(httpLoggingInterceptor: HttpLoggingInterceptor, authInterceptor: AuthInterceptor): Retrofit {

        val httpClient = OkHttpClient.Builder()
            .readTimeout(60, TimeUnit.SECONDS)
            .writeTimeout(60, TimeUnit.SECONDS)
            .connectTimeout(60, TimeUnit.SECONDS)
            .addInterceptor(httpLoggingInterceptor)
            .addInterceptor(authInterceptor)

        val okHttpClient = httpClient.build()

        return Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()
    }

    @Provides
    fun printApiReqResponse(): HttpLoggingInterceptor {
        val interceptor = HttpLoggingInterceptor { message: String? ->
            if (BuildConfig.DEBUG)
                println(message)
        }
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
        return interceptor
    }

    @Provides
    fun providesUserAPI(retrofit: Retrofit): ApiService {
        return retrofit.create(ApiService::class.java)
    }
}