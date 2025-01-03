package com.example.workoutpall.home
import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.workoutpall.Achivement
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
        firebaseAuth = FirebaseAuth.getInstance()
        firestore=FirebaseFirestore.getInstance()
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val user=firebaseAuth.currentUser
        val uid= user?.uid
        val userinfo =firestore.collection("users").document(uid!!).get()
        userinfo.addOnSuccessListener { document->
            binding.tvUserName.text="Hiii!"+document.getString("name")
        }
        val heading = arrayOf("Cycling","Walking","Running","Skipping","Stretching","Aerobics","Yoga","Squats")
        val image = arrayOf(R.drawable.cycle,R.drawable.walking,R.drawable.running,R.drawable.skipping,R.drawable.walking,R.drawable.running,R.drawable.yoga,R.drawable.running)
        recycler=findViewById(R.id.rvWorkouts)
        arrayList = arrayListOf<homedata>()

        recycler.layoutManager=LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false)
        for( index in heading.indices){
            val workouts = homedata(image = image[index],heading=(heading[index]))
            arrayList.add(workouts)
        }
        binding.tvUserName.setOnClickListener{
            val j = Intent(this, ProfileActivity::class.java)
            startActivity(j)
        }
        val myadapter =HomeWorkAdapter(arrayList,this,)
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
        binding.Achivement.setOnClickListener{
            val intent= Intent(this@HomeActivity,Achivement::class.java)
            startActivity(intent)
        }
        val sdf = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        val currentDate = sdf.format(Date())
        binding.tvDate.text=currentDate
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
            binding.tvCalories.text= cal.toLong().toString()
            binding.tvActivity.text=(time/60).toLong().toString()+"min"
        }
    }
}

