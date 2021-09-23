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


class FollowersViewModel: ViewModel() {
    private val listFollower = MutableLiveData<ArrayList<Person>>()

    fun setListFollowers(username: String){
        ClientInstance.apiInstances
            .getPersonFollowers(username)
            .enqueue(object : Callback<ArrayList<Person>>{
                override fun onResponse(
                    call: Call<ArrayList<Person>>,
                    response: Response<ArrayList<Person>>
                ) {
                    if(response.isSuccessful){
                        listFollower.postValue(response.body())
                    }
                }

                override fun onFailure(call: Call<ArrayList<Person>>, t: Throwable) {
                    Log.d("Exception",t.message.toString())
                }
            })
    }

    fun getListFollowers(): LiveData<ArrayList<Person>>{
        return listFollower
    }
}



