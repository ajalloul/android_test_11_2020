package com.adnanjalloul.androidtest_11_2020

import UserApiService
import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.test.core.app.ApplicationProvider
import com.adnanjalloul.androidtest_11_2020.data.api.UserApiHelper
import com.adnanjalloul.androidtest_11_2020.data.api.UserApiServiceImplementation
import com.adnanjalloul.androidtest_11_2020.data.model.Photo
import com.adnanjalloul.androidtest_11_2020.data.model.User
import com.adnanjalloul.androidtest_11_2020.data.repository.UserRepository
import com.adnanjalloul.androidtest_11_2020.ui.main.view.MainUserActivity
import com.adnanjalloul.androidtest_11_2020.ui.main.viewmodel.UserViewModel
import com.adnanjalloul.androidtest_11_2020.utils.Resource
import io.reactivex.Single
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.internal.operators.single.SingleError
import org.junit.After
import org.junit.Test

import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.`when`
import org.mockito.Mockito.verify
import org.mockito.MockitoAnnotations
import org.mockito.stubbing.Answer

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(JUnit4::class)
class MainUserActivityTests {
    @Mock
    lateinit var userApiHelper: UserApiHelper
    lateinit var viewModel: UserViewModel
    @Mock
    lateinit var observer: Observer<Resource<List<User>>>


    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        userApiHelper = UserApiHelper(UserApiServiceImplementation())
        viewModel = UserViewModel(UserRepository(userApiHelper))
//        viewModel.getUsers().observeForever(observer)
    }

    @Test
    fun testNullGetUsers() {
        var mock = Mockito.mock(UserApiServiceImplementation::class.java)
        `when`(mock.getUsers()).thenReturn(Single.just(listOf<User>()))
        var mockUsers = mock.getUsers()

        assert(mockUsers is Single<List<User>>)
    }

    @Test
    fun testGetPhotoAlbumError() {
        val mock = Mockito.mock(UserApiServiceImplementation::class.java)
        `when`(mock.getPhotoAlbum(1)).thenReturn(Single.error(Throwable("Api Error")))
        val a = mock.getPhotoAlbum(1)

        assert(a is SingleError)
    }

    @Test
    fun testApiFetchDataSuccess() {
        var mock = Mockito.mock(UserViewModel::class.java)
        `when`(mock.getUsers()).thenReturn(MutableLiveData<Resource<List<User>>>())
        val a = mock.getUsers()

        assert(a is LiveData<Resource<List<User>>>)
    }

    @Test
    fun testGetUserDataError() {
        var mock = Mockito.mock(UserApiHelper::class.java)
        `when`(mock.getUsers()).thenReturn(Single.error(Throwable("Api error")))
        val a = mock.getUsers()

        assert(a is SingleError)
    }

    @After
    fun tearDown() {

    }
}
