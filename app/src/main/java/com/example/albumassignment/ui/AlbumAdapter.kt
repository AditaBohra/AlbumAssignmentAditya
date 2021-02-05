package com.example.albumassignment.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.albumassignment.R
import com.example.albumassignment.api.model.Album
import java.util.*

class AlbumAdapter(private val listener: OnItemClick) :
    RecyclerView.Adapter<AlbumAdapter.AlbumViewHolder>(), Filterable {

    private var albumList = mutableListOf<Album>()
    private var filteredAlbumList = mutableListOf<Album>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AlbumViewHolder {

        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.album_row_layout, parent, false)
        return AlbumViewHolder(view)
    }

    override fun onBindViewHolder(holder: AlbumViewHolder, position: Int) {
        holder.albumTitle.text = albumList[position].title
        holder.onBind(albumList[position], listener)
    }

    override fun getItemCount(): Int {
        return albumList.size
    }

    inner class AlbumViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val albumTitle: TextView = view.findViewById(R.id.album_title)
        fun onBind(item: Album, listener: OnItemClick) {
            itemView.setOnClickListener {
                listener.onItemClick(item)
            }
        }

    }

    fun updateAlbumData(albums: List<Album>) {
        albumList.clear()
        albumList = albums as MutableList<Album>
        filteredAlbumList = albumList
        notifyDataSetChanged()
    }

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(charSequence: CharSequence?): FilterResults {
                val filterResults = FilterResults()
                if (charSequence == null || charSequence.isEmpty()) {
                    filterResults.count = filteredAlbumList.size
                    filterResults.values = filteredAlbumList
                } else {
                    val searchChr = charSequence.toString().toLowerCase(Locale.ROOT)
                    val resultAlbum = mutableListOf<Album>()

                    for (album in filteredAlbumList) {
                        if (album.title.toLowerCase(Locale.ROOT).contains(searchChr)) {
                            resultAlbum.add(album)
                        }
                    }
                    filterResults.count = resultAlbum.size
                    filterResults.values = resultAlbum
                }
                return filterResults
            }

            override fun publishResults(
                charSequence: CharSequence?,
                filterResults: FilterResults?
            ) {
                albumList = filterResults?.values as MutableList<Album>
                notifyDataSetChanged()
            }
        }
    }

}

interface OnItemClick {
    fun onItemClick(album: Album)
}


