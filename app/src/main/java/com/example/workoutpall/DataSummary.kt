package com.example.workoutpall
import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.workoutpall.data.Work
import com.example.workoutpall.data.workout
import com.example.workoutpall.data.workviewModel
import com.example.workoutpall.databinding.ActivityDataSummaryBinding
import com.example.workoutpall.home.HomeActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale


class DataSummary : AppCompatActivity() {
    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var viewModel: workviewModel
    private lateinit var binding: ActivityDataSummaryBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_data_summary)
        firebaseAuth = FirebaseAuth.getInstance()
        viewModel=ViewModelProvider(this).get(workviewModel::class.java)
        binding = ActivityDataSummaryBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val finalTime = intent.getIntExtra("finalTime", 0).toString().toInt()
        binding.tvWorkoutTime.text=finalTime.toString()
        calculatecalories()
        val heading = intent.getStringExtra("heading").toString()
        binding.tvWorkout.text=heading
        binding.btnFinish.setOnClickListener{
            addtoroom()
            val intent = Intent(this, HomeActivity::class.java)
            startActivity(intent)
        }
    }


    private fun calculatecalories() {

        firebaseAuth = FirebaseAuth.getInstance()
        val user = firebaseAuth.currentUser
        val uid = user!!.uid
        val db = FirebaseFirestore.getInstance()
        val ref=db.collection("users").document(uid)
        val finalTime = intent.getIntExtra("finalTime", 0).toString().toInt()
        ref.get().addOnSuccessListener {document->
            val weight = document.getString("weight")?.toInt()
            val heading=binding.tvWorkout.text.toString()

            if (heading=="Cycling"){
                val cal=((finalTime*9* weight!!)/3600).toDouble()
                binding.tvCaloriesDisplay.text= cal.toString()
            }else if (heading=="walking"){
                val cal=((finalTime*8* weight!!)/3600).toDouble()
                binding.tvCaloriesDisplay.text= cal.toString()
            }

        }
    }
    private fun addtoroom() {
        firebaseAuth = FirebaseAuth.getInstance()
        val user = firebaseAuth.currentUser
        val uid = user!!.uid
        val db = FirebaseFirestore.getInstance()
        val time = binding.tvWorkoutTime.text.toString().toDouble()
        val workouttype=intent.getStringExtra("heading")
        val calories=binding.tvCaloriesDisplay.text.toString().toDouble()
        val sdf = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        val date = sdf.format(Date())
        var users = Work( workouttype!!,date,time,calories)
        var ref=db.collection("work$uid").document()
        ref.set(users)
        ref.get().addOnSuccessListener { document->
            val time = document.getDouble("timestamp")
            val name=document.getString("workOutName").toString()
            val Calories =document.getDouble("calories")
            val user = workout(0, image = R.drawable.cycle,name,sdf.format(Date()),time!!,Calories!!.toDouble())
            viewModel.insert(user)

        }
        /**db.collection("work$uid").get().addOnSuccessListener {querySnapshot->
           for(document in querySnapshot.documents){
               val time = document.getDouble("timestamp")
               val name=document.getString("workOutName").toString()
               val Calories =document.getDouble("calories")
               val user = workout(0, image = R.drawable.cycle,name,sdf.format(Date()),time!!,Calories!!.toDouble())
               viewModel.insert(user)


           }
       }**/

    }

}