package com.moonborn.walmart.view.activities

import android.content.Intent
import android.content.res.ColorStateList
import android.os.Bundle
import android.text.Editable
import android.text.TextUtils
import android.text.TextWatcher
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.view.View
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.moonborn.walmart.Models.User
import com.moonborn.walmart.R
import com.moonborn.walmart.firebase.FirestoreClass
import kotlinx.android.synthetic.main.activity_sign_up.*
import kotlinx.android.synthetic.main.activity_sign_up.num_et_lay
import kotlinx.android.synthetic.main.activity_sign_up.num_ic
import kotlinx.android.synthetic.main.activity_sign_up.password_et_lay
import kotlinx.android.synthetic.main.activity_sign_up.password_ic
import kotlinx.android.synthetic.main.item_loading_dialog.*

class SignUpActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)

        progressBar.loadUrl("file:///android_asset/dots.html")

        sign_up_activity_hidden_ic.setOnClickListener{
            if(sign_up_activity_hidden_ic.isActivated == false){
                sign_up_activity_password_et.transformationMethod = HideReturnsTransformationMethod.getInstance()
                sign_up_activity_hidden_ic.background = resources.getDrawable(R.drawable.password_unhide)
                sign_up_activity_hidden_ic.isActivated = true

            } else {
                sign_up_activity_password_et.transformationMethod = PasswordTransformationMethod.getInstance()
                sign_up_activity_hidden_ic.background = resources.getDrawable(R.drawable.password_hide)
                sign_up_activity_hidden_ic.isActivated = false
            }

        }

        sign_up_activity_back_btn.setOnClickListener {
            finish()
            startActivity(Intent(this, MainActivity::class.java))
        }

        first_name_et.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                if(first_name_et.text.length < 4){
                    val colorStateList: ColorStateList =
                        ColorStateList.valueOf(resources.getColor(R.color.red))
                    first_name_et.backgroundTintList = colorStateList
                    first_name_ic.background = resources.getDrawable(R.drawable.avatar_red)
                    first_name_et_lay.background = resources.getDrawable(R.drawable.registration_edit_text_bg_red)
                } else{
                    val colorStateList: ColorStateList =
                        ColorStateList.valueOf(resources.getColor(R.color.green_cart_color))
                    first_name_et.backgroundTintList = colorStateList
                    first_name_ic.background = resources.getDrawable(R.drawable.avatar_green)
                    first_name_et_lay.background = resources.getDrawable(R.drawable.registration_edit_text_bg_green)
                }
            }

            override fun afterTextChanged(s: Editable) {
            }
        })

        last_name_et.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                if(last_name_et.text.length < 4){
                    val colorStateList: ColorStateList =
                        ColorStateList.valueOf(resources.getColor(R.color.red))
                    last_name_et.backgroundTintList = colorStateList
                    last_name_ic.background = resources.getDrawable(R.drawable.avatar_red)
                    last_name_et_lay.background = resources.getDrawable(R.drawable.registration_edit_text_bg_red)
                } else{
                    val colorStateList: ColorStateList =
                        ColorStateList.valueOf(resources.getColor(R.color.green_cart_color))
                    last_name_et.backgroundTintList = colorStateList
                    last_name_ic.background = resources.getDrawable(R.drawable.avatar_green)
                    last_name_et_lay.background = resources.getDrawable(R.drawable.registration_edit_text_bg_green)
                }
            }

            override fun afterTextChanged(s: Editable) {
            }
        })


        num_et.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                if(num_et.text.length != 12){
                    val colorStateList: ColorStateList =
                        ColorStateList.valueOf(resources.getColor(R.color.red))
                    num_et.backgroundTintList = colorStateList
                    num_ic.background = resources.getDrawable(R.drawable.phone_red)
                    num_et_lay.background = resources.getDrawable(R.drawable.registration_edit_text_bg_red)
                } else{
                    val colorStateList: ColorStateList =
                        ColorStateList.valueOf(resources.getColor(R.color.green_cart_color))
                    num_et.backgroundTintList = colorStateList
                    num_ic.background = resources.getDrawable(R.drawable.phone_green)
                    num_et_lay.background = resources.getDrawable(R.drawable.registration_edit_text_bg_green)
                }
            }

            override fun afterTextChanged(s: Editable) {
            }
        })


        city_et.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                if(city_et.text.length < 2){
                    val colorStateList: ColorStateList =
                        ColorStateList.valueOf(resources.getColor(R.color.red))
                    city_et.backgroundTintList = colorStateList
                    city_ic.background = resources.getDrawable(R.drawable.location_red)
                    city_et_lay.background = resources.getDrawable(R.drawable.registration_edit_text_bg_red)
                } else{
                    val colorStateList: ColorStateList =
                        ColorStateList.valueOf(resources.getColor(R.color.green_cart_color))
                    city_et.backgroundTintList = colorStateList
                    city_ic.background = resources.getDrawable(R.drawable.location_green)
                    city_et_lay.background = resources.getDrawable(R.drawable.registration_edit_text_bg_green)
                }
            }

            override fun afterTextChanged(s: Editable) {
            }
        })


        address_et.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                if(address_et.text.length < 2){
                    val colorStateList: ColorStateList =
                        ColorStateList.valueOf(resources.getColor(R.color.red))
                    address_et.backgroundTintList = colorStateList
                    address_ic.background = resources.getDrawable(R.drawable.navigation_red)
                    address_et_lay.background = resources.getDrawable(R.drawable.registration_edit_text_bg_red)
                } else{
                    val colorStateList: ColorStateList =
                        ColorStateList.valueOf(resources.getColor(R.color.green_cart_color))
                    address_et.backgroundTintList = colorStateList
                    address_ic.background = resources.getDrawable(R.drawable.navigation_green)
                    address_et_lay.background = resources.getDrawable(R.drawable.registration_edit_text_bg_green)
                }
            }

            override fun afterTextChanged(s: Editable) {
            }
        })

        sign_up_activity_password_et.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                if(sign_up_activity_password_et.text.length < 6){
                    val colorStateList: ColorStateList =
                        ColorStateList.valueOf(resources.getColor(R.color.red))
                    sign_up_activity_password_et.backgroundTintList = colorStateList
                    password_ic.background = resources.getDrawable(R.drawable.lock_red)
                    password_et_lay.background = resources.getDrawable(R.drawable.registration_edit_text_bg_red)
                } else{
                    val colorStateList: ColorStateList =
                        ColorStateList.valueOf(resources.getColor(R.color.green_cart_color))
                    sign_up_activity_password_et.backgroundTintList = colorStateList
                    password_ic.background = resources.getDrawable(R.drawable.lock_green)
                    password_et_lay.background = resources.getDrawable(R.drawable.registration_edit_text_bg_green)
                }
            }

            override fun afterTextChanged(s: Editable) {
            }
        })


        register_btn.setOnClickListener {
            registerUser()
//            this.finish()
        }

    }

    fun userRegisteredtSuccess(){
        Toast.makeText(this, "you have " +
            "successfully registered", Toast.LENGTH_SHORT).show()
        load_register.visibility = View.GONE
//        FirebaseAuth.getInstance().signOut()
        finish()
        startActivity(Intent(this, MainActivity::class.java))
    }


    private fun registerUser(){
        val name: String = first_name_et.text.toString().trim() { it <= ' ' }
        val lastName: String = last_name_et.text.toString().trim() { it <= ' ' }
        val phoneNumber: Long = num_et.text.toString().trim() { it <= ' ' }.toLong()
        val city: String = city_et.text.toString().trim() { it <= ' ' }
        val address: String = address_et.text.toString()
        val password: String = sign_up_activity_password_et.text.toString().trim() { it <= ' ' }

        if(validateForm(name, lastName, phoneNumber, city, address, password)){
            var email = phoneNumber.toString() + "@gmail.com"
            load_register.visibility = View.VISIBLE
            register_btn.visibility = View.GONE
            FirebaseAuth.getInstance()
                .createUserWithEmailAndPassword(email, password).addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        val firebseUser: FirebaseUser = task.result!!.user!!
                        val registeredEmail = firebseUser.email!!
                        val user = User(firebseUser.uid, name, lastName, phoneNumber, city, address, password)
                        FirestoreClass().registerUser(this, user)
                    } else {
                        load_register.visibility = View.GONE
                        Toast.makeText(
                            this, "" +
                                    "$name registraition failed, ${task.exception!!.message}",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
        }

    }



    private fun validateForm(
        name: String, lastName: String,
        phoneNumber: Long, city: String,
        adress: String, password: String): Boolean{

        return when {
            TextUtils.isEmpty(name) -> {
                showErrorSnackBar("Please enter a name")
                false
            }
            TextUtils.isEmpty(lastName) -> {
                showErrorSnackBar("Please enter a lastname")
                false
            }
            TextUtils.isEmpty(phoneNumber.toString()) -> {
                showErrorSnackBar("Please enter a phone number")
                false
            }
            TextUtils.isEmpty(city) -> {
                showErrorSnackBar("Please enter a city")
                false
            }
            TextUtils.isEmpty(adress) -> {
                showErrorSnackBar("Please enter an address")
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