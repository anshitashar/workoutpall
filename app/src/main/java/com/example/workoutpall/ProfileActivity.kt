package com.example.workoutpall

import android.content.ContentValues.TAG
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.workoutpall.databinding.ActivityProfileBinding
import com.example.workoutpall.databinding.ActivitySignBinding
import com.example.workoutpall.home.HomeActivity
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.google.firebase.auth.userProfileChangeRequest

class ProfileActivity : AppCompatActivity() {
    private lateinit var binding: ActivityProfileBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_profile)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        binding = ActivityProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)
        var email=intent.getStringExtra("email")
        var name =intent.getStringExtra("name")
        var weight = intent.getStringExtra("weight")
        var height = intent.getStringExtra("height")
        val user = Firebase.auth.currentUser

        val profileUpdates = userProfileChangeRequest {
            displayName =intent.getStringExtra("name")
            photoUri = Uri.parse("https://example.com/jane-q-user/profile.jpg")
        }
        binding.emailid.text=email
        binding.nmaeid.text=name
        binding.weightid.text=weight
        binding.heightid.text=height
        binding.submit.setOnClickListener{
            user!!.updateProfile(profileUpdates)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        Toast.makeText(this, "Successful", Toast.LENGTH_SHORT).show()
                    }
                }
            val intent = Intent(this, HomeActivity::class.java)
            startActivity(intent)
        }
    }

}