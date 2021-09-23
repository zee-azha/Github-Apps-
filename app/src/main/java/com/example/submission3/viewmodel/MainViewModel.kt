package com.example.submission3.viewmodel


import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.submission3.api.ClientInstance
import com.example.submission3.data.Person
import com.example.submission3.data.ResponsePerson
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class MainViewModel: ViewModel() {
    private val listPerson = MutableLiveData<ArrayList<Person>>()
    private val toastMessageObserver: MutableLiveData<String?> = MutableLiveData()

    fun setOnSearch(query: String){
        ClientInstance.apiInstances
            .getOnSearch(query)
            .enqueue(object : Callback<ResponsePerson>{
                override fun onResponse(
                    call: Call<ResponsePerson>,
                    response: Response<ResponsePerson>
                ) {
                    if(response.isSuccessful){
                        listPerson.postValue(response.body()?.items)
                    }
                }

                override fun onFailure(call: Call<ResponsePerson>, error: Throwable) {
                    Log.d("onFailure", error.message.toString())
                    toastMessageObserver.postValue("OnFailure: "+error.message.toString())
                }
            })
    }

    fun getOnSearch(): LiveData<ArrayList<Person>>{
        return listPerson
    }

    fun getToastObserver(): LiveData<String?> {
        return toastMessageObserver
    }
}