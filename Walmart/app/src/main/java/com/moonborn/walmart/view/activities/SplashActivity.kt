package com.moonborn.walmart.view.activities

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.View
import android.view.animation.AnimationUtils
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.activity.viewModels
import androidx.core.view.isVisible
import com.moonborn.walmart.Constants
import com.moonborn.walmart.R
import com.moonborn.walmart.application.WalmartApplication
import com.moonborn.walmart.databinding.ActivitySplashBinding
import com.moonborn.walmart.firebase.FirestoreClass
import com.moonborn.walmart.viewmodel.ProductViewModel
import com.moonborn.walmart.viewmodel.ProductViewModelFactory
import com.moonborn.walmart.viewmodel.WishlistViewModel
import com.moonborn.walmart.viewmodel.WishlistViewModelFactory
import kotlinx.android.synthetic.main.activity_splash.*
import kotlinx.android.synthetic.main.item_loading_dialog.*
import kotlinx.coroutines.*
import java.lang.Runnable


@SuppressLint("CustomSplashScreen")
class SplashActivity : BaseActivity() {
    var binding: ActivitySplashBinding? = null

    val mProductViewModel : ProductViewModel by viewModels {
        ProductViewModelFactory((this.application as WalmartApplication).productsRepository)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        binding = ActivitySplashBinding.inflate(layoutInflater)
//        setContentView(binding?.root)
        setContentView(R.layout.activity_splash)


        walmart_name_tv.animation = AnimationUtils.loadAnimation(this,
            R.anim.logo_animation
        )

        progressBar.webViewClient = object : WebViewClient() {
            override fun shouldOverrideUrlLoading(view: WebView?, url: String?): Boolean {
                if (url != null) {
                    view?.loadUrl(url)
                }
                return true
            }
        }


        val r = Runnable {
            loadFromNetwork()
        }

        Handler().postDelayed(r, 1500)

        refresh.setOnClickListener {
            loadFromNetwork()
        }

        splashActivity = this

    }

    fun loadFromNetwork(){

        progressBar.loadUrl("file:///android_asset/dots.html")

        if (no_internet_lay.isVisible) {
            no_internet_lay.visibility = View.GONE
            load_splash.visibility = View.VISIBLE
        }
        Log.i("Network", "${Constants.isNetworkAvalible(this)}")

        if(Constants.isNetworkAvalible(this) == true){
//            var r = Runnable {  startActivity(Intent(this, MainActivity::class.java)) }
//            Handler().postDelayed(r, SPLASH_DISPLAY_LENGTH.toLong())

            GlobalScope.launch(Dispatchers.Main) { //Your Main UI Thread

                withContext(Dispatchers.IO) {
                    FirestoreClass().loadProducts(this@SplashActivity)
                }

            }


        } else {
            load_splash.visibility = View.GONE
            no_internet_lay.visibility = View.VISIBLE
        }

    }

    fun endSplash(){
        startActivity(Intent(this, MainActivity::class.java))
    }

    companion object{
        var splashActivity: SplashActivity? = null



    }


//    fun webViewSetup(){
//        dotsLoading.webViewClient = WebViewClient()
//
//
//
//    }

}
