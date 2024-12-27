package com.example.workoutpall
import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.workoutpall.databinding.ActivityProfileBinding
import com.example.workoutpall.home.HomeActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class ProfileActivity : AppCompatActivity() {
    private lateinit var FirebaseAuth:FirebaseAuth
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
        val user = FirebaseAuth.currentUser
        val uid = user!!.uid
        val db = FirebaseFirestore.getInstance()
        val userinfo =db.collection("users").document(uid).get()

            val intent = Intent(this, HomeActivity::class.java)
            startActivity(intent)
        }
    }


