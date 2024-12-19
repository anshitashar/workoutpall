package com.example.workoutpall.ui.home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.workoutpall.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import edu.csueb.codepath.fitness_tracker.DateSummary
import edu.csueb.codepath.fitness_tracker.HomeWorkoutListAdapter
import edu.csueb.codepath.fitness_tracker.R
import com.example.workoutpall.ui.home
import java.text.DecimalFormat

class HomeFragment : Fragment() {

    private lateinit var tvUserName: TextView
    private lateinit var tvDate: TextView
    private lateinit var tvActivity: TextView
    private lateinit var tvCalories: TextView
    private lateinit var rvWorkouts: RecyclerView
    private val TAG = "HomeFragment"

    private val workout = mutableListOf<String>()   // Workout name
    private val listTime = mutableListOf<String>()  // Time for each workout
    private val indivCalo = mutableListOf<String>() // Individual calories per workout
    private lateinit var adapter: HomeWorkoutListAdapter
    private var totalCal: Double = 0.0
    private var totalTime: Int = 0
    private val df2 = DecimalFormat("#.##")

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_home, container, false)

        tvUserName = view.findViewById(R.id.tvUserName)
        tvDate = view.findViewById(R.id.tvDate)
        tvCalories = view.findViewById(R.id.tvCalories)
        tvActivity = view.findViewById(R.id.tvActivity)

        getCurrentUser()

        rvWorkouts = view.findViewById(R.id.rvWorkouts)
        adapter = HomeWorkoutListAdapter(workout, listTime, indivCalo, this)
        rvWorkouts.layoutManager = LinearLayoutManager(context)
        rvWorkouts.adapter = adapter

        populateWorkout()

        tvCalories.text = df2.format(totalCal).toString()
        Log.e(TAG, workout.size.toString())

        return view
    }

    private fun getCurrentUser() {
        val currentUser = FirebaseAuth.getInstance().currentUser
        if (currentUser != null) {
            tvUserName.text = "Hello ${currentUser.displayName}"
        } else {
            // Show the signup or login screen if not authenticated
            tvUserName.text = "Guest"
        }
        tvDate.text = DateSummary.getDate()
    }

    private fun populateWorkout() {
        val userId = FirebaseAuth.getInstance().currentUser?.uid
        if (userId != null) {
            val databaseRef = FirebaseDatabase.getInstance().getReference("workouts")
                .child(userId)
                .limitToLast(20)

            databaseRef.addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    var min = 0
                    var sec = 0
                    for (workoutSnapshot in snapshot.children) {
                        val workoutType = workoutSnapshot.child("workoutType").getValue(String::class.java)
                        val duration = workoutSnapshot.child("duration").getValue(String::class.java)
                        val calories = workoutSnapshot.child("calories").getValue(Double::class.java) ?: 0.0

                        workoutType?.let { workout.add(it) }
                        duration?.let { listTime.add(it) }
                        indivCalo.add(calories.toString())
                        totalCal += calories

                        val time = duration?.split(":") ?: listOf("0", "0")
                        min = time[0].toInt()
                        sec = time[1].toInt()
                        totalTime += (min * 60) + sec
                    }

                    Log.e(TAG, totalCal.toString())
                    tvCalories.text = df2.format(totalCal).toString()
                    val finalSec = if (totalTime % 60 < 9) {
                        "0${totalTime % 60}"
                    } else {
                        (totalTime % 60).toString()
                    }

                    val finalTime = "${totalTime / 60}:$finalSec"
                    tvActivity.text = finalTime

                    adapter.notifyDataSetChanged()
                }

                override fun onCancelled(error: DatabaseError) {
                    Log.e(TAG, "Error populating workouts", error.toException())
                }
            })
        }
    }
}
