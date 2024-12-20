package com.example.workoutpall.data
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "WorkOut")
data class workout (
   @PrimaryKey(autoGenerate = true)
   val id : Int ,
   val WorkOutName : String ,
   //val Timestamp : Timestamp ,
   val Calories : Double
)