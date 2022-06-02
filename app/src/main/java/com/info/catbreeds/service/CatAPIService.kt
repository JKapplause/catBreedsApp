package com.info.catbreeds.service

import com.info.catbreeds.model.Cat
import io.reactivex.Single
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class CatAPIService {

    private val BASE_URL = "https://api.thecatapi.com/"
    private val api = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .build()
        .create(CatAPI::class.java)


    fun getData() : Single<List<Cat>> {
        return api.getCat()
    }




}