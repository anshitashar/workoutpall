package com.example.workoutpall.data
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.firebase.Timestamp
import org.checkerframework.checker.units.qual.Time
import kotlin.properties.Delegates

@Entity(tableName = "WorkOut")
data class workout (
   @PrimaryKey(autoGenerate = true)
   val id : Int ,
   val WorkOutName : String ,
   //val Timestamp : Timestamp ,
   val Calories : Double
)