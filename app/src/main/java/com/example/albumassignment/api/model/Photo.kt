package com.example.albumassignment.api.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Photo(
    var albumId: Int = 0,
    var Id: Int = 0,
    var title: String = "",
    var url: String = "",
    var thumbnailUrl: String = ""
) : Parcelable {
}