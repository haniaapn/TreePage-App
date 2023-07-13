package com.dicoding.kmsuitmediatest

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.kmsuitmediatest.databinding.ActivityThirdScreenBinding
import com.dicoding.kmsuitmediatest.retrofit.DataItem

class ThirdScreen : AppCompatActivity() {

    private lateinit var binding: ActivityThirdScreenBinding
    private lateinit var userViewModel: UserViewModel

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
        userViewModel.userList.observe(this){ item ->
            setList(item)
        }

        recycleViewSetting()

    }

    private fun setList(item: List<DataItem>) {
        val adapter = UserAdapter(item)
        binding.rvUser.adapter = adapter
    }

    private fun recycleViewSetting() {
        val layoutManager = LinearLayoutManager(this)
        binding.rvUser.layoutManager = layoutManager
    }

    override fun onSupportNavigateUp(): Boolean {
        @Suppress("DEPRECATION")
        onBackPressed()
        return true
    }
}
