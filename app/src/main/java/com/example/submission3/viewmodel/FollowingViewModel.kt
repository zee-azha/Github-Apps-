package com.example.submission3.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.submission3.api.ClientInstance
import com.example.submission3.data.Person
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class FollowingViewModel: ViewModel() {
    private val listFollowing = MutableLiveData<ArrayList<Person>>()

    fun setListFollowing(username: String){
        ClientInstance.apiInstances
            .getPersonFollowing(username)
            .enqueue(object : Callback<ArrayList<Person>>{
                override fun onResponse(
                    call: Call<ArrayList<Person>>,
                    response: Response<ArrayList<Person>>
                ) {
                    if(response.isSuccessful){
                        listFollowing.postValue(response.body())
                    }
                }

                override fun onFailure(call: Call<ArrayList<Person>>, t: Throwable) {
                    Log.d("Exception",t.message.toString())
                }
            })
    }

    fun getListFollowing(): LiveData<ArrayList<Person>>{
        return listFollowing
    }
}



