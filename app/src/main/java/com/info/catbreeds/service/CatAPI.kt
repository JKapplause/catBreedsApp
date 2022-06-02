package com.info.catbreeds.service

import com.info.catbreeds.model.Cat
import io.reactivex.Single
import retrofit2.http.GET


interface CatAPI {

    //get , post

     @GET("v1/breeds/")
    fun getCat(): Single<List<Cat>>











}