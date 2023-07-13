package com.dicoding.kmsuitmediatest

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dicoding.kmsuitmediatest.retrofit.ApiConfig
import com.dicoding.kmsuitmediatest.retrofit.DataItem
import com.dicoding.kmsuitmediatest.retrofit.UserResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UserViewModel : ViewModel() {
    private val _userList = MutableLiveData<List<DataItem>>()
    val userList: LiveData<List<DataItem>> = _userList

    init {
        getUsers()
    }

    private fun getUsers() {
        val apiService = ApiConfig.getApiService().getUsers(1, 10)
        apiService.enqueue(object : Callback<UserResponse> {
            override fun onResponse(call: Call<UserResponse>, response: Response<UserResponse>) {
                if (response.isSuccessful) {
                    val userResponse = response.body()
                    userResponse?.let {
                        _userList.value = it.data
                    }
                }
            }

            override fun onFailure(call: Call<UserResponse>, t: Throwable) {
                _userList.value = emptyList()
                Log.e("UserViewModel", "Error: ${t.message}")
            }
        })
    }
}