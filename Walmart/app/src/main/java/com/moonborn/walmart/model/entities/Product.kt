package com.moonborn.walmart.model.entities

import android.os.Parcelable
import android.view.View
import androidx.databinding.adapters.Converters
import androidx.room.*
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.moonborn.walmart.Constants
import kotlinx.android.parcel.Parcelize
import kotlinx.android.parcel.RawValue
import java.lang.reflect.Field

@Parcelize
@Entity(tableName = "products")
data class Product(
    @ColumnInfo var id: String = " ",
    @ColumnInfo var name: String = " ",
    @ColumnInfo var brand:String = " ",
    @ColumnInfo var category: String = " ",
    @ColumnInfo var image: String = " ",
    @ColumnInfo(name = "image_source") var imageSource: String = " ",
    @ColumnInfo var price: Double = 0.00,
    @ColumnInfo(name = "new_sale") var newSale: Int = 0,
    @ColumnInfo(name = "promo_sale") var promoSale: Int = 0,
    @ColumnInfo var popular: Int = 0,
    @ColumnInfo(name = "on_sale") var onSale: Int = 0,
    @ColumnInfo var favourite: Boolean = false,
    @Ignore
    var attachedViews: @RawValue ArrayList<Any> = arrayListOf(),
    @PrimaryKey(autoGenerate = true) var columnId: Int = 0
): Parcelable

class AttachedViewsTypeConverter{
    @TypeConverter
    fun fromString(value: String?): ArrayList<String>{

        val listType = object :TypeToken<ArrayList<View>>(){}.type
        return Gson().fromJson(value, listType)
    }

    @TypeConverter
    fun fromArrayList(list: ArrayList<View?>): String{
        return Gson().toJson(list)
    }
}























//package com.moonborn.walmart.Models
//
//import android.os.Parcel
//import android.os.Parcelable
//
//data class Product(
//    private var id: String = "",
//    private var name: String = "",
//    private var brand:String = "",
//    private var category: String = "",
//    private var image: String = "",
//    private var price: Double = 0.00,
//    private var new: Int = 0,
//    private var promoSale: Int = 0,
//    private var popular: Int = 0,
//    private var onSale: Int = 0
//): Parcelable {
//
//    constructor(parcel: Parcel) : this(
//        parcel.readString()!!,
//        parcel.readString()!!,
//        parcel.readString()!!,
//        parcel.readString()!!,
//        parcel.readString()!!,
//        parcel.readDouble(),
//        parcel.readInt(),
//        parcel.readInt(),
//        parcel.readInt(),
//        parcel.readInt()
//    )
//
//
//    override fun describeContents(): Int {
//        return 0
//    }
//
//    override fun writeToParcel(parcel: Parcel, flags: Int) = with(parcel) {
//        parcel.writeString(id)
//        parcel.writeString(name)
//        parcel.writeString(brand)
//        parcel.writeString(category)
//        parcel.writeString(image)
//        parcel.writeDouble(price)
//        parcel.writeInt(new)
//        parcel.writeInt(promoSale)
//        parcel.writeInt(popular)
//        parcel.writeInt(onSale)
//    }
//
//    companion object CREATOR : Parcelable.Creator<Product> {
//        override fun createFromParcel(parcel: Parcel): Product {
//            return Product(parcel)
//        }
//
//        override fun newArray(size: Int): Array<Product?> {
//            return arrayOfNulls(size)
//        }
//    }
//
//    fun getId(): String {
//        return id
//    }
//
//
//    fun getName(): String {
//        return name
//    }
//
//    fun getBrand(): String {
//        return brand
//    }
//
//    fun getCategory(): String {
//        return category
//    }
//
//    fun getImage(): String {
//        return image
//    }
//
//
//    fun getPrice(): Double{
//        return price
//    }
//
//
//
//    fun getNew(): Int{
//        return new
//    }
//
//    fun getPromoSale(): Int{
//        return promoSale
//    }
//
//    fun getPopular(): Int{
//        return popular
//    }
//
//
//    fun getOnSale(): Int{
//        return onSale
//    }
//
//
//}