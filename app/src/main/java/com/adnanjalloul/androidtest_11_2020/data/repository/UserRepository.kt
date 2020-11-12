package com.adnanjalloul.androidtest_11_2020.data.repository

import com.adnanjalloul.androidtest_11_2020.data.api.UserApiHelper
import com.adnanjalloul.androidtest_11_2020.data.model.Photo
import com.adnanjalloul.androidtest_11_2020.data.model.User
import io.reactivex.Single

class UserRepository (private val apiHelper: UserApiHelper) {

    fun getUsers(): Single<List<User>> {
        return apiHelper.getUsers()
    }

    fun getPhotoAlbum(userId: Int): Single<List<Photo>> {
        return apiHelper.getPhotoAlbum(userId)
    }

}