package com.adnanjalloul.androidtest_11_2020.ui.main.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.adnanjalloul.androidtest_11_2020.R
import com.adnanjalloul.androidtest_11_2020.data.api.UserApiHelper
import com.adnanjalloul.androidtest_11_2020.data.api.UserApiServiceImplementation
import com.adnanjalloul.androidtest_11_2020.data.model.Photo
import com.adnanjalloul.androidtest_11_2020.data.model.User
import com.adnanjalloul.androidtest_11_2020.databinding.ActivityShowAlbumBinding
import com.adnanjalloul.androidtest_11_2020.ui.base.ViewModelFactory
import com.adnanjalloul.androidtest_11_2020.ui.main.adapter.AlbumPhotoAdapter
import com.adnanjalloul.androidtest_11_2020.ui.main.viewmodel.AlbumViewModel
import kotlinx.android.synthetic.main.activity_show_album.progressbar

class ShowAlbumActivity : AppCompatActivity() {
    private lateinit var albumViewModel: AlbumViewModel
    private lateinit var adapter: AlbumPhotoAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupViewModel()
        getData()
        val binding : ActivityShowAlbumBinding =
            DataBindingUtil.setContentView(this, R.layout.activity_show_album)
        binding.viewmodel = albumViewModel
        binding.lifecycleOwner = this
        albumViewModel.fetchAlbum()
        setupUI(binding)
        setupToolbar(binding)
        setupObserver(binding)
    }

    private fun getData(){
        albumViewModel.setUserValue(intent.getParcelableExtra<User?>("USER")?:User())

    }

    private fun setupUI(binding: ActivityShowAlbumBinding) {
        binding.recyclerViewPhotos.layoutManager = LinearLayoutManager(this)
        adapter = AlbumPhotoAdapter(arrayListOf())
        adapter.setOnAlbumPhotoItemSelectedListener(object :
            AlbumPhotoAdapter.IAlbumPhotoItemSelectedListener {
            override fun onItemClicked(position: Int, photo: Photo) {
                val intent = Intent(this@ShowAlbumActivity, ShowPhotoActivity::class.java)
                intent.putExtra("PHOTO", photo)
                startActivity(intent)
            }


        })
        binding.recyclerViewPhotos.adapter = adapter
    }

    private fun setupObserver(binding : ActivityShowAlbumBinding) {
        albumViewModel.getPhotos().observe(this, Observer {
            when (it.status) {
                Status.SUCCESS -> {
                    binding.progressbar.visibility = View.GONE
                    it.data?.let { users -> renderList(users) }
                    binding.recyclerViewPhotos.visibility = View.VISIBLE
                }
                Status.LOADING -> {
                    progressbar.visibility = View.VISIBLE
                    binding.recyclerViewPhotos.visibility = View.GONE
                }
                Status.ERROR -> {
                    //Handle Error
                    binding.progressbar.visibility = View.GONE
                    Toast.makeText(this, it.message, Toast.LENGTH_LONG).show()
                }
            }
        })
    }

    private fun renderList(photos: List<Photo>) {
        adapter.addData(photos)
        adapter.notifyDataSetChanged()
    }

    private fun setupViewModel() {
        albumViewModel = ViewModelProviders.of(
            this,
            ViewModelFactory(UserApiHelper(UserApiServiceImplementation()))
        ).get(AlbumViewModel::class.java)
    }

    private fun setupToolbar(binding : ActivityShowAlbumBinding) {
        setSupportActionBar(binding.toolbar)
        if (supportActionBar != null) {
            binding.textViewAlbumId.text = albumViewModel.user.value!!.id.toString()
            supportActionBar!!.setDisplayHomeAsUpEnabled(false)
            supportActionBar!!.setDisplayShowTitleEnabled(false)
        }
    }
}
