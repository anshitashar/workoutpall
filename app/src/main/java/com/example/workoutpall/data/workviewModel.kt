package com.example.workoutpall.data
import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import androidx.room.Room
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch

// Creating a flow of data
class workviewModel(application: Application) : AndroidViewModel(application) {
    private val workoutRepository: workRepository
    val readAllData:LiveData<List<workout>>
    val MIGRATION_1_2 = object : Migration(1, 2) {
        override fun migrate(database: SupportSQLiteDatabase) {
            database.execSQL("""
            CREATE TABLE WorkOut_new (
                id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
                image INTEGER NOT NULL,
                WorkOutName TEXT NOT NULL,
                Calories REAL NOT NULL
            )
        """)

            // Step 2: Copy the data from the old table to the new table
            database.execSQL("""
            INSERT INTO WorkOut_new (id, image, WorkOutName, Calories)
            SELECT id, image, WorkOutName, CAST(Calories AS REAL) FROM WorkOut
        """)

            // Step 3: Drop the old table
            database.execSQL("DROP TABLE WorkOut")

            // Step 4: Rename the new table to the old table name
            database.execSQL("ALTER TABLE WorkOut_new RENAME TO WorkOut")
        }
    }


    // Create a Room database instance
    private val db = Room.databaseBuilder(application, workDatabase::class.java, "workout_database") .addMigrations(MIGRATION_1_2).build()
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
}


