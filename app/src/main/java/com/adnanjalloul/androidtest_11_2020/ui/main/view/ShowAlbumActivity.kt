package com.adnanjalloul.androidtest_11_2020.ui.main.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.adnanjalloul.androidtest_11_2020.R
import com.adnanjalloul.androidtest_11_2020.data.api.UserApiHelper
import com.adnanjalloul.androidtest_11_2020.data.api.UserApiServiceImplementation
import com.adnanjalloul.androidtest_11_2020.data.model.Photo
import com.adnanjalloul.androidtest_11_2020.data.model.User
import com.adnanjalloul.androidtest_11_2020.ui.base.ViewModelFactory
import com.adnanjalloul.androidtest_11_2020.ui.main.adapter.AlbumPhotoAdapter
import com.adnanjalloul.androidtest_11_2020.ui.main.viewmodel.PhotoViewModel
import kotlinx.android.synthetic.main.activity_main_user.*
import kotlinx.android.synthetic.main.activity_show_album.*
import kotlinx.android.synthetic.main.activity_show_album.progressbar
import kotlinx.android.synthetic.main.activity_show_album.toolbar

class ShowAlbumActivity : AppCompatActivity() {
    private lateinit var photoViewModel: PhotoViewModel
    private lateinit var adapter: AlbumPhotoAdapter
    private lateinit var user: User

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_show_album)
        getData()
        setupUI()
        setupToolbar()
        setupViewModel()
        setupObserver()
    }

    private fun getData(){
        user = intent.getParcelableExtra<User?>("USER")?:User()
    }

    private fun setupUI() {
        recyclerView_photos.layoutManager = LinearLayoutManager(this)
        adapter = AlbumPhotoAdapter(arrayListOf())
        adapter.setOnAlbumPhotoItemSelectedListener(object :
            AlbumPhotoAdapter.IAlbumPhotoItemSelectedListener {
            override fun onItemClicked(position: Int, photo: Photo) {
                val intent = Intent(this@ShowAlbumActivity, ShowPhotoActivity::class.java)
                intent.putExtra("PHOTO", photo)
                startActivity(intent)
            }


        })
        recyclerView_photos.adapter = adapter
    }

    private fun setupObserver() {
        photoViewModel.getPhotos().observe(this, Observer {
            when (it.status) {
                Status.SUCCESS -> {
                    progressbar.visibility = View.GONE
                    it.data?.let { users -> renderList(users) }
                    recyclerView_photos.visibility = View.VISIBLE
                }
                Status.LOADING -> {
                    progressbar.visibility = View.VISIBLE
                    recyclerView_photos.visibility = View.GONE
                }
                Status.ERROR -> {
                    //Handle Error
                    progressbar.visibility = View.GONE
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
        photoViewModel = ViewModelProviders.of(
            this,
            ViewModelFactory(UserApiHelper(UserApiServiceImplementation()))
        ).get(PhotoViewModel::class.java)

        photoViewModel.fetchAlbum(user.id)
    }

    private fun setupToolbar() {
        setSupportActionBar(toolbar!!)
        if (supportActionBar != null) {
            textView_albumId.text = user.id.toString()
            supportActionBar!!.setDisplayHomeAsUpEnabled(false)
            supportActionBar!!.setDisplayShowTitleEnabled(false)
        }
    }
}
