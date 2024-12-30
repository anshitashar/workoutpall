package com.example.workoutpall.data
import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import androidx.room.Room
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

// Creating a flow of data
class workviewModel(application: Application) : AndroidViewModel(application) {
    private val workoutRepository: workRepository
    val readAllData:LiveData<List<workout>>
    val MIGRATION_1_2 = object : Migration(1, 2) {
        override fun migrate(database: SupportSQLiteDatabase) {
            // Drop the WorkOut table in the migration path
        }
    }


    // Create a Room database instance
    private val db = Room.databaseBuilder(application, workDatabase::class.java, "workout_database").fallbackToDestructiveMigration().build()
    private val workoutDao = db.WorkOutDAO()

    init {
        // Initialize the repository
        workoutRepository = workRepository(workoutDao)

        // Get the list of all workouts and observe it in the UI
         readAllData= workoutRepository.readAllData
    }

    // Function to insert a workout
    fun insert(workout: workout) {
        viewModelScope.launch(Dispatchers.IO) {
            workoutRepository.addworkout(workout)
        }
    }
    fun clearAllData() {
        viewModelScope.launch(Dispatchers.IO) {
            workoutDao.deleteAllWorkouts()
        }
    }
}


