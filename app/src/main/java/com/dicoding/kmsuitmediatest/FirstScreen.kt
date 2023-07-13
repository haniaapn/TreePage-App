package com.dicoding.kmsuitmediatest

import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import com.dicoding.kmsuitmediatest.databinding.ActivityFirstScreenBinding
import java.util.*

class FirstScreen : AppCompatActivity() {

    private lateinit var binding: ActivityFirstScreenBinding
    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFirstScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()

        sharedPreferences = getSharedPreferences("MyPrefs", MODE_PRIVATE)


        binding.buttonCheck.setOnClickListener {
            val inputText: String = binding.etPalindrome.text.toString()
            if (inputText.isNotEmpty()){
                val isPalindrome = isPalindrome(inputText)
                val message = if (isPalindrome) "IS PALINDROME" else "NOT PALINDROME"
                showDialog(message)
            }else{
                showDialog("Text not found")
            }

        }

        binding.buttonNext.setOnClickListener {
            val name = binding.etName.text.toString()
            if (name.isNotEmpty()) {
                saveNameToSharedPreferences(name)
                val intent = Intent(this, SecondScreen::class.java)
                startActivity(intent)
            }else {
                showDialog("Name cannot be empty")
            }
        }
    }

    private fun saveNameToSharedPreferences(name: String) {
        val editor: SharedPreferences.Editor = sharedPreferences.edit()
        editor.putString("name", name)
        editor.apply()
    }

    private fun isPalindrome(text: String): Boolean {
        val cleanText = text.replace("\\s+".toRegex(), "").lowercase(Locale.getDefault())
        val reversedText = cleanText.reversed()
        return cleanText == reversedText
    }

    private fun showDialog(message: String) {
        val alertDialogBuilder = AlertDialog.Builder(this)
        alertDialogBuilder.setMessage(message)
            .setPositiveButton("OK") { dialog, _ ->
                dialog.dismiss()
            }
        val alertDialog = alertDialogBuilder.create()
        alertDialog.show()
    }

}
