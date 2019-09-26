package com.thebrodyaga.frogogo.di.modules

import android.content.Context
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.thebrodyaga.frogogo.domain.ErrorHandler
import com.thebrodyaga.frogogo.frameworks.net.FrogogoApi
import com.thebrodyaga.frogogo.frameworks.net.NetErrorInterceptor
import com.thebrodyaga.frogogo.screen.common.ResourceManager
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.Executors
import javax.inject.Singleton

@Module
class NetworkModule {

    @Provides
    @Singleton
    fun provideFrogogoApi(gson: Gson, netErrorInterceptor: NetErrorInterceptor): FrogogoApi {
        val logging = HttpLoggingInterceptor().apply { level = HttpLoggingInterceptor.Level.BODY }
        val client = OkHttpClient().newBuilder()
            .addInterceptor(logging)
            .addInterceptor(netErrorInterceptor)
            .build()
        return Retrofit.Builder()
            .client(client)
            .baseUrl("https://frogogo-test.herokuapp.com/")
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create(gson))
            .callbackExecutor(Executors.newFixedThreadPool(3))
            .build().create(FrogogoApi::class.java)
    }

    @Provides
    @Singleton
    fun provideNetErrorInterceptor(
        errorHandler: ErrorHandler,
        resourceManager: ResourceManager
    ): NetErrorInterceptor {
        return NetErrorInterceptor(errorHandler, resourceManager)
    }

    @Provides
    @Singleton
    fun provideErrorHandler(resourceManager: ResourceManager): ErrorHandler {
        return ErrorHandler(resourceManager)
    }

    @Provides
    @Singleton
    fun provideResourceManager(context: Context): ResourceManager {
        return ResourceManager(context)
    }

    @Provides
    @Singleton
    fun provideGson() =
        GsonBuilder()
            .setDateFormat("yyyy-MM-dd'T'HH:mm:ss")
            .create()
}