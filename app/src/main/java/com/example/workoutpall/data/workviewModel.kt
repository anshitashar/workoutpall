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
    //private val workoutRepository: workRepository
    val read :LiveData<List<MonthlyCalories>>
    val readAllData:LiveData<List<workout>>
    val MIGRATION_1_2 = object : Migration(1, 2) {
        override fun migrate(database: SupportSQLiteDatabase) {
        }
    }
    private val db = Room.databaseBuilder(application, workDatabase::class.java, "workout_database").addMigrations(MIGRATION_1_2).build()
    private val workoutDao = db.WorkOutDAO()

   init {
        read = workoutDao.read()
        readAllData= workoutDao.readAllData()

   }

    fun insert(workout: workout) {
        viewModelScope.launch(Dispatchers.IO) {
            workoutDao.addData(workout)
        }
    }
    fun clearAllData() {
        viewModelScope.launch(Dispatchers.IO) {
            workoutDao.deleteAllWorkouts()
        }
    }
    fun getMonthlyCalories() {
        viewModelScope.launch(Dispatchers.IO) {
            workoutDao.read()
        }
    }

}




