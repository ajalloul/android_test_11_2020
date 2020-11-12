package com.adnanjalloul.androidtest_11_2020

import UserApiService
import android.content.Context
import androidx.lifecycle.Observer
import androidx.test.core.app.ApplicationProvider
import com.adnanjalloul.androidtest_11_2020.data.api.UserApiHelper
import com.adnanjalloul.androidtest_11_2020.data.api.UserApiServiceImplementation
import com.adnanjalloul.androidtest_11_2020.data.model.User
import com.adnanjalloul.androidtest_11_2020.data.repository.UserRepository
import com.adnanjalloul.androidtest_11_2020.ui.main.view.MainUserActivity
import com.adnanjalloul.androidtest_11_2020.ui.main.viewmodel.UserViewModel
import com.adnanjalloul.androidtest_11_2020.utils.Resource
import io.reactivex.Single
import io.reactivex.disposables.CompositeDisposable
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
        observer = Observer()
        viewModel = UserViewModel(UserRepository(userApiHelper))
        viewModel.getUsers().observeForever(observer)
    }

    @Test
    fun testNullGetUsers() {
        assertNotNull(viewModel.getUsers())
        assertTrue(viewModel.getUsers().hasObservers())
    }

    @Test
    fun testApiFetchDataSuccess() {
        // Mock API response
        `when`(userApiHelper.getUsers()).thenReturn(Single.just(listOfNotNull<User>()))
        viewModel.getUsers()
        verify(observer).onChanged(Resource.loading(null))
        verify(observer).onChanged(Resource.success(listOfNotNull<User>()))
    }

    @Test
    fun testApiFetchDataError() {
        `when`(userApiHelper.getUsers()).thenReturn(Single.error(Throwable("Api error")));
        viewModel.getUsers()
        verify(observer).onChanged(Resource.loading(null))
        verify(observer).onChanged(Resource.error("Api Error", null))
    }

    @After
    fun tearDown() {

    }
}
