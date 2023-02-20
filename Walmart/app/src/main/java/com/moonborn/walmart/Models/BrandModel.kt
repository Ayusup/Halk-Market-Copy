package com.moonborn.walmart.Models

import android.os.Parcel
import android.os.Parcelable

data class BrandModel(
    private val name: String = "",
    private val image: String = ""
): Parcelable {

    constructor(parcel: Parcel) : this(
        parcel.readString()!!,
        parcel.readString()!!
    )


    override fun describeContents(): Int {
        return 0
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) = with(parcel) {
        parcel.writeString(name)
        parcel.writeString(image)
    }

    companion object CREATOR : Parcelable.Creator<BrandModel> {
        override fun createFromParcel(parcel: Parcel): BrandModel {
            return BrandModel(parcel)
        }

        override fun newArray(size: Int): Array<BrandModel?> {
            return arrayOfNulls(size)
        }
    }

    fun getName(): String {
        return this.name
    }

    fun getImage(): String {
        return this.image
    }

}