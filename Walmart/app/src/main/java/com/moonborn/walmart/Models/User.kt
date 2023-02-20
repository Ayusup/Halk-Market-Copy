package com.moonborn.walmart.Models

import android.os.Parcel
import android.os.Parcelable
import android.text.Editable

data class User(
    val id: String = "",
    val name:String = "",
    val lastName: String = "",
    val phoneNumber: Long = 0,
    val city: String = "",
    val address: String = "",
    val fcmToken: String = ""
): Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readLong(),
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!
    ) {
    }

    override fun describeContents() = 0

    override fun writeToParcel(dest: Parcel, flags: Int) = with(dest) {
        writeString(id)
        writeString(name)
        writeString(lastName)
        writeLong(phoneNumber)
        writeString(city)
        writeString(address)
        writeString(fcmToken)
    }

    companion object CREATOR : Parcelable.Creator<User> {
        override fun createFromParcel(parcel: Parcel): User {
            return User(parcel)
        }

        override fun newArray(size: Int): Array<User?> {
            return arrayOfNulls(size)
        }
    }
}