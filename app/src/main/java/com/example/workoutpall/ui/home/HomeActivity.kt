package com.example.workoutpall.ui.home
import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.workoutpall.ProfileActivity
import com.example.workoutpall.R
import com.example.workoutpall.databinding.ActivityHomeBinding
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import kotlin.collections.*


class HomeActivity : AppCompatActivity() {
    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var binding: ActivityHomeBinding
     lateinit var arrayList: ArrayList<homedata>
    private lateinit var recycler: RecyclerView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_home)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        firebaseAuth = FirebaseAuth.getInstance()
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)
       // var name = intent.getStringExtra("name")

        val user = Firebase.auth.currentUser
        user?.let {
            // Name, email address, and profile photo Url
            var name= it.displayName
            val emailText = it.email
            // Check if user's email is verified
            val emailVerified = it.isEmailVerified
            // The user's ID, unique to the Firebase project. Do NOT use this value to
            // authenticate with your backend server, if you have one. Use
            // FirebaseUser.getIdToken() instead.
            val uid = it.uid
            binding.tvUserName.text="Hello!"+name
        }

        var heading = arrayOf("Cycling","Walking","Running","Cardio","stretching","Aerobics","Yoga","Squats")
        recycler=findViewById(R.id.rvWorkouts)
        arrayList = arrayListOf<homedata>()

        recycler.layoutManager=LinearLayoutManager(this)
        for( index in arrayList.indices){
            val workouts= homedata(heading[index])
            arrayList.add(workouts)
        }
        recycler.adapter=HomeWorkAdapter(heading,this)
        binding.tvUserName.setOnClickListener{
            val j = Intent(this, ProfileActivity::class.java)
            startActivity(j)
        }
    }


}

