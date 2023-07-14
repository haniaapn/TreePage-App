package com.dicoding.kmsuitmediatest

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dicoding.kmsuitmediatest.retrofit.ApiConfig
import com.dicoding.kmsuitmediatest.retrofit.UserResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class UserViewModel : ViewModel() {

    fun getUsers(page: Int, perPage: Int): MutableLiveData<Response<UserResponse>?> {
        val apiService = ApiConfig.getApiService().getUsers(page, perPage)
        val responseLiveData = MutableLiveData<Response<UserResponse>?>()
        apiService.enqueue(object : Callback<UserResponse> {
            override fun onResponse(call: Call<UserResponse>, response: Response<UserResponse>) {
                responseLiveData.value = response
            }

            override fun onFailure(call: Call<UserResponse>, t: Throwable) {
                responseLiveData.value = null
            }
        })
        return responseLiveData
    }
}