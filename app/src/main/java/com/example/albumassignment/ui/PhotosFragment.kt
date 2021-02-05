package com.example.albumassignment.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.GridView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.albumassignment.R
import com.example.albumassignment.viewModel.ActivityViewModel

class PhotosFragment : Fragment() {

    companion object {
        val TAG = PhotosFragment::class.java.simpleName
    }

    private val viewModel: ActivityViewModel by activityViewModels()
    private lateinit var  gridView: GridView
    private var photoAdapter: PhotoAdapter? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view: View =
            inflater.inflate(R.layout.frament_photo, container, false)

        gridView = view.findViewById(R.id.grid_view)
        gridView.apply {
            photoAdapter = PhotoAdapter(requireContext())
            adapter = photoAdapter
        }

        observePhotoList()
        observeError()


        return view
    }

    private fun observePhotoList() {
        viewModel.photos.observe(viewLifecycleOwner, Observer {
            if (!it.isNullOrEmpty()) {
                photoAdapter?.updatePhotoAdapter(it)
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