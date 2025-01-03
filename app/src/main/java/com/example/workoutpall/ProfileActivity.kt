package com.example.workoutpall
import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.workoutpall.data.workDatabase
import com.example.workoutpall.data.workviewModel
import com.example.workoutpall.databinding.ActivityProfileBinding
import com.example.workoutpall.home.HomeActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class ProfileActivity : AppCompatActivity() {
    private lateinit var dbase: workDatabase
    private lateinit var firebaseAuth:FirebaseAuth
    private lateinit var binding: ActivityProfileBinding
    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_profile)
        firebaseAuth=FirebaseAuth.getInstance()
        binding = ActivityProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)
        dbase=workDatabase.getDatabase(this)
        val user = firebaseAuth.currentUser
        val uid = user!!.uid
        val db = FirebaseFirestore.getInstance()
        val userinfo =db.collection("users").document(uid)
        userinfo.get().addOnSuccessListener { document->
            binding.nmaeid.setText(document.getString("name"))
            binding.weightid.setText(document.getString("weight"))
            binding.heightid.setText(document.getString("height"))
            binding.emailid.setText(document.getString("email"))
        }
        binding.close.setOnClickListener {
            val intent = Intent(this, HomeActivity::class.java)
            startActivity(intent)
        }
        binding.submit.setOnClickListener{
            val name = binding.nmaeid.text.toString()
            val email =binding.emailid.text.toString()
            val height = binding.heightid.text.toString()
            val weight = binding.weightid.text.toString()
            val udpademap  = mapOf(
                "name" to name,
                "email" to email,
                "height" to height,
                "weight" to weight
            )
            userinfo.update(udpademap).addOnSuccessListener {
                Toast.makeText(this, "Successfully Updated", Toast.LENGTH_SHORT).show()
            }.addOnFailureListener {
            Toast.makeText(this, "Failed", Toast.LENGTH_SHORT).show()
        }
        }
        binding.SignOut.setOnClickListener{
            firebaseAuth.signOut()
            clearallData()
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
    }
    private fun clearallData() {
        var viewModel: workviewModel
        viewModel=ViewModelProvider(this).get(workviewModel::class.java)
        viewModel.clearAllData()
        }

    }



