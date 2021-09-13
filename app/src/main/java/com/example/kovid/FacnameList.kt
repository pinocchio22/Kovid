package com.example.kovid
import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

/**
 * @author CHOI
 * @email vviian.2@gmail.com
 * @created 2021-08-12
 * @desc
 */
@Parcelize
data class FacnameList(var name : String , var num : String, var add : String, var lat : String , var lng : String) : Parcelable