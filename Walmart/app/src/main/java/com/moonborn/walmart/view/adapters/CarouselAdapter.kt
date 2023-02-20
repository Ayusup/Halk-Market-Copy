package com.moonborn.walmart.view.adapters

import android.view.View
import android.widget.ImageView
import androidx.constraintlayout.helper.widget.Carousel


class CarouselAdapter(val items: MutableList<Int>): Carousel.Adapter {


    override fun count(): Int {
        return items.size
    }

    override fun populate(view: View?, index: Int) {
        val imageView = view as ImageView
        imageView.setImageResource(items[index])
    }

    override fun onNewItem(index: Int) {

    }
}