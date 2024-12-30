package com.example.workoutpall
import android.content.Intent
import android.os.Bundle
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
        val userinfo =db.collection("users").document(uid).get()
        userinfo.addOnSuccessListener { document->
            binding.nmaeid.text=document.getString("name")
            binding.weightid.text=document.getString("weight")
            binding.heightid.text=document.getString("height")

        }
        binding.submit.setOnClickListener {

            val intent = Intent(this, HomeActivity::class.java)
            startActivity(intent)
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



