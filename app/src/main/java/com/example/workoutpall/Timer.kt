package com.example.workoutpall

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.os.SystemClock
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.Chronometer
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import java.text.SimpleDateFormat
import java.util.Date

class Timer:AppCompatActivity() {

    private val TAG = "workout_timer"
    private lateinit var chronometer: Chronometer
    private var running = false
    private var started = false
    private lateinit var startStopButton: Button
    private lateinit var resetButton: Button
    private lateinit var finishWorkoutButton: Button
    private var pauseOffset: Long = 0
    private var currentTime: Int = 0  // will be used to store the current time on the chronometer
    private lateinit var workouts: List<String>
    private lateinit var workoutTitle: ImageView
    private lateinit var startTime: Date  // stores the date, and time of the started workout
    private lateinit var finishTime: Date  // stores the date and time of the finished workout
    private lateinit var progressBar: ProgressBar

    @SuppressLint("MissingInflatedId")
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_timer)
        val dtf = SimpleDateFormat("dd/MM/yyyy HH:mm:ss")

        chronometer = findViewById(R.id.timer)
        startStopButton = findViewById(R.id.btnStart_stop)
        resetButton = findViewById(R.id.btnReset)
        finishWorkoutButton = findViewById(R.id.btnFinish)
        workoutTitle = findViewById(R.id.tvWorkoutList)
        progressBar = findViewById(R.id.circleProgress)

        startStopButton.setOnClickListener {
            startStopTimer(it, dtf)
        }

        resetButton.setOnClickListener {
            resetTimer(it)
        }

        finishWorkoutButton.setOnClickListener {
            finishWorkout(it)
        }

        chronometer.setOnChronometerTickListener {
            val elapsedMillis = SystemClock.elapsedRealtime() - chronometer.base
            Log.e(TAG, (elapsedMillis / 1000).toString())
            var timerSeconds = (elapsedMillis / 1000).toInt()
            var finalTimerSeconds = if (timerSeconds > 60) timerSeconds % 60 else timerSeconds

            Log.e(TAG, (finalTimerSeconds * 1.666).toString())
            progressBar.progress = (finalTimerSeconds * 1.666).toInt()
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun startStopTimer(view: View, dtf: SimpleDateFormat) {
        when {
            !running && !started -> {   // If workout has just started
                chronometer.base = SystemClock.elapsedRealtime()
                chronometer.start()
                running = true
                startStopButton.text = "Stop"
                startStopButton.setBackgroundTintList(resources.getColorStateList(R.color.light_blue_A200))
                started = true
                startTime = Date()
                Log.i(TAG, startTime.toString())
            }
            !running && started -> { // If workout is getting unpaused
                chronometer.base = SystemClock.elapsedRealtime() - pauseOffset
                chronometer.start()
                running = true
                startStopButton.text = "Stop"
                startStopButton.setBackgroundTintList(resources.getColorStateList(R.color.light_blue_A200))
            }
            running -> {  // Stops the timer
                chronometer.stop()
                pauseOffset = SystemClock.elapsedRealtime() - chronometer.base
                running = false
                val elapsedMillis = SystemClock.elapsedRealtime() - chronometer.base
                currentTime = (elapsedMillis / 1000).toInt()
                startStopButton.text = "Start"
                startStopButton.setBackgroundTintList(resources.getColorStateList(R.color.light_blue_A200))
            }
        }
    }

    private fun resetTimer(view: View) {
        chronometer.base = SystemClock.elapsedRealtime()
        pauseOffset = 0
        //Toast.makeText(this, "Finished", Toast.LENGTH_SHORT).show()
    }

    private fun finishWorkout(view: View) {
        var seconds = if (!running) {
            currentTime
        } else {
            val elapsedMillis = SystemClock.elapsedRealtime() - chronometer.base
            (elapsedMillis / 1000).toInt()
        }

        if (seconds == 0) {
            Toast.makeText(this, "Workout Not Started!", Toast.LENGTH_SHORT).show()
            return
        }

        finishTime = Date()
        Log.i(TAG, finishTime.toString())

        val i = Intent(this, DataSummary::class.java)
        /**i.putExtra("Workout2", workouts as Serializable)
        i.putExtra("startTime", startTime as Serializable)
        i.putExtra("finishTime", finishTime as Serializable)**/
        i.putExtra("finalTime", seconds)
        startActivity(i)
    }


}