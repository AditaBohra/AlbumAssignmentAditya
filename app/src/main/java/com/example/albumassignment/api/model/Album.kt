package com.example.albumassignment.api.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Album(
    var userId: Int = 0,
    var id: Int = 0,
    var title: String = ""
): Parcelable
