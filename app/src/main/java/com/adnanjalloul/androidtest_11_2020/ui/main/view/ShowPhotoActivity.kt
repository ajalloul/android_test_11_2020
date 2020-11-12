package com.adnanjalloul.androidtest_11_2020.ui.main.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.adnanjalloul.androidtest_11_2020.R
import com.adnanjalloul.androidtest_11_2020.data.model.Photo
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_main_user.*
import kotlinx.android.synthetic.main.activity_show_photo.*
import kotlinx.android.synthetic.main.activity_show_photo.toolbar
import kotlinx.android.synthetic.main.item_albumimage.view.*

class ShowPhotoActivity : AppCompatActivity() {

    private lateinit var photo: Photo

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_show_photo)

        getData()
        setupToolbar()
        setupUi()
    }

    private fun getData() {
        photo = intent.getParcelableExtra("PHOTO") ?: Photo()
    }

    private fun setupUi() {
        textView_photoText.text = photo.title
        Picasso.Builder(this).build().load(photo.photoUrl).into(imageView_photoImage)
    }

    private fun setupToolbar() {
        setSupportActionBar(toolbar!!)
        if (supportActionBar != null) {
            textView_albumId.text = photo.albumId.toString()
            textView_photoId.text = photo.photoId.toString()
            supportActionBar!!.setDisplayHomeAsUpEnabled(false)
            supportActionBar!!.setDisplayShowTitleEnabled(false)
        }
    }
}
