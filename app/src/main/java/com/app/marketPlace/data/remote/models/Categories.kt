package com.app.marketPlace.data.remote.models

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName



data class Categories(

    @SerializedName("name")
    @Expose
    var name: String? = null,

    @SerializedName("id")
    @Expose
    var id: String? = null,

    var image: String? = "https://www.dropbox.com/s/3z9sqdui2zztnws/one.png?dl=0",

    @SerializedName("subCategories")
    @Expose
    var subCategories: List<Categories>? = null,

    var backgroundColorSelected:Int? = null,

    var back:Int? = null

):Parcelable{
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.createTypedArrayList(CREATOR)
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(name)
        parcel.writeString(id)
        parcel.writeString(image)
        parcel.writeTypedList(subCategories)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Categories> {
        override fun createFromParcel(parcel: Parcel): Categories {
            return Categories(parcel)
        }

        override fun newArray(size: Int): Array<Categories?> {
            return arrayOfNulls(size)
        }
    }

}

//data class SubCategory(
//    @SerializedName("id")
//    @Expose
//    var id: String? = null,
//
//    @SerializedName("name")
//    @Expose
//    var name: String? = null,
//
//    var image: String? = "https://www.dropbox.com/s/3z9sqdui2zztnws/one.png?dl=0",
//): Parcelable,Category{
//    constructor(parcel: Parcel) : this(
//        parcel.readString(),
//        parcel.readString()
//    ) {
//    }
//
//    override fun writeToParcel(parcel: Parcel, flags: Int) {
//        parcel.writeString(id)
//        parcel.writeString(name)
//    }
//
//    override fun describeContents(): Int {
//        return 0
//    }
//
//    companion object CREATOR : Parcelable.Creator<SubCategory> {
//        override fun createFromParcel(parcel: Parcel): SubCategory {
//            return SubCategory(parcel)
//        }
//
//        override fun newArray(size: Int): Array<SubCategory?> {
//            return arrayOfNulls(size)
//        }
//    }
//
//}