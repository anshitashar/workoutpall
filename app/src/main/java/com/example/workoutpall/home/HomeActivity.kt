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
import com.example.workoutpall.databinding.ActivityHomeBinding
import com.google.firebase.auth.FirebaseAuth

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
        //var heading = arrayOf("Cycling","Walking","Running","Cardio","stretching","Aerobics","Yoga","Squats")
        recycler=findViewById(R.id.rvWorkouts)
        arrayList = arrayListOf<homedata>()

        recycler.layoutManager=LinearLayoutManager(this)
        arrayList.add(homedata(image = R.drawable.ic_menu_camera,"Cycling"))
        arrayList.add(homedata(image = R.drawable.ic_menu_camera,"Cycling"))
        arrayList.add(homedata(image = R.drawable.ic_menu_camera,"Cycling"))
        arrayList.add(homedata(image = R.drawable.ic_menu_camera,"Cycling"))
        arrayList.add(homedata(image = R.drawable.ic_menu_camera,"Cycling"))
        arrayList.add(homedata(image = R.drawable.ic_menu_camera,"Cycling"))
        arrayList.add(homedata(image = R.drawable.ic_menu_camera,"Cycling"))
        arrayList.add(homedata(image = R.drawable.ic_menu_camera,"Cycling"))
        var myadapter =HomeWorkAdapter(arrayList,this,)
        recycler.adapter= myadapter
        binding.tvUserName.setOnClickListener{
            val j = Intent(this, ProfileActivity::class.java)
            startActivity(j)
        }
        myadapter.setItemClickListener(object : HomeWorkAdapter.onItemClickListener {
            override fun onItemClick(position: Int) {
                val intent=Intent(this@HomeActivity,MainActivity::class.java)
                startActivity(intent)
            }


        })

    }
}

