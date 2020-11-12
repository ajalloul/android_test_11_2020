package com.adnanjalloul.androidtest_11_2020.ui.main.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.adnanjalloul.androidtest_11_2020.R
import com.adnanjalloul.androidtest_11_2020.data.api.UserApiHelper
import com.adnanjalloul.androidtest_11_2020.data.api.UserApiServiceImplementation
import com.adnanjalloul.androidtest_11_2020.data.model.User
import com.adnanjalloul.androidtest_11_2020.ui.base.ViewModelFactory
import com.adnanjalloul.androidtest_11_2020.ui.main.adapter.UserAdapter
import com.adnanjalloul.androidtest_11_2020.ui.main.viewmodel.UserViewModel
import kotlinx.android.synthetic.main.activity_main_user.*

class MainUserActivity : AppCompatActivity() {
    private lateinit var userViewModel: UserViewModel
    private lateinit var adapter: UserAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_user)
        setupUI()
        setupToolbar()
        setupViewModel()
        setupObserver()
    }

    private fun setupUI() {
        recyclerView_users.layoutManager = LinearLayoutManager(this)
        adapter = UserAdapter(arrayListOf())
        adapter.setOnUserItemSelectedListener(object : UserAdapter.IUserItemSelectedListener {
            override fun onItemClicked(position: Int, user: User) {
                val intent = Intent(this@MainUserActivity, ShowAlbumActivity::class.java)
                intent.putExtra("USER", user)
                startActivity(intent)
            }

        })
        recyclerView_users.adapter = adapter
    }

    private fun setupObserver() {
        userViewModel.getUsers().observe(this, Observer {
            when (it.status) {
                Status.SUCCESS -> {
                    progressbar.visibility = View.GONE
                    it.data?.let { users -> renderList(users) }
                    recyclerView_users.visibility = View.VISIBLE
                }
                Status.LOADING -> {
                    progressbar.visibility = View.VISIBLE
                    recyclerView_users.visibility = View.GONE
                }
                Status.ERROR -> {
                    //Handle Error
                    progressbar.visibility = View.GONE
                    Toast.makeText(this, it.message, Toast.LENGTH_LONG).show()
                }
            }
        })
    }

    private fun renderList(users: List<User>) {
        adapter.addData(users)
        adapter.notifyDataSetChanged()
    }

    private fun setupViewModel() {
        userViewModel = ViewModelProviders.of(
            this,
            ViewModelFactory(UserApiHelper(UserApiServiceImplementation()))
        ).get(UserViewModel::class.java)
    }

    private fun setupToolbar() {
        setSupportActionBar(toolbar!!)
        if (supportActionBar != null) {
            textView_title.text = getString(R.string.user_info)
            supportActionBar!!.setDisplayHomeAsUpEnabled(false)
            supportActionBar!!.setDisplayShowTitleEnabled(false)
        }
    }
}