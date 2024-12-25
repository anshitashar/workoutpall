package com.example.workoutpall.data
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.type.Date
import java.sql.Timestamp

@Entity(tableName = "WorkOut")
data class workout (
   @PrimaryKey(autoGenerate = true)
   val id : Int,
   val image:Int,
   val WorkOutName : String,
   //val date: Date,
   //val Timestamp : Timestamp,
   val Calories : Double
)