package com.moonborn.walmart.view.activities

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextUtils
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.util.Log
import android.view.View
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.moonborn.walmart.Models.User
import com.moonborn.walmart.R
import com.moonborn.walmart.firebase.FirestoreClass
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.item_loading_dialog.*

class LoginActivity : BaseActivity() {

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)


        auth = FirebaseAuth.getInstance()

        login_user_btn.setOnClickListener {
            loginUser()
        }
        login_activity_back_btn.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
            this.finish()
        }

        login_activity_hidden_ic.setOnClickListener{
            if(login_activity_hidden_ic.isActivated == false){
                password_et_login.transformationMethod = HideReturnsTransformationMethod.getInstance()
                login_activity_hidden_ic.background = resources.getDrawable(R.drawable.password_unhide)
                login_activity_hidden_ic.isActivated = true

            } else {
                password_et_login.transformationMethod = PasswordTransformationMethod.getInstance()
                login_activity_hidden_ic.background = resources.getDrawable(R.drawable.password_hide)
                login_activity_hidden_ic.isActivated = false
            }

        }

        progressBar.loadUrl("file:///android_asset/dots.html")

    }

    fun loginInSuccess(user: User){
//        hideProgressDialog(login_activity)
        load_login.visibility = View.GONE
        startActivity(Intent(this, MainActivity::class.java))
        finish()
    }

    private fun loginUser(){
        val phoneNumber: Editable = num_et_login.text
        val password: String = password_et_login.text.toString().trim() { it <= ' ' }

        if(validateForm(phoneNumber, password)){
//            showProgressDialog(login_activity)
            load_login.visibility = View.VISIBLE
            var email = phoneNumber.toString().toLong().toString() + "@gmail.com"
            auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this) { task ->
//                    hideProgressDialog(login_activity)
                    load_login.visibility = View.GONE
                    if(task.isSuccessful){
                        Log.d("Sign in: ", "SignIn Successful")
                        val user = auth.currentUser
                        startActivity(Intent(this, MainActivity::class.java))
                        FirestoreClass().signInUser(this)
                    } else {
                        Log.w("Sign in: ", "SignIn not Successful", task.exception)
                        Toast.makeText(baseContext, "Authentication failed.",
                        Toast.LENGTH_SHORT).show()
                    }


                }
        }

    }


    private fun validateForm(phoneNumber: Editable, password: String): Boolean{

        return when {
            TextUtils.isEmpty(phoneNumber.toString()) -> {
                showErrorSnackBar("Please enter a phone number")
                false
            }

            TextUtils.isEmpty(password) -> {
                showErrorSnackBar("Please enter a password")
                false
            }else -> {
                true
            }
        }

    }


    override fun onBackPressed() {
        startActivity(Intent(this, MainActivity::class.java))
        return
    }

}