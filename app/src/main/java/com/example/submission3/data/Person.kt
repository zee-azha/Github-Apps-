package com.example.submission3.data

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Person (
      val login: String,
      val id: Int,
      val avatar_url: String
):Parcelable
