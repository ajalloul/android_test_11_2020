package com.adnanjalloul.androidtest_11_2020.ui.main.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.adnanjalloul.androidtest_11_2020.data.model.Photo
import com.adnanjalloul.androidtest_11_2020.data.repository.UserRepository
import com.adnanjalloul.androidtest_11_2020.utils.Resource
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class PhotoViewModel (private val userRepository: UserRepository) : ViewModel(){
    private val photos = MutableLiveData<Resource<List<Photo>>>()
    private val compositeDisposable = CompositeDisposable()

    init {
//        fetchAlbum()
    }

    fun fetchAlbum(userId: Int){
        photos.postValue(Resource.loading(null))
        compositeDisposable.add(
            userRepository.getPhotoAlbum(userId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ albumPhotos ->
                    photos.postValue(Resource.success(albumPhotos))
                }, { throwable ->
                    photos.postValue(Resource.error("Something went wrong!", null))
                })
        )
    }

    fun getPhotos() : LiveData<Resource<List<Photo>>> {
        return photos
    }
}