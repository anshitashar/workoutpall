package com.example.workoutpall.home
import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.workoutpall.DataSummary
import com.example.workoutpall.MainActivity
import com.example.workoutpall.ProfileActivity
import com.example.workoutpall.R
import com.example.workoutpall.Timer
import com.example.workoutpall.WorkOutHistry
import com.example.workoutpall.databinding.ActivityHomeBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.logging.Logger.global

class HomeActivity : AppCompatActivity() {
    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var firestore: FirebaseFirestore
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
        firestore=FirebaseFirestore.getInstance()
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        //var heading = arrayOf("Cycling","Walking","Running","Cardio","stretching","Aerobics","Yoga","Squats")
        var heading = arrayOf("Cycling","Walking","Running","Cardio","stretching","Aerobics")
        recycler=findViewById(R.id.rvWorkouts)
        arrayList = arrayListOf<homedata>()


        recycler.layoutManager= LinearLayoutManager(this)
        for( index in heading.indices){
            val workouts = homedata(image = R.drawable.cycle,heading=(heading[index]))
            arrayList.add(workouts)
        }
        binding.tvUserName.setOnClickListener{
            val j = Intent(this, ProfileActivity::class.java)
            startActivity(j)
        }
        var myadapter =HomeWorkAdapter(arrayList,this,)
        recycler.adapter= myadapter
        myadapter.setItemClickListener(object : HomeWorkAdapter.onItemClickListener {
            override fun onItemClick(position: Int) {
                val intent= Intent(this@HomeActivity,Timer::class.java)
                intent.putExtra("heading", arrayList[position].heading)
                startActivity(intent)
            }


        })
        binding.histry.setOnClickListener{
            val intent= Intent(this@HomeActivity,WorkOutHistry::class.java)
            startActivity(intent)

        }
        val sdf = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        val currentDate = sdf.format(Date())
        binding.tvDate.text=currentDate

        val user=firebaseAuth.currentUser
        val uid= user?.uid
        var cal=0.0
        var time=0.0
        firestore.collection("work$uid").get().addOnSuccessListener {
            result -> for (document in result) {
            if (document.getString("date") == currentDate) {
                val t = document.getDouble("timestamp")
                val c = document.getDouble("calories")
                cal += c!!
                time += t!!
            }
        }
            binding.tvCalories.text= cal.toString()
            binding.tvActivity.text=time.toString()
        }




    }
}

