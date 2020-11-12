package com.adnanjalloul.androidtest_11_2020.ui.main.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.adnanjalloul.androidtest_11_2020.R
import com.adnanjalloul.androidtest_11_2020.data.model.Photo
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_albumimage.view.*
import kotlinx.android.synthetic.main.item_user.view.*
import kotlinx.android.synthetic.main.item_user.view.linearLayout_userInfo

class AlbumPhotoAdapter(
    private val photos: ArrayList<Photo>
): RecyclerView.Adapter<AlbumPhotoAdapter.UserViewHolder>() {

    private var listener: IAlbumPhotoItemSelectedListener? = null

    fun setOnAlbumPhotoItemSelectedListener(listenerI: IAlbumPhotoItemSelectedListener) {
        this.listener = listenerI

    }

    class UserViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(photo: Photo) {
            itemView.textView_imageText.text = photo.title
            Picasso.Builder(itemView.imageView_photo.context).build().load(photo.thumbnailUrl).into(itemView.imageView_photo)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        UserViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_albumimage, parent,
                false
            )
        )

    override fun getItemCount(): Int = photos.size

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        holder.bind(photos[position])
        holder.itemView.linearLayout_userInfo.setOnClickListener {
            listener?.onItemClicked(position, photos[position])
        }
    }

    fun addData(list: List<Photo>) {
        photos.addAll(list)
    }

    interface IAlbumPhotoItemSelectedListener {
        fun onItemClicked(position: Int, photo: Photo)
    }
}