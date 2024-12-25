package com.example.workoutpall
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.workoutpall.databinding.ActivitySignBinding
import com.example.workoutpall.home.HomeActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.firestore.FirebaseFirestore

class SignActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySignBinding
    private lateinit var firebaseAuth: FirebaseAuth
    lateinit var database : DatabaseReference
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign)
        firebaseAuth = FirebaseAuth.getInstance()
        binding = ActivitySignBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.log.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }
        binding.register.setOnClickListener {
            val emailText = binding.email.text.toString().trim()
            val passwordText = binding.password.text.toString().trim()
            val confirmPassText = binding.conpassword.text.toString().trim()
            val name = binding.name.text.toString().trim()
            val height=binding.height.text.toString().trim()
            val weight=binding.weight.text.toString().trim()
            if (emailText.isEmpty() || passwordText.isEmpty() || confirmPassText.isEmpty() || name.isEmpty()|| height.isEmpty() || weight.isEmpty() ) {
                Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            if (passwordText != confirmPassText) {
                Toast.makeText(this, "Passwords do not match", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            firebaseAuth.createUserWithEmailAndPassword(emailText, passwordText).addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val userid = User(Name = name, email = emailText, weight = weight, Height = height)
                    val user = firebaseAuth.currentUser
                    val uid = user?.uid
                    val db = FirebaseFirestore.getInstance()
// Assuming you're storing user data in a collection called 'users' with document ID as the user's UID
                    db.collection("users").document(uid.toString()).set(userid) // 'user' is an instance of the User data model
                        .addOnSuccessListener { documentReference ->
                           Toast.makeText(this,"added",Toast.LENGTH_SHORT).show()
                        }
                        .addOnFailureListener {
                            Toast.makeText(this, "adding Failed: ${task.exception?.message}", Toast.LENGTH_SHORT).show()
                        }
                    Toast.makeText(this, "Registration Successful", Toast.LENGTH_SHORT).show()
                    val i = Intent(this, HomeActivity::class.java)
                    startActivity(i)
                } else {
                    Toast.makeText(this, "Registration Failed: ${task.exception?.message}", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}




