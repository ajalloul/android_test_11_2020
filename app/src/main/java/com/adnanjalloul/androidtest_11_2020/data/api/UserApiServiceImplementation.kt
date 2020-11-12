package com.adnanjalloul.androidtest_11_2020.data.api

import UserApiService
import com.adnanjalloul.androidtest_11_2020.data.model.Photo
import com.adnanjalloul.androidtest_11_2020.data.model.User
import com.rx2androidnetworking.Rx2AndroidNetworking
import io.reactivex.Single

class UserApiServiceImplementation: UserApiService {
    override fun getUsers(): Single<List<User>> {
        return Rx2AndroidNetworking
            .get("https://jsonplaceholder.typicode.com/users")
            .build()
            .getObjectListSingle(User::class.java)
    }

    override fun getPhotoAlbum(userId: Int): Single<List<Photo>> {
        return Rx2AndroidNetworking
            .get("https://jsonplaceholder.typicode.com/photos?albumId=${userId}")
            .build()
            .getObjectListSingle(Photo::class.java)
    }
}
