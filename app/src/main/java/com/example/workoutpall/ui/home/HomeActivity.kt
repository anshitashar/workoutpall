package com.example.workoutpall.ui.home
import android.os.Bundle
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.Recycler
import com.example.workoutpall.R
import com.example.workoutpall.databinding.ActivityHomeBinding
import com.example.workoutpall.databinding.ActivityLoginBinding
import com.google.firebase.auth.FirebaseAuth
import kotlin.collections.*


class HomeActivity : AppCompatActivity() {
    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var binding: ActivityHomeBinding
    public final lateinit var arrayList: ArrayList<homedata>
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
        var name = intent.getStringExtra("name")
        name = binding.tvUserName.text.toString()
        setContentView(binding.root)
        var heading = arrayOf("Cycling","Walking","Running","Cardio","stretching","Aerobics")
        recycler=findViewById(R.id.rvWorkouts)
        arrayList = arrayListOf<homedata>()


        recycler.layoutManager=LinearLayoutManager(this)
        for( index in arrayList.indices){
            val workouts= homedata(heading[index])
            arrayList.add(workouts)
        }
        recycler.adapter=HomeWorkAdapter(arrayList,this)

    }

}

