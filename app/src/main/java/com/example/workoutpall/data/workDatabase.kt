package com.example.workoutpall.data
import androidx.room.Database
import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase


@Database(entities = [workout::class], version = 1 , exportSchema =  false)
abstract class workDatabase : RoomDatabase() {
    abstract fun WorkOutDAO(): WorkOutDAO
   /**companion object{
        @Volatile
        private var INSTANCE: workDatabase? = null

        fun getDatabase(context: Context ): workDatabase {
            val tempInstance = INSTANCE
            if(tempInstance!= null)
                return tempInstance

            synchronized(this){
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    workDatabase::class.java,
                    name = "work_Database"
                ).build()
                INSTANCE=instance
                return instance
            }
        }
    }**/
}