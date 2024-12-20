package com.example.workoutpall
import android.content.Intent
import android.os.Bundle
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.workoutpall.databinding.ActivitySignBinding
import com.example.workoutpall.ui.home.HomeActivity
import com.google.android.material.button.MaterialButton
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase

class SignActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySignBinding
    private lateinit var firebaseAuth: FirebaseAuth
    public lateinit var email:String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign)
        var database = FirebaseDatabase.getInstance()
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
                    val user =firebaseAuth.currentUser
                    user?.let {
                        val userID = it.uid
                        val userRef = database.getReference("users").child(userID)
                        val userDetails = hashMapOf(
                            "NAME" to name
                        )
                    }
                    Toast.makeText(this, "Registration Successful", Toast.LENGTH_SHORT).show()
                    val j = Intent(this, ProfileActivity::class.java)
                    j.putExtra("email",emailText )
                    j.putExtra("name",name )
                    j.putExtra("height",height )
                    j.putExtra("weight",weight )
                    startActivity(j)
                    val i = Intent(this, HomeActivity::class.java)
                    i.putExtra("name",name )

                } else {
                    Toast.makeText(this, "Registration Failed: ${task.exception?.message}", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}