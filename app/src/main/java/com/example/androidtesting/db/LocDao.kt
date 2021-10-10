package com.example.androidtesting.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.androidtesting.utils.UserLocation
import kotlinx.coroutines.flow.Flow

@Dao
interface LocDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertData(userLocation: UserLocation)

    @Query("Select * from User_Entity_table")
    fun getData(): Flow<List<UserLocation>>

}