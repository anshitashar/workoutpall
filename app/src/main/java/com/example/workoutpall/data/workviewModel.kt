package com.example.workoutpall.data
import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import androidx.room.Room
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch

// Creating a flow of data





class workviewModel(application: Application) : AndroidViewModel(application) {
    private val workoutRepository: workRepository

    // Create a Room database instance
    private val db = Room.databaseBuilder(application, workDatabase::class.java, "workout_database").build()
    private val workoutDao = db.WorkOutDAO()

    init {
        // Initialize the repository
        workoutRepository = workRepository(workoutDao)

        // Get the list of all workouts and observe it in the UI
        val readAllData: LiveData<List<workout>> = workoutDao.readAllData()
    }

    // Function to insert a workout
    fun insert(workout: workout) {
        viewModelScope.launch {
            workoutRepository.addworkout(workout)
        }
    }


}