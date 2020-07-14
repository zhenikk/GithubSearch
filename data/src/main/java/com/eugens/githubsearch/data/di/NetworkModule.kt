package com.eugens.githubsearch.data.di

import com.eugens.githubsearch.data.api.GitHubApi
import com.eugens.githubsearch.data.api.RequestInterceptor
import com.google.gson.Gson
import com.itkacher.okhttpprofiler.OkHttpProfilerInterceptor
import hu.akarnokd.rxjava3.retrofit.RxJava3CallAdapterFactory
import okhttp3.OkHttpClient
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


fun createNetworkModule(baseUrl: String) = module {

//    single { LoggingInterceptor() }

    single { Gson() }
    single { OkHttpProfilerInterceptor()}

    single { RequestInterceptor(get()) }


    single {
        OkHttpClient.Builder()
            .addInterceptor(get() as RequestInterceptor)
            .addInterceptor(get() as OkHttpProfilerInterceptor)
            .build()
    }

    single {
        Retrofit.Builder()
            .client(get())
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
            .build()
    }

    factory { get<Retrofit>().create(GitHubApi::class.java) }
}