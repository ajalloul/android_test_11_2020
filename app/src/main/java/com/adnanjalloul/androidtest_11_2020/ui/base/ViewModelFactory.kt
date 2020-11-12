package com.adnanjalloul.androidtest_11_2020.ui.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.adnanjalloul.androidtest_11_2020.data.api.UserApiHelper
import com.adnanjalloul.androidtest_11_2020.data.repository.UserRepository
import com.adnanjalloul.androidtest_11_2020.ui.main.viewmodel.AlbumViewModel
import com.adnanjalloul.androidtest_11_2020.ui.main.viewmodel.PhotoViewModel
import com.adnanjalloul.androidtest_11_2020.ui.main.viewmodel.UserViewModel

class ViewModelFactory(private val apiHelper: UserApiHelper) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(UserViewModel::class.java)) {
            return UserViewModel(UserRepository(apiHelper)) as T
        }
        else if(modelClass.isAssignableFrom(AlbumViewModel::class.java)){
            return AlbumViewModel(UserRepository(apiHelper)) as T
        }
        else if(modelClass.isAssignableFrom(PhotoViewModel::class.java)){
            return PhotoViewModel() as T
        }
        throw IllegalArgumentException("Unknown class name")
    }

}