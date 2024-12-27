package com.example.workoutpall

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
import com.example.workoutpall.data.WorkOutHistry
import com.example.workoutpall.data.workout
import com.example.workoutpall.data.workviewModel
import com.example.workoutpall.databinding.ActivityDataSummaryBinding


class DataSummary : AppCompatActivity() {
    private lateinit var viewModel: workviewModel
    private lateinit var binding: ActivityDataSummaryBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_data_summary)
        viewModel=ViewModelProvider(this).get(workviewModel::class.java)
        binding = ActivityDataSummaryBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.btnFinish.setOnClickListener{
            val workouttype=binding.tvWorkout.text.toString()
            val calories=binding.tvCaloriesDisplay.text.toString().toDouble()
            val user = workout(0, image = R.drawable.cycle,workouttype,calories)
            deleteDatabase( "WorkOut")

            val intent = Intent(this,WorkOutHistry::class.java)
            startActivity(intent)
        }

    }
}