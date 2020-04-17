package com.aryan.kootlinassignment.repository

import okhttp3.*
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

/**
 *
 * @B ApiUtilis :  This class contain the Base url of server as well as singletone values required.
 **/


class ApiUtilis {
    companion object {

        val BASE_URL = WebConstants.SERVER_URL
        private val httpClient = OkHttpClient.Builder()
        private val builder = Retrofit.Builder()
                .baseUrl(BASE_URL).addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())


        fun getAPIService(): APIService {

            val logging = HttpLoggingInterceptor()
             httpClient.addInterceptor(logging)
             val client = httpClient.connectTimeout(30, TimeUnit.SECONDS)
                    .readTimeout(20, TimeUnit.SECONDS)
                    .writeTimeout(20, TimeUnit.SECONDS).build()
            val retrofit = builder.client(client).build()
            return retrofit.create(APIService::class.java)


        }


    }











}