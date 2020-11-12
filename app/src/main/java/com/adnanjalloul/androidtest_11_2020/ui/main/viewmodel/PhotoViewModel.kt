package com.adnanjalloul.androidtest_11_2020.ui.main.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.adnanjalloul.androidtest_11_2020.data.model.Photo
import com.adnanjalloul.androidtest_11_2020.data.model.User
import com.adnanjalloul.androidtest_11_2020.utils.Resource
import io.reactivex.disposables.CompositeDisposable

class PhotoViewModel () : ViewModel(){
    private val photos = MutableLiveData<Resource<List<Photo>>>()
    var photo: MutableLiveData<Photo> = MutableLiveData()

    init {
    }

    fun fetchAlbum(){
        photos.postValue(Resource.loading(null))
    }

    fun setPhotoValue(photo: Photo){
        this.photo.value = photo
    }
}