package com.moonborn.walmart.view.activities

//import kotlinx.android.synthetic.main.display_language_layout.*
//import kotlinx.android.synthetic.main.main_layout.*

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.res.Configuration
import android.os.Bundle
import android.os.Parcelable
import android.util.DisplayMetrics
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.*
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatDelegate
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.google.android.material.tabs.TabLayout
import com.google.firebase.auth.FirebaseAuth
import com.moonborn.walmart.Constants
import com.moonborn.walmart.Models.User
import com.moonborn.walmart.R
import com.moonborn.walmart.application.WalmartApplication
import com.moonborn.walmart.databinding.ActivityMainBinding
import com.moonborn.walmart.firebase.FirestoreClass
import com.moonborn.walmart.model.entities.Cart
import com.moonborn.walmart.model.entities.PlannedListsModel
import com.moonborn.walmart.model.entities.Product
import com.moonborn.walmart.model.entities.Wishlist
import com.moonborn.walmart.view.adapters.*
import com.moonborn.walmart.view.fragments.*
import com.moonborn.walmart.viewmodel.*
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_products_in_planned_list.*
import kotlinx.android.synthetic.main.activity_products_in_planned_list.view.*
import kotlinx.android.synthetic.main.cart_layout.*
import kotlinx.android.synthetic.main.cart_layout_content.*
import kotlinx.android.synthetic.main.profile_layout.*
import kotlinx.android.synthetic.main.tab_item_cart.*
import kotlinx.android.synthetic.main.tab_item_categories.*
import kotlinx.android.synthetic.main.tab_item_main.*
import kotlinx.android.synthetic.main.tab_item_planned.*
import kotlinx.android.synthetic.main.tab_item_wishlist.*
import kotlinx.android.synthetic.main.wishlist_layout.*
import kotlinx.android.synthetic.main.wishlist_layout_content.*
import java.io.File
import java.text.DecimalFormat
import java.util.*


open class MainActivity : BaseActivity(){

    var cur_selected_layout: Array<View?>? = null
    var previous_selected_layout: Array<View?>? = null
    private var binding: ActivityMainBinding? = null



    val adapter = TabPageAdapter(this)

    val mProductViewModel : ProductViewModel by viewModels {
        ProductViewModelFactory((this.application as WalmartApplication).productsRepository)
    }

    private val mWishlistViewModel : WishlistViewModel by viewModels {
        WishlistViewModelFactory((this.application as WalmartApplication).wishlistRepository)
    }

    private val mPlannedListViewModel : PlannedListViewModel by viewModels {
        PlannedListViewModelFactory((this.application as WalmartApplication).plannedListsRepository)
    }

    private val mCartViewModel : CartViewModel by viewModels {
        CartViewModelFactory((this.application as WalmartApplication).cartRepository)
    }

    @SuppressLint("StaticFieldLeak")
    var items_for_recyclerview: MutableMap<String, ArrayList<Product>>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        // Languages
        contextActivity = this
//        setLocale("ru")


//        for (i in Constants.pList){
//            mProductViewModel.insert(i)
//        }


//        mWishlistViewModel.clearTable()

        mWishlistViewModel.allItemsList.observe(this){
                list ->
            for (i in list){
                MainActivity.liked_items_with_multiple_parents.add(i.id)
                Log.i("qwe", "${liked_items_with_multiple_parents}")
            }
            MainActivity.item_num_above_wishlist(MainActivity.liked_items_with_multiple_parents)


        }

        mCartViewModel.allItemsList.observe(this){
            list -> list.let {
                for (i in list){
                    items_added_to_cart.put(i.id, i.quantity)
                }
            }
            MainActivity.item_num_above_cart()

        }

//        MainFragment().exitTransition = resources.getAnimation(R.anim.layout_move_left_exit_animation)

//        mproductViewModel.clearTable()




//        GlobalScope.launch(Dispatchers.Main) { //Your Main UI Thread
//
//            withContext(Dispatchers.IO) {
//                for (i in Constants.pList) {
//                    val product =
//                        ProductModel(
//                            i.getId(),
//                            i.getName(),
//                            i.getBrand(),
//                            i.getCategory(),
//                            i.getImage(),
//                            Constants.DISH_IMAGE_SOURCE_ONLINE,
//                            i.getPrice(),
//                            i.getNew(),
//                            i.getPromoSale(),
//                            i.getPopular(),
//                            i.getOnSale(),
//                            false
//                        )
//                    Log.i("iname", "${product.name}")
//                    mproductViewModel.insert(product)
//                }
//
//
//
//            }
//
//        }






//        CallAPILoginAsyncTask().execute()
//        items_added_to_cart = dbHandler.getItemFromDB()

//        if(Constants.itemsInCart.isEmpty()) {
//            for(i in items_added_to_cart){
//                productsByID.get(i.key)?.let { Constants.itemsInCart.add(it) }
////                MainActivity.update_similar_views_add_to_cart(productsByID.get(i.key)!!, MainActivity.itemsWithMultipleParents)
//            }
//
//        }
//        liked_items_with_multiple_parents = dbHandler.getItemFromWishlistDB()
//        if(Constants.itemsInWishlist.isEmpty()){
//            for(i in liked_items_with_multiple_parents){
//                productsByID.get(i)?.let { Constants.itemsInWishlist.add(it) }
////                MainActivity.update_similar_views_like_btn(i)
//            }
//        }


//        MainActivity.update_similar_views_add_to_cart()



//        if(Constants.listOfPlannedLists.isEmpty()){
//            var itemsInPlannedList = mutableListOf<ProductModel>()
//            for(i in dbHandler.getListFromDB()){
////                itemsInPlannedList.add()
//
//
//            }
//        }

//        Constants.listOfPlannedLists = dbHandler.getListFromDB()

            for(i in 0 until Constants.listOfPlannedLists.size){
            Log.i("Products ppin PlannedList", "${Constants.listOfPlannedLists[i].ItemsInList}")
        }





//        //Initializing adapters
//        items_for_recyclerview = Constants.sort_by_deals()
//        val newProductsAdapter = NewProductsAdapter(this, items_for_recyclerview!!["new_products"]!!)
//        Log.i("new Product", "${items_for_recyclerview!!["new_products"]!!}")
//        val promoProductsAdapter = PromotionsAndSalesAdapter(this, items_for_recyclerview!!["promoSale"]!!)
//        val popularProductsAdapter = PopularProductsAdapter(this, items_for_recyclerview!!["popular"]!!)
//        val salesProductsAdapter = SalesAdapter(this, items_for_recyclerview!!["onSale"]!!)
//        val searchProductsAdapter = SearchProductsAdapter(this, search_products)
//        val ProductsInCartAdapter = ProductsInCartAdapter(this, Constants.itemsInCart)
//        val ProductsInWishlistAdapter = ProductsInWishlistAdapter(this, Constants.itemsInWishlist)


//        val NewProductsContent = LayoutInflater.from(this).inflate(R.layout.new_products_content, null)
//        val PromotionsAndSalesContent = LayoutInflater.from(this).inflate(R.layout.promotions_and_sales_content, null)
//        val PopularProductsContent = LayoutInflater.from(this).inflate(R.layout.popular_products_content, null)
//        val SalesContent = LayoutInflater.from(this).inflate(R.layout.sales_content, null)
//        val SearchProductsContent = LayoutInflater.from(this).inflate(R.layout.search_layout_content, null)
        val plannedListContent = LayoutInflater.from(this).inflate(R.layout.planned_list_layout_content, null)



//        categoriesProductsContent = LayoutInflater.from(this).inflate(R.layout.categories_content, null)

        val firestoreClass = FirestoreClass()

        firestoreClass.loadProducts(this)


        MainActivity.mPlannedListViewModel = mPlannedListViewModel

        binding = ActivityMainBinding.inflate(layoutInflater)
        mainActivityBinding = binding
        setContentView(binding?.root)
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR)
        getSupportActionBar()?.setDisplayShowTitleEnabled(false)


        webview(main_layout_host)




        setupTabBar()

        Companion.items_in_wishlist = items_in_wishlist
        Companion.items_in_cart = items_in_cart
        Companion.cart_btn = cart_btn
        pieces_text = resources.getString(R.string.pieces)

        item_num_above_cart()
        item_num_above_wishlist(liked_items_with_multiple_parents)



//        progressbar.visibility = View.GONE
//        content_activity_scrollview.visibility = View.VISIBLE

        var displayMetrics = DisplayMetrics()
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        var displayHeight: Int = displayMetrics.heightPixels
        var displayWidth: Int = displayMetrics.widthPixels
        main_layout_host.layoutParams.width = displayWidth
        activity_main_layout.layoutParams.width = ViewGroup.LayoutParams.WRAP_CONTENT




        products_in_planned_list_top_bar_back_btn.setOnClickListener {
            products_in_planned_list_lay.visibility = View.GONE
            mainActivityBinding!!.activityMainLayout.products_in_planned_list_layout_scrollview.removeAllViews()
//            selectedPlannedListItems.clear()
        }


        registration.setOnClickListener {
            finish()
            this.startActivity(Intent(this, SignUpActivity::class.java))
        }
        login_btn.setOnClickListener {
            this.startActivity(Intent(this, LoginActivity::class.java))
            this.finish()
        }
        logout_btn.setOnClickListener {
            FirebaseAuth.getInstance().signOut()
            user_functionality.visibility = View.GONE
            login_register_lay.visibility = View.VISIBLE
            logout_btn.visibility = View.GONE
            profile_info_lay.visibility = View.GONE
        }



        // Change language layout clickers
        display_language.setOnClickListener{

            this.supportFragmentManager.beginTransaction()
                .replace(android.R.id.content, DisplayLanguageFragment(), "lang")
                .addToBackStack(null)
                .commit()

//            this.startActivity(Intent(this, DisplayLanguageActivity::class.java))
//            this.finish()
//            tabLayout.visibility = View.GONE

        }
        var currentUserID = FirestoreClass().getCurrentUserId()
        Log.i("is logged in", "${currentUserID}")
        if(currentUserID.isEmpty()){
            user_functionality.visibility = View.GONE
            login_register_lay.visibility = View.VISIBLE
            logout_btn.visibility = View.GONE
            profile_info_lay.visibility = View.GONE
        }else{
            user_functionality.visibility = View.VISIBLE
            login_register_lay.visibility = View.GONE
            logout_btn.visibility = View.VISIBLE
            profile_info_lay.visibility = View.VISIBLE
        }

        if(currentUserID.isNotEmpty())
            FirestoreClass().signInUser(this@MainActivity)


        lever2.setOnClickListener{
            activity_main_layout.transitionToStart()


        }






    }


    fun setupTabBar() {
        fragments_viewPager.adapter = adapter
        fragments_viewPager.offscreenPageLimit = 5
        var fragmentsList = listOf<Fragment>(MainFragment(), CategroiesFragment(), CartFragment(), WishlistFragment(), PlannedListsFragment())
        adapter.updateList(fragmentsList)

        fragments_viewPager.isUserInputEnabled = false
        tabLayout.getTabAt(0)!!.setCustomView(R.layout.tab_item_main)
        tabLayout.getTabAt(1)!!.setCustomView(R.layout.tab_item_categories)
        tabLayout.getTabAt(2)!!.setCustomView(R.layout.tab_item_cart)
        tabLayout.getTabAt(3)!!.setCustomView(R.layout.tab_item_wishlist)
        tabLayout.getTabAt(4)!!.setCustomView(R.layout.tab_item_planned)


        fragments_viewPager.registerOnPageChangeCallback(object: ViewPager2.OnPageChangeCallback()
        {
            override fun onPageSelected(position: Int) {
                tabLayout.selectTab(tabLayout.getTabAt(position))
            }
        })

        tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener{
            override fun onTabSelected(tab: TabLayout.Tab) {
                fragments_viewPager.setCurrentItem(tab.position, true)
//                As I use custom view for two tabs I need to manually change the text color when selected
                if (tab == tabLayout.getTabAt(0)){
                    main_btn_txt.setTextColor(resources.getColor(R.color.color_Accent))
                } else {
                    main_btn_txt.setTextColor(resources.getColor(R.color.black))
                }
                if (tab == tabLayout.getTabAt(1)){
                    categories_btn_txt.setTextColor(resources.getColor(R.color.color_Accent))
                } else {
                    categories_btn_txt.setTextColor(resources.getColor(R.color.black))
                }
                if (tab == tabLayout.getTabAt(2)){
                    cart_btn_txt.setTextColor(resources.getColor(R.color.color_Accent))
                } else {
                    cart_btn_txt.setTextColor(resources.getColor(R.color.black))
                }
                if (tab == tabLayout.getTabAt(3)){
                    wishlist_btn_txt.setTextColor(resources.getColor(R.color.color_Accent))
                } else {
                    wishlist_btn_txt.setTextColor(resources.getColor(R.color.black))
                }
                if (tab == tabLayout.getTabAt(4)){
                    planned_btn_txt.setTextColor(resources.getColor(R.color.color_Accent))
                } else {
                    planned_btn_txt.setTextColor(resources.getColor(R.color.black))
                }

            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
            }

            override fun onTabReselected(tab: TabLayout.Tab?) {
            }
        })

    }


    fun updateUserProfileDetails(user: User){
        hello_user_tv.text = "Hello, " + user.name
        user_adress.text = user.address
    }



    fun openProductlayout(product: Parcelable, container: Int, tag: String){


        val bundle = Bundle()
        bundle.putParcelable("product", product)

        val productFragment = ProductFragment()

        this.supportFragmentManager.beginTransaction()
            .replace(container, productFragment, tag)
            .addToBackStack(null)
            .commit()
        productFragment.arguments = bundle


    }

//    fun getPlannedListFromDB(mutableList: MutableList<String>): PlannedListModel{
//        var itemsInListIds = mutableList[4]
//        var itemsInList = mutableListOf<ProductModel>()
//        for(i in itemsInListIds.split(" ")){
//            productsByID.get(i)?.let { itemsInList.add(it) }
//        }
//        return PlannedListModel(
//            mutableList[0],
//            mutableList[1],
//            mutableList[2],
//            mutableList[3],
//            itemsInList
//        )
//    }



    fun View.hideKeyboard() {
        val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(windowToken, 0)
    }


//    override fun onAttachedToWindow() {
//        super.onAttachedToWindow()
//        window.decorView.layoutDirection = Locale.getDefault().layoutDirection
//    }



    override fun onDestroy() {
        File(this.cacheDir.path).deleteRecursively()
        binding = null
        super.onDestroy()

    }

    override fun onPause() {

        mProductViewModel.clearTable()
        mWishlistViewModel.clearTable()
        for(i in MainActivity.liked_items_with_multiple_parents){
            mWishlistViewModel.insert(Wishlist(i))
        }
        mCartViewModel.clearTable()

        for (i in items_added_to_cart){
            mCartViewModel.insert(Cart(i.key, i.value))
        }

        super.onPause()
    }

    fun remove_item_from_cart_layout(adapter: ProductsInCartAdapter){
        if(items_added_to_cart.size > 0){
            adapter.differ.submitList(null)
            adapter.differ.submitList(Constants.itemsInCart)
        } else {
            amount_of_products_cart_layout.visibility = View.GONE
            empty_cart_layout.visibility = View.VISIBLE
        }

    }

    fun remove_item_from_wishlist_layout(adapter: ProductsInWishlistAdapter){
        if(liked_items_with_multiple_parents.size > 0){
            adapter.differ.submitList(null)
            adapter.differ.submitList(Constants.itemsInWishlist)
        } else {
            amount_of_products_wishlist_layout.visibility = View.GONE
            empty_wishlist_layout.visibility = View.VISIBLE
        }
    }

    public fun updatePlannedList(plannedList: PlannedListsModel){
        mPlannedListViewModel.update(plannedList)
    }


    companion object{
        lateinit var contextActivity: Context
        var selectedPlannedLists: MutableList<PlannedListsModel> = mutableListOf()
        var itemForPlannedList: Product? = null
        var selectedPlannedListItems: MutableList<Product> = mutableListOf()

        var search_products: MutableList<Product> = mutableListOf()
        var sorted_by_categories = Constants.sort_by_category()
//        var selected_category_name = ""
//        var selected_category: String = ""
//        var itemsWithMultipleParents: MutableMap<String, MutableList<Any>> = Constants.populate()
        var liked_items_with_multiple_parents: MutableList<String> = mutableListOf()
        var items_added_to_cart = mutableMapOf<String, Int>()
        var pieces_text: String? = null
        var items_in_wishlist: TextView? = null
        var items_in_cart: TextView? = null
        var cart_btn: View? = null
        var mainActivityBinding: ActivityMainBinding? = null
        var currentFragment: Int = -1
        var mPlannedListViewModel: PlannedListViewModel? = null



        fun setproductDataInUI(product: Product, image: ImageView, nameView: TextView, priceView: TextView, productLikeBtn: View?){


            if(product.id in MainActivity.liked_items_with_multiple_parents){
                productLikeBtn?.isSelected = true

            }

            Log.i("likedItreter", "${liked_items_with_multiple_parents}")

             Glide
                .with(contextActivity)
                .load(product.image)
//                .apply(RequestOptions().override(100, 100))
                .fitCenter()
                .placeholder(R.drawable.walmart_lgo)
                .dontAnimate()
                .into(image)
//                .onLoadFailed(a)

            nameView.text = product.name


            val dec = DecimalFormat("#,###.00")
            priceView.text = dec.format(product.price).toString() + " TMT"

        }

        fun update_similar_views_like_btn(product: Product){
            WishlistFragment.wishlist_updated = true
            if(product.id in liked_items_with_multiple_parents && product.favourite != true) {
                product.favourite = true
//                GlobalScope.launch(Dispatchers.Main) { //Your Main UI Thread
//
//                    withContext(Dispatchers.IO) {
//                        mProductViewModel.insert(product)
//                    }
//                }
//                mWishlistViewModel.insert(WishlistModel(product.id))
                for (j in product.attachedViews) {
                    if(j is ProductsInDealsAdapter.ViewHolder){
                        if(j.productName.text.toString() == product.name){
                            j.productLike.isSelected = true
                        }
                    }
                    if(j is ProductsByCategoryOrBrandAdapter.ViewHolder){
                        if(j.productName.text.toString() == product.name){
                            j.productLike.isSelected = true
                        }
                    }



                }
                item_num_above_wishlist (liked_items_with_multiple_parents!!)
            } else {
                product.favourite = false
//                mWishlistViewModel.delete(product.id)

//                GlobalScope.launch(Dispatchers.Main) { //Your Main UI Thread
//
//                    withContext(Dispatchers.IO) {
//                        mProductViewModel.delete(product)
//                    }
//                }

                for (j in product.attachedViews) {
                    if(j is ProductsInDealsAdapter.ViewHolder){
                        if(j.productName.text.toString() == product.name){
                            j.productLike.isSelected = false
                        }
                    }
                    if(j is ProductsByCategoryOrBrandAdapter.ViewHolder){
                        if(j.productName.text.toString() == product.name){
                            j.productLike.isSelected = false
                        }
                    }

                }
                item_num_above_wishlist (liked_items_with_multiple_parents!!)
            }
        }



        fun item_num_above_wishlist (liked_items_with_multiple_parents: MutableList<String>){
            Log.i("items_in_wishlist", "$items_in_wishlist")
            if(liked_items_with_multiple_parents.size > 0){
                items_in_wishlist?.visibility = View.VISIBLE
                items_in_wishlist?.text = liked_items_with_multiple_parents.size.toString()
            } else if(liked_items_with_multiple_parents.size == 0 || liked_items_with_multiple_parents.isEmpty()){
                items_in_wishlist?.visibility = View.GONE
                items_in_wishlist?.text = ""
            }
        }

        fun update_similar_views_add_to_cart (product: Product){
            CartFragment.cart_updated = true
            val dec = DecimalFormat("#,###.00")
            if(product.id in items_added_to_cart) {
                for (j in product.attachedViews) {
                    if (j is ProductsInDealsAdapter.ViewHolder) {
//                        if (j.productName.text.toString() == product.id) {
                            j.productAddToCartLayout.visibility = View.GONE
                            j.productAddAndTakeLayout.visibility = View.VISIBLE
                            j.productPieces.text =
                                items_added_to_cart.get(product.id).toString() + " " + pieces_text
                            j.productPiecesPrice.text =
                                (dec.format(items_added_to_cart.get(product.id)!! * product.price)).toString() + " TMT"
//                        }
                    }
                    if (j is ProductsByCategoryOrBrandAdapter.ViewHolder) {
//                        if (j.productName.text.toString() == product.name) {
                            j.productAddToCartLayout.visibility = View.GONE
                            j.productAddAndTakeLayout.visibility = View.VISIBLE
                            j.productPieces.text =
                                items_added_to_cart.get(product.id).toString() + " " + pieces_text
                            j.productPiecesPrice.text =
                                (dec.format(items_added_to_cart.get(product.id)!! * product.price)).toString() + " TMT"
//                        }
                    }


                }
                item_num_above_cart()
            } else {
                for (j in product.attachedViews) {
                    if (j is ProductsInDealsAdapter.ViewHolder) {
//                        if (j.productName.text.toString() == product.name) {
                            j.productAddAndTakeLayout.visibility = View.GONE
                            j.productAddToCartLayout.visibility = View.VISIBLE
//                        }
                    }
                    if (j is ProductsByCategoryOrBrandAdapter.ViewHolder) {
//                        if (j.productName.text.toString() == product.name) {
                            j.productAddAndTakeLayout.visibility = View.GONE
                            j.productAddToCartLayout.visibility = View.VISIBLE
//                        }
                    }

                }
                item_num_above_cart()
            }
        }

        fun update_similar_views_add_and_take(product: Product){
            CartFragment.cart_updated = true
            val dec = DecimalFormat("#,###.00")
            if(product.id in items_added_to_cart && items_added_to_cart.get(product.id)!! >= 1){
                for(j in product.attachedViews){
                    if(j is ProductsInDealsAdapter.ViewHolder) {
                        if (j.productName.text.toString() == product.name) {
                            j.productPieces.text = items_added_to_cart[product.id].toString() + " " + pieces_text
                            j.productPiecesPrice.text =
                                dec.format((items_added_to_cart[product.id]!! * product.price)).toString() + " TMT"
                        }
                    }
                    if(j is ProductsByCategoryOrBrandAdapter.ViewHolder) {
                        if (j.productName.text.toString() == product.name) {
                            j.productPieces.text = items_added_to_cart[product.id].toString() + " " + pieces_text
                            j.productPiecesPrice.text =
                                dec.format((items_added_to_cart[product.id]!! * product.price)).toString() + " TMT"
                        }
                    }


                }
            } else {
                for(j in product.attachedViews){
                    if (j is ProductsInDealsAdapter.ViewHolder) {
                        if (j.productName.text.toString() == product.name) {
                            j.productAddAndTakeLayout.visibility = View.GONE
                            j.productAddToCartLayout.visibility = View.VISIBLE
                        }
                    }
                    if (j is ProductsByCategoryOrBrandAdapter.ViewHolder) {
                        if (j.productName.text.toString() == product.name) {
                            j.productAddAndTakeLayout.visibility = View.GONE
                            j.productAddToCartLayout.visibility = View.VISIBLE
                        }
                    }

                }

            }
            item_num_above_cart()
        }

        fun item_num_above_cart (){
            if(items_added_to_cart.size > 0){
                items_in_cart!!.visibility = View.VISIBLE
                items_in_cart!!.text = items_added_to_cart.size.toString()
//                cart_btn!!.isSelected = true
            } else if(items_added_to_cart.size == 0 || items_added_to_cart.isEmpty()){
                items_in_cart!!.visibility = View.GONE
//                cart_btn!!.isSelected = false
            }

        }

        var selectedPlannedList: String = ""



        var itms: MutableList<Product>? = null

        fun setLocale(languageCode: String, context: Activity) {
            val locale = Locale(languageCode)
            Locale.setDefault(locale)
            val resources = context.resources
            val config: Configuration = resources.configuration
            config.setLocale(locale)
            context.createConfigurationContext(config);
            context.getResources().updateConfiguration(config, context.getResources().getDisplayMetrics());

//            resources.updateConfiguration(config, resources.displayMetrics)
        }

    }


}
