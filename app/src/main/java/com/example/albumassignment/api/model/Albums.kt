package com.example.albumassignment.api.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Albums(val albums: List<Album> = mutableListOf()): Parcelable
