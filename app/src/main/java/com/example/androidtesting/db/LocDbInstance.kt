package com.example.androidtesting.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.androidtesting.utils.UserLocation

@Database(entities = [UserLocation::class], version = 1)
abstract class LocDbInstance : RoomDatabase() {
    abstract val locDao: LocDao

    companion object {
        private var INSTANCE: LocDbInstance? = null
        fun getInstance(context: Context): LocDbInstance {
            synchronized(this) {
                val instance = INSTANCE
                if (instance != null) {
                    return instance
                }
                synchronized(this) {
                    val oldInstance = Room.databaseBuilder(
                        context.applicationContext,
                        LocDbInstance::class.java,
                        "My_Location_db"
                    ).build()
                    INSTANCE = oldInstance
                    return oldInstance
                }
            }

        }
    }
}