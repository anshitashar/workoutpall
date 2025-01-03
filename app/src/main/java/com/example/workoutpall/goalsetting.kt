package com.example.workoutpall

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.workoutpall.data.goalset
import com.example.workoutpall.databinding.ActivityGoalsettingBinding
import com.example.workoutpall.databinding.ActivityLoginBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class goalsetting : AppCompatActivity() {
    private lateinit var firebaseAuth:FirebaseAuth
    private lateinit var firebaseFirestore:FirebaseFirestore
    private lateinit var binding: ActivityGoalsettingBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_goalsetting)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        firebaseAuth=FirebaseAuth.getInstance()
        val user = firebaseAuth.currentUser
        val uid = user!!.uid
        binding =ActivityGoalsettingBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val db = FirebaseFirestore.getInstance()
        binding.button2.setOnClickListener{
            val monthly=binding.editTextText1.text.toString().toDouble()
            val daily=binding.editTextText2.text.toString().toDouble()
            val value = mapOf(
                "monthcal" to monthly,
                 "daily" to daily
            )
            db.collection("goal").document(uid).update(value)
            val intent = Intent(this, Achivement::class.java)
            startActivity(intent)
        }

    }
}