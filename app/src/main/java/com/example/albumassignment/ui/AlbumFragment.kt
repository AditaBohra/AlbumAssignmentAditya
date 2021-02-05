package com.example.albumassignment.ui

import android.os.Bundle
import android.view.*
import android.widget.SearchView
import androidx.fragment.app.Fragment
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.albumassignment.R
import com.example.albumassignment.viewModel.ActivityViewModel

class AlbumFragment : Fragment() {

    companion object {
        val TAG = AlbumFragment::class.java.simpleName
    }

    private lateinit var recyclerView: RecyclerView
    private var albumAdapter: AlbumAdapter? = null

    private val viewModel: ActivityViewModel by activityViewModels()


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view: View =
            inflater.inflate(R.layout.fragment_album, container, false)

        recyclerView = view.findViewById(R.id.album_recycler_view)
        recyclerView.apply {
            layoutManager = LinearLayoutManager(requireContext())
            albumAdapter = AlbumAdapter(viewModel)
            adapter = albumAdapter
        }

        observeAlbumList()
        observeError()

        setHasOptionsMenu(true)

        return view
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu, menu)

        val menuItem: MenuItem = menu.findItem(R.id.search_menu)

        val searchView: SearchView = menuItem.actionView as SearchView
        searchView.maxWidth = Int.MAX_VALUE

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(p0: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                albumAdapter?.filter?.filter(newText)
                return true
            }

        })
    }


    private fun observeAlbumList() {
        viewModel.albums.observe(viewLifecycleOwner, Observer {
            if (!it.isNullOrEmpty()) {
                albumAdapter?.updateAlbumData(it)
            }
        })
    }

    private fun observeError() {
        viewModel.error.observe(viewLifecycleOwner, Observer {
            if (!it.isNullOrEmpty()) {
                Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
            }
        })
    }

}