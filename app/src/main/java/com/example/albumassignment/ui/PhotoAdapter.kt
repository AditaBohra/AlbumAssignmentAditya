package com.example.albumassignment.ui

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import com.example.albumassignment.R
import com.example.albumassignment.api.model.Photo
import com.squareup.picasso.Picasso

class PhotoAdapter(private val context: Context) : BaseAdapter() {

    private var photosList = mutableListOf<Photo>()


    override fun getCount(): Int {
        return photosList.size
    }

    override fun getItem(position: Int): Any {
        return photosList[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getView(position: Int, view: View?, viewGroup: ViewGroup?): View {

        var v = view
        if (v == null) {
            v = LayoutInflater.from(context).inflate(R.layout.photo_row_layout, viewGroup, false)
            v.setTag(R.id.photo_image_view, v.findViewById(R.id.photo_image_view))
        }
        val imageView: ImageView = v?.getTag(R.id.photo_image_view) as ImageView

        loadImages(photosList[position].thumbnailUrl, imageView)
        return v
    }

    fun updatePhotoAdapter(photos: List<Photo>) {
        photosList.clear()
        photosList = photos as MutableList<Photo>
        notifyDataSetChanged()
    }

    private fun loadImages(url: String, imageView: ImageView) {
        Picasso.get().load(url).into(imageView)
    }
}