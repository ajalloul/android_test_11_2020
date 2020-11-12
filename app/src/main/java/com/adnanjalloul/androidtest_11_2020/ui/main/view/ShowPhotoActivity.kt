package com.adnanjalloul.androidtest_11_2020.ui.main.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProviders
import com.adnanjalloul.androidtest_11_2020.R
import com.adnanjalloul.androidtest_11_2020.data.api.UserApiHelper
import com.adnanjalloul.androidtest_11_2020.data.api.UserApiServiceImplementation
import com.adnanjalloul.androidtest_11_2020.data.model.Photo
import com.adnanjalloul.androidtest_11_2020.databinding.ActivityShowPhotoBinding
import com.adnanjalloul.androidtest_11_2020.ui.base.ViewModelFactory
import com.adnanjalloul.androidtest_11_2020.ui.main.viewmodel.PhotoViewModel
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_show_photo.*
import kotlinx.android.synthetic.main.activity_show_photo.toolbar

class ShowPhotoActivity : AppCompatActivity() {

    private lateinit var photoViewModel: PhotoViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setupViewModel()
        getData()

        val binding : ActivityShowPhotoBinding =
            DataBindingUtil.setContentView(this, R.layout.activity_show_photo)

        binding.viewmodel = photoViewModel
        binding.lifecycleOwner = this

        setupToolbar()
        setupUi(binding)
    }

    private fun getData() {
        var photo = intent.getParcelableExtra("PHOTO") ?: Photo()
        photoViewModel.setPhotoValue(photo)
    }

    private fun setupUi(binding: ActivityShowPhotoBinding) {
        Picasso.Builder(this)
            .build()
            .load(photoViewModel.photo.value!!.photoUrl)
            .into(binding.imageViewPhotoImage)
    }

    private fun setupViewModel() {
        photoViewModel = ViewModelProviders.of(
            this,
            ViewModelFactory(UserApiHelper(UserApiServiceImplementation()))
        ).get(PhotoViewModel::class.java)
    }

    private fun setupToolbar() {
        setSupportActionBar(toolbar!!)
        if (supportActionBar != null) {
            supportActionBar!!.setDisplayHomeAsUpEnabled(false)
            supportActionBar!!.setDisplayShowTitleEnabled(false)
        }
    }
}
