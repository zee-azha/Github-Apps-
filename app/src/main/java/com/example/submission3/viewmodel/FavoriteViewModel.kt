package com.example.submission3.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.example.submission3.database.FavoriteDao
import com.example.submission3.database.FavoriteUser
import com.example.submission3.database.UserRoomDatabase

class FavoriteViewModel(application: Application): AndroidViewModel(application) {

    private var mUserDao: FavoriteDao?
    private var userDb: UserRoomDatabase? = UserRoomDatabase.getDatabase(application)

    init {
        mUserDao = userDb?.favoriteDao()
    }

    fun getFavoriteUser(): LiveData<List<FavoriteUser>>? {
       return mUserDao?.getFavoriteUser()
    }
}