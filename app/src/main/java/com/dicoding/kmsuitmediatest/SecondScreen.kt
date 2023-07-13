package com.dicoding.kmsuitmediatest

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.dicoding.kmsuitmediatest.databinding.ActivitySecondScreenBinding

class SecondScreen : AppCompatActivity() {

    private lateinit var binding: ActivitySecondScreenBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySecondScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()

        binding.buttonBack.setOnClickListener {
            val intent = Intent(this, FirstScreen::class.java)
            startActivity(intent)
            finish()
        }

        val name = retrieveNameFromSharedPreferences()
        binding.tvName.text = name

        selectedUser()

        displayUserName()

    }

    private fun retrieveNameFromSharedPreferences(): String {
        val sharedPreferences = getSharedPreferences("MyPrefs", MODE_PRIVATE)
        return sharedPreferences.getString("name", "") ?: ""
    }

    private fun displayUserName() {
        val selectedUserName = intent.getStringExtra("full_name")
        if (selectedUserName != null) {
            binding.tvSelectedUser.text = selectedUserName
        }
    }

    private fun selectedUser() {
        binding.buttonSelectedUser.setOnClickListener {
            val intent = Intent(this, ThirdScreen::class.java)
            startActivity(intent)
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        @Suppress("DEPRECATION")
        onBackPressed()
        return true
    }

}