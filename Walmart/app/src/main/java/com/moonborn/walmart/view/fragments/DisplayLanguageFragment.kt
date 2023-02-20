package com.moonborn.walmart.view.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.viewModels
import androidx.core.app.ActivityCompat.recreate
import androidx.fragment.app.viewModels
import com.moonborn.walmart.Constants
import com.moonborn.walmart.view.activities.MainActivity
import com.moonborn.walmart.R
import com.moonborn.walmart.application.WalmartApplication
import com.moonborn.walmart.viewmodel.ProductViewModel
import com.moonborn.walmart.viewmodel.ProductViewModelFactory
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_display_language.view.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class DisplayLanguageFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }


    var language_selected: View? =null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        val rootView = inflater.inflate(R.layout.fragment_display_language, container, false)

        rootView.display_language_back_btn.setOnClickListener {
            requireActivity().supportFragmentManager.popBackStack()
            (requireActivity() as MainActivity).setupTabBar()

        }

        fun selectLanguage(lang: View){
            lang.visibility = View.VISIBLE
            if (language_selected != null){
                language_selected!!.visibility = View.GONE
            }
            language_selected = lang
        }


        rootView.change_lang_tk.setOnClickListener {
            MainActivity.setLocale(Constants.TURKMEN, requireActivity())
            selectLanguage(rootView.turkmen_check)
            rootView.display_language_topBar_tv.text = resources.getString(R.string.display_lang_tk)

        }

        rootView.change_lang_ru.setOnClickListener {
            MainActivity.setLocale(Constants.RUSSIAN, requireActivity())
            selectLanguage(rootView.russian_check)
            rootView.display_language_topBar_tv.text = resources.getString(R.string.display_lang_ru)
        }

        rootView.change_lang_en.setOnClickListener {
            MainActivity.setLocale(Constants.ENGLISH, requireActivity())
            selectLanguage(rootView.english_check)
            rootView.display_language_topBar_tv.text = resources.getString(R.string.display_lang_en)

        }


        return rootView
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)



    }

}