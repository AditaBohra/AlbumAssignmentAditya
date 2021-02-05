package com.example.albumassignment.api

import com.example.albumassignment.api.model.Album
import com.example.albumassignment.api.model.Photo
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface AlbumEndPoints {

    @GET("/albums")
    fun getAlbums(): Call<List<Album>>

    @GET("/photos")
    fun getPhotos(@Query("albumId") key: String): Call<List<Photo>>

}