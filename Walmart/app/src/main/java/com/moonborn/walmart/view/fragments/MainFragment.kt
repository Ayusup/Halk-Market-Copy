package com.moonborn.walmart.view.fragments



import android.os.Bundle
import android.os.Handler
import android.os.Parcelable
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.constraintlayout.motion.widget.MotionLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager.widget.PagerAdapter
import androidx.viewpager2.widget.ViewPager2
import com.moonborn.walmart.*
import com.moonborn.walmart.Constants.newProducts
import com.moonborn.walmart.Constants.popularProducts
import com.moonborn.walmart.Constants.promoProducts
import com.moonborn.walmart.Constants.saleProducts
import com.moonborn.walmart.view.adapters.*
import com.moonborn.walmart.application.WalmartApplication
import com.moonborn.walmart.databinding.FragmentMainBinding
import com.moonborn.walmart.model.entities.Product
import com.moonborn.walmart.view.activities.BaseActivity
import com.moonborn.walmart.view.activities.MainActivity
import com.moonborn.walmart.viewmodel.*
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_main.view.*
import kotlinx.android.synthetic.main.cardview_layout.*
import kotlinx.android.synthetic.main.cardview_layout.view.*
import kotlinx.android.synthetic.main.carousel_motion_layout.view.*
import kotlinx.android.synthetic.main.fragment_main.*
import kotlinx.android.synthetic.main.fragment_main.view.*
import kotlinx.android.synthetic.main.fragment_search.view.*
import kotlinx.android.synthetic.main.item_loading_dialog.*
import kotlinx.android.synthetic.main.item_loading_dialog.view.*
import kotlinx.android.synthetic.main.products_in_deals_recyclerview.view.*
import kotlinx.android.synthetic.main.search_layout.*
import kotlinx.android.synthetic.main.search_layout.view.*
import kotlinx.android.synthetic.main.search_layout_content.*
import kotlinx.android.synthetic.main.search_layout_content.view.*
import kotlinx.android.synthetic.main.see_all.*
import kotlinx.android.synthetic.main.see_all.view.*
import kotlinx.android.synthetic.main.see_all_layout_content.view.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.File


/**
 * A simple [Fragment] subclass.
 * Use the [MainFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class MainFragment : Fragment() {

    private lateinit var mBinding: FragmentMainBinding
    private lateinit var rootView : ViewGroup
    private lateinit var mRandomDishViewModel: RandomDishViewModel

    private val mProductViewModel : ProductViewModel by viewModels {
        ProductViewModelFactory((requireActivity().application as WalmartApplication).productsRepository)
    }



    companion object {
        //Tag for opening productFragment from Main Fragment
        var mainFragTag: String = "fromMain"
    }

    private var viewPager2: ViewPager2? = null
    private val sliderHandler = Handler()
    private val viewPagerItemBeforedestruction = -1
//    private var rootView: View? = null


    var items_for_recyclerview: MutableMap<String, ArrayList<Product>>? = null

    private var pager2Callback = object:ViewPager2.OnPageChangeCallback(){
        override fun onPageSelected(position: Int) {
            sliderHandler.removeCallbacks(sliderRunnable)


            sliderHandler.postDelayed(sliderRunnable, 6000)


        }


    }

    private val sliderRunnable = Runnable{
        if(viewPager2!!.currentItem == PageLists.somSlides.size - 1){
            viewPager2!!.setCurrentItem(0, false)

            rootView!!.dotsIndicator.setViewPager2(viewPager2!!)

//            viewPager2!!.adapter = viewPager2?.let { SOMAdapter(requireContext(), PageLists.somSlides, it) }


        } else {

            viewPager2!!.setCurrentItem(viewPager2!!.currentItem + 1, true)

        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)




        Log.i("Which Fragment", "${MainActivity.currentFragment}")

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment


        //Initializing adapters
        mBinding = FragmentMainBinding.inflate(inflater, container, false)

        rootView = mBinding.root



        this.exitTransition = rootView.resources.getAnimation(R.anim.layout_move_left_exit_animation)



        return mBinding.root


    }

    override fun onViewCreated(root_view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(root_view, savedInstanceState)




//        BaseActivity.webview(rootView.main_fragment_progressbar_layout)


        mRandomDishViewModel = ViewModelProvider(this).get(RandomDishViewModel::class.java)

        mRandomDishViewModel.getTenItems()


        randomDishViewModelObserver()

        items_for_recyclerview = Constants.sort_by_deals()



        val newProductsAdapter = ProductsInDealsAdapter(this,  R.id.fragment_main_container)
        val promoProductsAdapter = ProductsInDealsAdapter(this,  R.id.fragment_main_container)
        val popularProductsAdapter = ProductsInDealsAdapter(this,  R.id.fragment_main_container)
        val salesProductsAdapter = ProductsInDealsAdapter(this,  R.id.fragment_main_container)

        val NewProductsContent = LayoutInflater.from(requireContext()).inflate(R.layout.products_in_deals_recyclerview, null)
        val PromotionsAndSalesContent = LayoutInflater.from(requireContext()).inflate(R.layout.products_in_deals_recyclerview, null)
        val PopularProductsContent = LayoutInflater.from(requireContext()).inflate(R.layout.products_in_deals_recyclerview, null)
        val SalesContent = LayoutInflater.from(requireContext()).inflate(R.layout.products_in_deals_recyclerview, null)


        mProductViewModel.getProductByDeal(Constants.NEWPRODUCTSDEAL).observe(viewLifecycleOwner){
            list -> list?.let {
                newProductsAdapter.differ.submitList(list)
            }
        }

        mProductViewModel.getProductByDeal(Constants.PROMOSALEDEAL).observe(viewLifecycleOwner){
                list -> list?.let {
                promoProductsAdapter.differ.submitList(list)
            }
        }

        mProductViewModel.getProductByDeal(Constants.POPULARDEAL).observe(viewLifecycleOwner){
                list -> list?.let {
                popularProductsAdapter.differ.submitList(list)
        }
        }

        mProductViewModel.getProductByDeal(Constants.SALESDEAL).observe(viewLifecycleOwner){
                list -> list?.let {
                salesProductsAdapter.differ.submitList(list)
                root_view.main_fragment_scrollview.visibility = View.VISIBLE
            }
        }





//        var rootView = inflater.inflate(R.layout.fragment_main, container, false)

        MainActivity.currentFragment = 0



//        for(i in Constants.itemsInWishlist){
//            MainActivity.update_similar_views_like_btn(i)
//        }

        MainActivity.item_num_above_wishlist(MainActivity.liked_items_with_multiple_parents)


        Log.i("ItemsInCart", "${MainActivity.items_added_to_cart}")
        Log.i("ItemsInWishlist", "${MainActivity.liked_items_with_multiple_parents}")


//        var searchProductsInDeals: MutableList<Product> = mutableListOf()


        viewPager2 = root_view.findViewById(R.id.mviewPager)


        root_view.let { setupViewPager(it) }

        // What content is added to see all layout
        var w_content: View? = null
        var w_recyclerview: RecyclerView? = null
        var w_deal: LinearLayout? = null

//        Constants.getCategories()

//        FirestoreClass().loadCategories()
//        Log.i("document", "${Constants.categoriesNames}")

        root_view.new_products.addView(NewProductsContent)
        root_view.promotions_and_sales.addView(PromotionsAndSalesContent)
        root_view.popular_products.addView(PopularProductsContent)
        root_view.sales.addView(SalesContent)

        NewProductsContent.products_in_deals_recyclerview.adapter = newProductsAdapter
        PromotionsAndSalesContent.products_in_deals_recyclerview.adapter = promoProductsAdapter
        PopularProductsContent.products_in_deals_recyclerview.adapter = popularProductsAdapter
        SalesContent.products_in_deals_recyclerview.adapter = salesProductsAdapter





        val searchFrag = SearchFragment()

        root_view.search_btn?.setOnClickListener {
            requireActivity().supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_main_container, searchFrag, "findThisFragment")
                .addToBackStack(null)
                .commit()

        }

        root_view.top_brands_btn?.setOnClickListener {
            requireActivity().supportFragmentManager.beginTransaction()
                .setCustomAnimations(R.anim.layout_move_right_enter_animation, R.anim.layout_move_left_exit_animation)
                .replace(R.id.fragment_main_container, BrandsFragment(), "findThisFragment")
                .addToBackStack(null)
                .commit()
        }





        fun startSeeAllFragment(dealName: String, top_bar_name: Int, productsInDealList: ArrayList<Parcelable>){

            val seeAllFragment = SeeAllFragment()

            var bundle = Bundle()
            bundle.putInt(SeeAllFragment.TOPBARNAME, top_bar_name)
            bundle.putParcelableArrayList(SeeAllFragment.PRODUCTSINSELECTEDDEAL, productsInDealList)
            bundle.putString(SeeAllFragment.DEALNAME, dealName)

            requireActivity().supportFragmentManager.beginTransaction()
                .setCustomAnimations(R.anim.layout_move_right_enter_animation, R.anim.layout_move_left_exit_animation)
                .replace(R.id.fragment_main_container, seeAllFragment, "findThisFragment")
                .addToBackStack(null)
                .commit()

            seeAllFragment.arguments = bundle



        }


        root_view.new_products_see_all_btn.setOnClickListener{

            startSeeAllFragment(Constants.NEWPRODUCTSDEAL, R.string.new_products, newProducts as ArrayList<Parcelable> /* = java.util.ArrayList<android.os.Parcelable> */)


        }
        root_view.promo_sale_see_all_btn.setOnClickListener{

            startSeeAllFragment(Constants.PROMOSALEDEAL, R.string.promotions_and_sales, promoProducts as ArrayList<Parcelable> /* = java.util.ArrayList<android.os.Parcelable> */)


        }
        root_view.popular_see_all_btn.setOnClickListener {

            startSeeAllFragment(Constants.POPULARDEAL, R.string.popular_products, popularProducts as ArrayList<Parcelable> /* = java.util.ArrayList<android.os.Parcelable> */)


        }
        root_view.on_sale_see_all_btn.setOnClickListener{

            startSeeAllFragment(Constants.SALESDEAL, R.string.sales, saleProducts as ArrayList<Parcelable> /* = java.util.ArrayList<android.os.Parcelable> */)


        }




        root_view.profile_btn.setOnClickListener {
            Log.i("MotionLayout", "${(requireActivity() as MainActivity).findViewById<MotionLayout>(R.id.activity_main_layout)}")
            val motionLayout: MotionLayout = (requireActivity() as MainActivity).findViewById(R.id.activity_main_layout)
            motionLayout.transitionToEnd()
        }

        root_view.top_categories_btn.setOnClickListener {
            requireActivity().supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_main_container, CategoriesAtTopFragment(), "findThisFragment")
                .addToBackStack(null)
                .commit()
        }




    }


    private fun randomDishViewModelObserver(){
        mRandomDishViewModel.productFromApiResponse.observe(viewLifecycleOwner,
            { randomDishOnlineResponse -> randomDishOnlineResponse?.let{
                Log.i("ProductFromAPI", "${randomDishOnlineResponse.recipes[0]}")
                    mProductViewModel.insert(
                        mRandomDishViewModel.convertToProductObject(
                            randomDishOnlineResponse.recipes[0]
                        )
                    )

                }
            }
            )
        mRandomDishViewModel.productFromApiLoadingError.observe(viewLifecycleOwner,
            {
                dataError -> dataError?.let{
                Log.e("ProductFromAPIERROR", "$dataError")
                }
            }
            )

        mRandomDishViewModel.loadProduct.observe(viewLifecycleOwner,
            {
                loadProduct ->
                loadProduct?.let{
                    Log.i("ProductFromAPILoading", "$loadProduct")
                }
            })

    }

    private fun setupViewPager(view: View){
        (viewPager2!!.getChildAt(0) as RecyclerView).clearOnChildAttachStateChangeListeners()
        val  adapter = viewPager2?.let { SOMAdapter(requireContext(), PageLists.somSlides, it) }
        viewPager2 = view.mviewPager
        viewPager2?.adapter = adapter
        viewPager2?.registerOnPageChangeCallback(pager2Callback)
        view.dotsIndicator.setViewPager2(viewPager2!!)
        viewPager2!!.offscreenPageLimit = 2
    }



    override fun onDestroy() {
        super.onDestroy()
        viewPager2?.unregisterOnPageChangeCallback(pager2Callback)
        File(requireContext().cacheDir.path).deleteRecursively()

    }


}