package com.example.albumassignment.api

import com.example.albumassignment.api.Result.Success
import com.example.albumassignment.api.model.Album
import com.example.albumassignment.api.model.Photo
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

class Repo {
    private var serviceClient = ServiceBuilder.buildService(AlbumEndPoints::class.java)

    suspend fun fetchAlbums(): Result = suspendCoroutine { result ->
        val call = serviceClient.getAlbums()
        call.enqueue(object : Callback<List<Album>> {

            override fun onResponse(call: Call<List<Album>>, response: Response<List<Album>>) {
                if (response.isSuccessful) {
                    response.body()?.let {
                        result.resume(Success(it))
                    }

                }
            }

            override fun onFailure(call: Call<List<Album>>, t: Throwable) {
                result.resume(Result.Error(t.localizedMessage ?: ""))
            }
        })

    }

    suspend fun fetchPhotos(albumId: Int): Result = suspendCoroutine { result ->
        val call = serviceClient.getPhotos(albumId.toString())
        call.enqueue(object : Callback<List<Photo>> {
            override fun onResponse(call: Call<List<Photo>>, response: Response<List<Photo>>) {
                if (response.isSuccessful) {
                    response.body()?.let {
                        result.resume(Success(it))
                    }

                }
            }

            override fun onFailure(call: Call<List<Photo>>, t: Throwable) {
                result.resume(Result.Error(t.localizedMessage ?: ""))
            }

        })
    }

}