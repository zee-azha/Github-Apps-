package com.example.submission3.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [FavoriteUser::class], version = 1)
abstract class UserRoomDatabase: RoomDatabase() {

    abstract fun favoriteDao(): FavoriteDao

    companion object{
        @Volatile
        private var INSTANCE: UserRoomDatabase? = null

        @JvmStatic
        fun getDatabase(context: Context): UserRoomDatabase{
            if(INSTANCE == null){
                synchronized(UserRoomDatabase::class.java){
                    INSTANCE = Room.databaseBuilder(context.applicationContext,
                        UserRoomDatabase::class.java, "user_database")
                        .build()
                }
            }
            return INSTANCE as UserRoomDatabase
        }
    }
}