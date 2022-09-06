package com.shop_user.model

import android.os.Parcel
import android.os.Parcelable

class SellerModel() : Parcelable
{

    internal lateinit var id: String
    internal lateinit var randomKey: String
    internal lateinit var image: String
    internal lateinit var name: String
    internal lateinit var phone: String
    internal lateinit var address: String

    constructor(parcel: Parcel) : this() {
        id = parcel.readString()!!
        randomKey = parcel.readString()!!
        image = parcel.readString()!!
        name = parcel.readString()!!
        phone = parcel.readString()!!
        address = parcel.readString()!!
    }

    override fun writeToParcel(parcel: Parcel, flags: Int)
    {
        parcel.writeString(id)
        parcel.writeString(randomKey)
        parcel.writeString(image)
        parcel.writeString(name)
        parcel.writeString(phone)
        parcel.writeString(address)
    }

    override fun describeContents(): Int
    {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<SellerModel>
    {
        override fun createFromParcel(parcel: Parcel): SellerModel
        {
            return SellerModel(parcel)
        }

        override fun newArray(size: Int): Array<SellerModel?>
        {
            return arrayOfNulls(size)
        }
    }

}