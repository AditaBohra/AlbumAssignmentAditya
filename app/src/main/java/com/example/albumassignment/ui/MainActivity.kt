package com.example.albumassignment.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.albumassignment.R
import com.example.albumassignment.api.Repo
import com.example.albumassignment.viewModel.ActivityViewModel
import com.example.albumassignment.viewModel.ViewModelFactory


class MainActivity : AppCompatActivity() {

    private lateinit var viewModel: ActivityViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        viewModel = ViewModelProvider(this, ViewModelFactory {
            ActivityViewModel(Repo())
        }).get(ActivityViewModel::class.java)

        viewModel.fetchAlbumsFromApi()

        fragmentTransition(fragment = AlbumFragment(), tag = AlbumFragment.TAG)

        observeNavigateTo()
    }


    private fun observeNavigateTo() {
        viewModel.navigateTo.observe(this, Observer {
            when (it) {
                PhotosFragment::class.java -> {
                    fragmentTransition(PhotosFragment(), PhotosFragment.TAG)
                }
            }
        })
    }

    private fun fragmentTransition(fragment: Fragment, tag: String) {
        val fragmentManager = supportFragmentManager
        fragmentManager.beginTransaction().apply {
            replace(R.id.fragment_container, fragment, tag)
            addToBackStack(null)
            commit()
        }
    }

    private fun getCurrentFragment(): Fragment? {
        return supportFragmentManager.findFragmentById(R.id.fragment_container)
    }

    override fun onBackPressed() {
        if (getCurrentFragment() is AlbumFragment) {
            finish()
        } else {
            super.onBackPressed()
        }

    }


}