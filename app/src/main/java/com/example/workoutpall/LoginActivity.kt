package com.example.workoutpall
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
import com.example.workoutpall.data.workout
import com.example.workoutpall.data.workviewModel
import com.example.workoutpall.databinding.ActivityLoginBinding
import com.example.workoutpall.home.HomeActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class LoginActivity : AppCompatActivity() {
    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var binding: ActivityLoginBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_login)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        firebaseAuth = FirebaseAuth.getInstance()
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.register.setOnClickListener {
            val intent = Intent(this, SignActivity::class.java)
            startActivity(intent)
        }

        binding.login.setOnClickListener {
            val email = binding.email.text.toString()
            val pass = binding.password.text.toString()

            if (email.isNotEmpty() && pass.isNotEmpty()) {

                firebaseAuth.signInWithEmailAndPassword(email, pass).addOnCompleteListener {
                    if (it.isSuccessful) {
                        var viewModel: workviewModel
                        var firebaseAuth:FirebaseAuth
                        firebaseAuth = FirebaseAuth.getInstance()
                        val user = firebaseAuth.currentUser
                        val uid = user!!.uid
                        val db = FirebaseFirestore.getInstance()
                        db.collection("work$uid").get().addOnSuccessListener {querySnapshot->
                        for(document in querySnapshot.documents) {
                            val name = document.getString("workOutName").toString()
                            val Calories = document.getDouble("calories")
                            val time = document.getDouble("time")
                            var date=document.getString("date")
                            val user = workout(0, image = R.drawable.cycle, name,date!!,time!!, Calories!!.toDouble())
                            viewModel = ViewModelProvider(this).get(workviewModel::class.java)
                            viewModel.insert(user)
                        }
                        }
                        val intent = Intent(this, HomeActivity::class.java)
                        startActivity(intent)

                    } else {
                        Toast.makeText(this, it.exception.toString(), Toast.LENGTH_SHORT).show()

                    }
                }
            } else {
                Toast.makeText(this, "Empty Fields Are not Allowed !!", Toast.LENGTH_SHORT).show()

            }
        }
    }

}