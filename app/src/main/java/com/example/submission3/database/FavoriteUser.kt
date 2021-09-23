package com.example.submission3.database

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = "favorite_user")
data class FavoriteUser(
    var login: String,
    @PrimaryKey
    var id: Int,
    var avatar_url: String

):Parcelable
