package com.moonborn.walmart.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.moonborn.walmart.model.entities.Product
import com.moonborn.walmart.model.entities.RandomDish
import com.moonborn.walmart.model.network.RandomDishApiService
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.observers.DisposableSingleObserver
import io.reactivex.rxjava3.schedulers.Schedulers

class RandomDishViewModel: ViewModel() {

    val loaderService = RandomDishApiService()

    val compositeDisposable = CompositeDisposable()

    val loadProduct = MutableLiveData<Boolean>()

    val productFromApiResponse = MutableLiveData<RandomDish.Recipes>()

    val productFromApiLoadingError = MutableLiveData<Boolean>()

    fun getTenItems(){
        for(i in 1..10) {
            getRandomDishFromAPI()
        }
    }


    fun getRandomDishFromAPI(){
        loadProduct.value = true

        Log.i("PreLoad", "1")

        compositeDisposable.add(
            loaderService.getProduct()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object: DisposableSingleObserver<RandomDish.Recipes>(){
                    override fun onSuccess(value: RandomDish.Recipes) {
                        loadProduct.value = false
                        productFromApiResponse.value = value
                        productFromApiLoadingError.value = false
                        Log.i("Success", "1")
                    }

                    override fun onError(e: Throwable) {
                        loadProduct.value = false
                        productFromApiLoadingError.value = true
                        e.printStackTrace()
                        Log.i("Errorrrr", "1")
                    }

                })

        )

    }



    fun convertToProductObject(randomDish: RandomDish.Recipe): Product{
        val product = Product(
            if(randomDish.id != null){randomDish.id.toString()} else{""},
            if(randomDish.title != null){randomDish.title.toString()} else{""},
            if(randomDish.license != null){randomDish.license.toString()} else{""},
            if(randomDish.dishTypes[0] != null){randomDish.dishTypes.toString()} else{""},
            if(randomDish.image != null){randomDish.image.toString()} else{""},
            "online",
            if(randomDish.pricePerServing != null){randomDish.pricePerServing.toDouble()} else{0.00},
            1,
            if (randomDish.veryHealthy==true){ 1 }else{ 0 },
            if (randomDish.veryPopular==true){ 1 }else{ 0 },
            if (randomDish.cheap==true){ 1 }else{ 0 },
            false,
            arrayListOf(),
            0
        )
        return product
    }

}