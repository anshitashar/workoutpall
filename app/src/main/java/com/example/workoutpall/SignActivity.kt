package com.example.workoutpall
import android.os.Bundle
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.workoutpall.databinding.ActivitySignBinding
import com.google.android.material.button.MaterialButton
import com.google.firebase.auth.FirebaseAuth
class SignActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySignBinding
    private lateinit var firebaseAuth: FirebaseAuth
    //private lateinit var signup: MaterialButton
    //private lateinit var email: EditText
    //private lateinit var password: EditText
    //private lateinit var confirmpass: EditText
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign)
        firebaseAuth = FirebaseAuth.getInstance()
        binding = ActivitySignBinding.inflate(layoutInflater)
        setContentView(binding.root)
        //email = findViewById(R.id.email)
        //password = findViewById(R.id.password)
        //confirmpass = findViewById(R.id.conpassword)
        //signup = findViewById(R.id.register)
        binding.register.setOnClickListener {
            val emailText = binding.email.text.toString().trim()
            val passwordText = binding.password.text.toString().trim()
            val confirmPassText = binding.conpassword.text.toString().trim()

            if (emailText.isEmpty() || passwordText.isEmpty() || confirmPassText.isEmpty()) {
                Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (passwordText != confirmPassText) {
                Toast.makeText(this, "Passwords do not match", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            firebaseAuth.createUserWithEmailAndPassword(emailText, passwordText).addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Toast.makeText(this, "Registration Successful", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(this, "Registration Failed: ${task.exception?.message}", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}
