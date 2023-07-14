package com.dicoding.kmsuitmediatest

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.dicoding.kmsuitmediatest.databinding.ActivityThirdScreenBinding

class ThirdScreen : AppCompatActivity() {

    private lateinit var binding: ActivityThirdScreenBinding
    private lateinit var userViewModel: UserViewModel
    private lateinit var adapter: UserAdapter
    private lateinit var layoutManager: LinearLayoutManager
    private var currentPage = 1
    private val perPage = 10
    private var isLoading = false
    private var isLastPage = false
    private var totalDataCount = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityThirdScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()

        binding.buttonBack.setOnClickListener {
            val intent = Intent(this, SecondScreen::class.java)
            startActivity(intent)
            finish()
        }

        userViewModel = ViewModelProvider(this)[UserViewModel::class.java]

        layoutManager = LinearLayoutManager(this)
        binding.rvUser.layoutManager = layoutManager

        adapter = UserAdapter(mutableListOf())
        binding.rvUser.adapter = adapter

        binding.swipRefreshLayout.setOnRefreshListener {
            currentPage = 1
            isLastPage = false
            adapter.clearData()
            getUsers()
        }

        binding.rvUser.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                val totalItemCount = layoutManager.itemCount
                val lastVisibleItemPosition = layoutManager.findLastVisibleItemPosition()
                if (!isLoading && !isLastPage && lastVisibleItemPosition + 1 == totalItemCount) {
                    currentPage++
                    getUsers()
                }
            }
        })

        getUsers()
    }

    private fun getUsers() {
        isLoading = true
        userViewModel.getUsers(currentPage, perPage).observe(this) { response ->
            if (response != null && response.isSuccessful) {
                val userResponse = response.body()
                userResponse?.let {
                    val data = it.data
                    if (data.isNotEmpty()) {
                        if (currentPage == 1) {
                            adapter.clearData()
                            totalDataCount = it.total
                        }
                        adapter.addData(data)
                    } else {
                        isLastPage = true
                    }
                }
            } else {
                Toast.makeText(this, "Failed to load data. Please try again.", Toast.LENGTH_SHORT).show()
            }

            isLoading = false
            binding.swipRefreshLayout.isRefreshing = false
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        @Suppress("DEPRECATION")
        onBackPressed()
        return true
    }
}

