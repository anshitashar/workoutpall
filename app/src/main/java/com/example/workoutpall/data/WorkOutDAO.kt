package com.example.workoutpall.data
import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
 interface WorkOutDAO {
    @Query("SELECT * FROM workout  ")
    fun readAllData(): LiveData<List<workout>>
    @Insert
    fun addData(workout: workout)
   @Query("DELETE FROM WorkOut")
   suspend fun deleteAllWorkouts()
}