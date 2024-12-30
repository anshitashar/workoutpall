package com.example.workoutpall.data
import androidx.lifecycle.LiveData


class workRepository (private val workOutDAO: WorkOutDAO){
    val readAllData : LiveData<List<workout>> = workOutDAO.readAllData()
    fun addworkout(workout: workout){
        workOutDAO.addData(workout)
    }
}