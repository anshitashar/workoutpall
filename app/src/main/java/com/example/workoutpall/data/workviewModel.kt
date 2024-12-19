package com.example.workoutpall.data
import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class workviewModel (application: Application):AndroidViewModel(application){
    private lateinit  var readAllDAta :LiveData<List<workout>>
    private lateinit var  repository : workRepository
    init {
        val workOutDAO=workDatabase.getDatabase(application).WorkOutDAO()
       // repository = workRepository(WorkOutDAO)
        //readAllDAta= repository.readAllData
    }
    fun addWorkout(workout: workout){
        viewModelScope.launch (Dispatchers.IO){
            repository.addworkout(workout)
        }
    }
}