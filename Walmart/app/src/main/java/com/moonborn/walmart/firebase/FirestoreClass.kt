package com.moonborn.walmart.firebase

import android.app.Activity
import android.content.Context
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.webkit.WebView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions
import com.moonborn.walmart.*
import com.moonborn.walmart.view.adapters.BrandsAdapter
import com.moonborn.walmart.view.adapters.CategorieslayoutAdapter
import com.moonborn.walmart.view.fragments.CategroiesFragment
import com.moonborn.walmart.view.fragments.ProductInCategoriesFragment
import com.moonborn.walmart.Models.BrandModel
import com.moonborn.walmart.Models.CategoryModel


import com.moonborn.walmart.Models.User
import com.moonborn.walmart.application.WalmartApplication
import com.moonborn.walmart.model.entities.Product
import com.moonborn.walmart.view.activities.LoginActivity
import com.moonborn.walmart.view.activities.MainActivity
import com.moonborn.walmart.view.activities.SignUpActivity
import com.moonborn.walmart.view.activities.SplashActivity
import com.moonborn.walmart.viewmodel.ProductViewModel
import com.moonborn.walmart.viewmodel.ProductViewModelFactory
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_product_in_categories.view.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class FirestoreClass {

    private val mFirestore = FirebaseFirestore.getInstance()



    fun registerUser(activity: SignUpActivity, userInfo: User){
        mFirestore.collection(Constants.USERS)
            .document(getCurrentUserId())
            .set(userInfo, SetOptions.merge())
            .addOnSuccessListener {
                activity.userRegisteredtSuccess()
            }.addOnFailureListener { e ->
                Log.e(
                    activity.javaClass.simpleName,
                    "Error writing document", e
                )
            }
    }


    fun signInUser(activity: Activity){
        mFirestore.collection(Constants.USERS)
            .document(getCurrentUserId())
            .get()
            .addOnSuccessListener { document ->
                val loggedInUser = document.toObject(User::class.java)
                if(loggedInUser != null) {
                    when (activity) {
                        is LoginActivity -> {
                            activity.loginInSuccess(loggedInUser)
                        }
                        is MainActivity -> {
                            activity.updateUserProfileDetails(loggedInUser)

                        }
                    }
                }

            }.addOnFailureListener { e ->

                when(activity){
                    is LoginActivity -> {
                        activity.hideProgressDialog(activity.login_activity)
                    }
                    is MainActivity -> {
                        activity.hideProgressDialog(activity.main_layout_host)

                    }
                }

                Log.e(
                    "LoginUser",
                    "Error writing document", e
                )
            }
    }

    fun getCurrentUserId(): String{

        var currentUser = FirebaseAuth.getInstance().currentUser
        var currentUserID = ""
        if (currentUser != null) {
            currentUserID = currentUser.uid
        }
        return currentUserID

    }

    fun loadProducts(activity: Activity){
        mFirestore.collection(Constants.PRODUCTS)
            .get()
            .addOnSuccessListener { document ->

                var products = mutableListOf<Product>()
                for(i in document){
                    products.add(i.toObject(Product::class.java))
                }
//                if (products.isNotEmpty()) {
                    when(activity){
                        is SplashActivity -> {
//                            for (i in 1..50) {


//                            GlobalScope.launch(Dispatchers.Main) { //Your Main UI Thread
//                                withContext(Dispatchers.IO) {
//                                    Constants.pList = products
//                                    for (i in Constants.pList) {
//                                        activity.mProductViewModel.insert(i)
//                                    }
//
//                                }
//                                activity.endSplash()
//                            }
                            activity.endSplash()
//                            }


//                            activity.endSplash()
//                            var r = Runnable {}
//                            Handler().postDelayed(r, 2500)



                        }

                    }

//                }




                for(i in Constants.pList){
                    Log.i("P id", "${i.id}")
                }

//                if(loggedInUser != null) {
//                    when (activity) {
//                        is LoginActivity -> {
//                            activity.loginInSuccess(loggedInUser)
//                        }
//                        is MainActivity -> {
//                            activity.updateUserProfileDetails(loggedInUser)
//
//                        }
//                    }
//                }

            }.addOnFailureListener { e ->

                when(activity){
                    is MainActivity -> {
                        activity.hideProgressDialog(activity.main_layout_host)

                    }
                    is SplashActivity -> {
                        activity.loadFromNetwork()
                    }
                }

                Log.e(
                    "LoginUser",
                    "Error writing document", e
                )
            }
    }

    fun loadBrands(context: Context, rootView: ViewGroup, viewToBeHidden: View, recyclerView: RecyclerView){
        viewToBeHidden.visibility = View.GONE
        mFirestore.collection(Constants.BRANDS)
            .get()
            .addOnSuccessListener { document ->

                var brands: MutableList<BrandModel> = mutableListOf()

                for (i in document){
                    brands.add(i.toObject(BrandModel::class.java))
                }

                if(brands.isNotEmpty()){
                    if (context is MainActivity){
                        Constants.brandsList = brands

                        recyclerView.visibility = View.GONE
                        recyclerView.adapter = BrandsAdapter(context, Constants.brandsList)
                        recyclerView.visibility = View.VISIBLE



                    }
                }
                viewToBeHidden.visibility = View.VISIBLE

            }

    }

    fun loadCategories(rootView: ViewGroup, viewToBeHidden: View,  recyclerView: RecyclerView, context: Context, fragment: Fragment): MutableList<CategoryModel> {
//        BaseActivity.loadView(rootView, viewToBeHidden, progressBar, true)

        viewToBeHidden.visibility = View.GONE
        var categories = mutableListOf<CategoryModel>()

        mFirestore.collection(Constants.CATEGORIES)
            .get()
            .addOnSuccessListener { document ->
                Log.i("categoriesLayout", "${viewToBeHidden}")

                var categoriyObjects: MutableList<CategoryModel> = mutableListOf()


                for (i in document){
                    for (j in i.data){
                        categoriyObjects.add(CategoryModel(i.id,
                            j.key,
                            j.value as MutableList<String>))
                    }

                }

                if(context is MainActivity){
                    Constants.categories = categoriyObjects
                    categories = categoriyObjects
                }




                Log.i("categoriesNames", "${Constants.categories}")
//                Constants.setCategories()

                if (fragment is CategroiesFragment){
                    recyclerView.visibility = View.GONE
                    recyclerView.adapter = CategorieslayoutAdapter(context, Constants.categories)
                    recyclerView.visibility = View.VISIBLE
                }

//                recyclerView.adapter!!.notifyDataSetChanged()
//                BaseActivity.loadView(rootView, viewToBeHidden, progressBar, false)

                viewToBeHidden.visibility = View.VISIBLE
            }
            .addOnFailureListener {
                Log.i("Failedddd", "${viewToBeHidden.id}")
                viewToBeHidden.visibility = View.VISIBLE
            }

//        mFirestore.collection(Constants.CATEGORIES)
//            .document("Soft drinks")
//            .get()
//            .addOnSuccessListener { document ->
//                var category = document.get("Soft drinks") as ArrayList<String>
//
//                Log.i("iiiiiia", "${document.get("Soft drinks")}")
////                for (i in category as List<*>){
////                    Log.i("iiiiiiiiiiii", "${i}")
////
////                }
//
////                loadSubCategory("Cold drinks")
//
//            }

        return categories
    }

    fun loadSubCategory(rootView: ViewGroup, subCategory: String, progressBar: WebView) {

        var subCategories: MutableList<String> = mutableListOf()
        mFirestore.collection(Constants.CATEGORIES).document(CategroiesFragment.selectedBaseCategory).collection(subCategory).document(subCategory)
            .get()
            .addOnSuccessListener { document ->


                for (i in document.get(subCategory) as ArrayList<String>) {
                    subCategories.add(i)

                }

                ProductInCategoriesFragment.subCategories.clear()
                ProductInCategoriesFragment.subCategories = subCategories


                val suggestions = rootView.suggestions_recyclerView as RecyclerView
//                BaseActivity.loadView(rootView.products_in_categories_parent_view, rootView.products_in_categories_scrollview,  false)
//                suggestions.adapter!!.notifyDataSetChanged()

                Log.i("selectedBaseCategory", "${ProductInCategoriesFragment.subCategories}")
            }

//        return subCategories

    }




}