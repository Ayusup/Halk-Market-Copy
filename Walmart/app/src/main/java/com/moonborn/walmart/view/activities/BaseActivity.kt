package com.moonborn.walmart.view.activities

import android.app.Dialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebView
import android.widget.RelativeLayout

import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.core.view.contains
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.moonborn.walmart.R
//import com.moonborn.walmart.Models.Product
import kotlinx.android.synthetic.main.dialog_progress.*

open class BaseActivity : AppCompatActivity() {

    private var doubleBacktoExitPressedOnce = false

    private lateinit var mProgressDialog: Dialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }



    fun hideProgressDialog(view: RelativeLayout) {

        if (progress_bar in view){
            view.removeView(progress_bar)
        }

    //        mProgressDialog.dismiss()
    }

    fun getCurrentUserID(): String{
        return FirebaseAuth.getInstance().currentUser!!.uid
    }

    fun doubleBackToExit(){
        if(doubleBacktoExitPressedOnce) {
            super.onBackPressed()
            return
        }

        this.doubleBacktoExitPressedOnce = true
        Toast.makeText(this, resources.getString(R.string.please_click_back_again_to_exit),
            Toast.LENGTH_SHORT).show()

        Handler().postDelayed({doubleBacktoExitPressedOnce = false}, 2000)

    }


    fun showErrorSnackBar(message: String){
        val snackbar = Snackbar.make(findViewById(android.R.id.content),
            message, Snackbar.LENGTH_LONG)

        val snackbarView = snackbar.view
        snackbarView.setBackgroundColor(ContextCompat.getColor(
            this,
            R.color.snackbar_error_color
        ))
        snackbar.show()

    }

    companion object{

        fun webview(parentView: ViewGroup){
            val webview = LayoutInflater.from(parentView.context).inflate(R.layout.loading_progress_bar, null) as WebView

            webview.layoutParams = ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)

            webview.elevation = -2f

            webview.foregroundGravity = Gravity.CENTER

            webview.loadUrl(parentView.resources.getString(R.string.myurl))

            parentView.foregroundGravity = Gravity.CENTER

            parentView.addView(webview)

        }

//        fun loading(ParentView: ViewGroup, viewToBeHidden: View, hide: Boolean) {

//            ParentView.findViewById<WebView>(R.id.progressBar).webViewClient = object : WebViewClient() {
//                override fun shouldOverrideUrlLoading(view: WebView?, url: String?): Boolean {
//                    if (url != null) {
//                        view?.loadUrl(url)
//                    }
//                    return true
//                }
//            }
//
//            ParentView.progressBar.loadUrl("file:///android_asset/dots.html")

//            var loading = ParentView.findViewById<re>()
//
//            if(hide == true){
//                ParentView.addView()
//                viewToBeHidden.visibility = View.GONE
//            } else {
//                if(loading in ParentView){
//                    ParentView.removeView(loading)
//                }
//                viewToBeHidden.visibility = View.VISIBLE
//            }
//        }


        fun loadView(rootView: ViewGroup, viewToBeHidden: View,progressBar: WebView, load: Boolean) {

            if(load == true) {

                viewToBeHidden.visibility = View.GONE
//                rootView.addView(loadingLayout)

            } else {
                viewToBeHidden.visibility = View.VISIBLE
//                if (rootView.findViewById<WebView>(R.id.progress_bar) in rootView)

            }


        }

        fun hideProgressView(){

        }


    }












}