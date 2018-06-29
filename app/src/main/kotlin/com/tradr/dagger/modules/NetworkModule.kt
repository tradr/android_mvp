package com.tradr.dagger.modules

import android.content.Context
import com.google.gson.Gson
import com.tradr.BuildConfig
import com.tradr.http.DefaultApiCallAdapterFactory
import com.tradr.http.amb.AMBService
import dagger.Module
import dagger.Provides
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import okhttp3.Cache
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.File
import java.util.concurrent.TimeUnit
import javax.inject.Named
import javax.inject.Singleton

@Module
class NetworkModule {
    //////////////////////////////////////////////////
    //////////////////// Retrofit ////////////////////
    //////////////////////////////////////////////////
    @Provides
    @Singleton
    @Named("amb_retrofit")
    internal fun providesAMBRetrofit(okHttpClient: OkHttpClient, gson: Gson): Retrofit {
        return Retrofit.Builder()
                .baseUrl(BuildConfig.AMB_BASE_URL)
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addCallAdapterFactory(DefaultApiCallAdapterFactory.createWithSchedulers(Schedulers.io(), AndroidSchedulers.mainThread()))
                .build()
    }

    @Provides
    @Singleton
    @Named("retrofit_2_amb_service")
    internal fun providesAmbService(@Named("amb_retrofit") retrofit: Retrofit): AMBService {
        return retrofit.create<AMBService>(AMBService::class.java)
    }

    //////////////////////////////////////////////////
    ////////////////// OkHttp Client /////////////////
    //////////////////////////////////////////////////
    @Provides
    @Singleton
    internal fun providesDefaultOkHttpClient(@Named("default_cache") cache: Cache,
                                             @Named("http_logging_interceptor") loggingInterceptor: Interceptor): OkHttpClient {

        return OkHttpClient.Builder()
                .cache(cache)
                .addInterceptor(loggingInterceptor)
                .connectTimeout(20, TimeUnit.SECONDS)
                .readTimeout(90, TimeUnit.SECONDS)
                .build()
    }

    @Provides
    @Singleton
    @Named("default_cache")
    internal fun providesDefaultCache(context: Context): Cache {
        val cacheDir = File(context.cacheDir, "HttpResponseCache")
        return Cache(cacheDir, (10 * 1024 * 1024).toLong())
    }

    @Provides
    @Named("http_logging_interceptor")
    internal fun providesHttpLoggingInterceptor(): Interceptor {
        val interceptor = HttpLoggingInterceptor()
        interceptor.level = if (BuildConfig.ENABLE_LOG_HTTP) HttpLoggingInterceptor.Level.BODY else HttpLoggingInterceptor.Level.BASIC
        return interceptor
    }
}