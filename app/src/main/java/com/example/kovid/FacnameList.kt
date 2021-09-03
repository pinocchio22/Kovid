package com.example.kovid

import android.os.Parcel
import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import java.io.Serializable

/**
 * @author CHOI
 * @email vviian.2@gmail.com
 * @created 2021-08-12
 * @desc
 */
@Parcelize
data class FacnameList(var name : String , var num : String, var add : String, var lat : String , var lng : String) : Parcelable {

}

//    constructor(parcel : Parcel) : this(parcel.writeString(),parcel.writeString(),parcel.writeString()) {
//
//    }
//
//    override fun writeToParcel(parcel: Parcel, flags: Int) {
//        parcel.writeString(name)
//        parcel.writeString(num)
//        parcel.writeString(add)
//    }
//
//    override fun describeContents(): Int {
//        return 0
//    }
//
//    companion object CREATOR : Parcelable.Creator<FacnameList> {
//        override fun createFromParcel(parcel: Parcel): FacnameList {
//            return FacnameList(parcel)
//        }
//
//        override fun newArray(size: Int): Array<FacnameList?> {
//            return arrayOfNulls(size)
//        }
//    }
//
//}