package com.example.submission3.viewmodel


import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.submission3.api.ClientInstance
import com.example.submission3.data.DetailsResponse
import com.example.submission3.database.FavoriteDao
import com.example.submission3.database.FavoriteUser
import com.example.submission3.database.UserRoomDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class DetailsViewModel(application: Application): AndroidViewModel(application) {
    val listPersonDetail = MutableLiveData<DetailsResponse>()

    private val toastMessageObserver: MutableLiveData<String?> = MutableLiveData()

    private var mUserDao: FavoriteDao?
    private var userDb: UserRoomDatabase? = UserRoomDatabase.getDatabase(application)

    init {
        mUserDao = userDb?.favoriteDao()

    }

    fun setOnDetails(username: String){
        ClientInstance.apiInstances
            .getPersonDetails(username)
            .enqueue(object: Callback<DetailsResponse>{
                override fun onResponse(
                    call: Call<DetailsResponse>,
                    response: Response<DetailsResponse>
                ) {
                    if(response.isSuccessful){
                        listPersonDetail.postValue(response.body())
                    }
                }

                override fun onFailure(call: Call<DetailsResponse>, error: Throwable) {
                    Log.d("Exception",error.message.toString())
                    toastMessageObserver.postValue("OnFailure: "+error.message.toString())
                }
            })
    }

    fun getOnDetails(): LiveData<DetailsResponse> {
        return listPersonDetail
    }

    fun getToastObserver(): LiveData<String?> {
        return toastMessageObserver
    }

    fun insert(username: String,id: Int, avatar: String) {
        CoroutineScope(Dispatchers.IO).launch{
            val user = FavoriteUser(
                username,
                id,
                avatar
            )
            mUserDao?.insert(user)
        }
    }

    suspend fun checkUser(id: Int) = mUserDao?.checkUser(id)

    fun remove(id: Int){
        CoroutineScope(Dispatchers.IO).launch {
            mUserDao?.remove(id)
        }
    }


}