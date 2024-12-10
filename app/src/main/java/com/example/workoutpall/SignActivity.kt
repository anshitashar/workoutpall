package com.example.workoutpall
import android.content.Intent
import android.os.Bundle
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.material.button.MaterialButton
import com.google.firebase.auth.FirebaseAuth
class SignActivity : AppCompatActivity() {
    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var binding: SignActivity
    private lateinit var signup: MaterialButton
    private lateinit var register: MaterialButton
    private lateinit var email: TextView
    private lateinit var password: TextView
    private lateinit var confirmpass:TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_sign)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
            password = findViewById(R.id.password)
            email = findViewById(R.id.email)
            confirmpass = findViewById(R.id.conpassword)
            signup = findViewById(R.id.register)
            firebaseAuth = FirebaseAuth.getInstance()
            val email = email.text.toString()
            val pass = password.text.toString()
            val confirmPass = confirmpass.text.toString()
            signup.setOnClickListener {
                if (pass == confirmPass) {
                    firebaseAuth.createUserWithEmailAndPassword(email, pass).addOnCompleteListener {
                        if (it.isSuccessful) {
                           Toast.makeText(this,"successfully",Toast.LENGTH_SHORT).show()
                        } else {
                            Toast.makeText(this,"error", Toast.LENGTH_SHORT).show()
                        }
                    }
                } else {
                    Toast.makeText(this, "Password is not matching", Toast.LENGTH_SHORT).show()
                }
            }
    }
}