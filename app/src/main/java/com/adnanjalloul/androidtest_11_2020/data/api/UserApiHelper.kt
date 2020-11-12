package com.adnanjalloul.androidtest_11_2020.data.api

import UserApiService

class UserApiHelper (private val userApiService: UserApiService){

    fun getUsers() = userApiService.getUsers()

    fun getPhotoAlbum(userId: Int) = userApiService.getPhotoAlbum(userId)
}