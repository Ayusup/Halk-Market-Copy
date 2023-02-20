package com.moonborn.walmart.model.network

import com.moonborn.walmart.Constants
import com.moonborn.walmart.model.entities.RandomDish
import io.reactivex.rxjava3.core.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface RandomDishApi {

    @GET(Constants.API_ENDPOINT)
    fun getDishes(
        @Query(Constants.API_KEY) apiKey: String,
        @Query(Constants.LIMIT_LICENSE) limitLicense: Boolean,
        @Query(Constants.TAGS) tags: String,
        @Query(Constants.NUMBER) number: Int
    ) : Single<RandomDish.Recipes>

}