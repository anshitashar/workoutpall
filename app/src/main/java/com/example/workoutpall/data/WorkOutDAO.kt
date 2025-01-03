package com.example.workoutpall.data
import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
 interface WorkOutDAO {
    @Query("SELECT * FROM WorkOut ORDER BY date ASC")
    fun readAllData(): LiveData<List<workout>>
    @Insert
    fun addData(workout: workout)
   @Query("DELETE FROM WorkOut")
   suspend fun deleteAllWorkouts()
    @Query( """
        SELECT strftime('%Y-%m', date) AS month, sum(Calories) AS Tc
        FROM workout
        GROUP BY month
    """)
     fun read():LiveData<List<MonthlyCalories>>

}