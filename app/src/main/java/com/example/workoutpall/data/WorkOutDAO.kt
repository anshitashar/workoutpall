package com.example.workoutpall.data
import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
public interface WorkOutDAO {
    @Query("SELECT * FROM workout ORDER BY id ASC")
    fun readAllData(): LiveData<List<workout>>
    @Insert
    fun addData(workout: workout)
}