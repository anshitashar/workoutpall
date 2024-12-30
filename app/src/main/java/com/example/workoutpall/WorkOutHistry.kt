package com.example.workoutpall
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.workoutpall.data.myadapter
import com.example.workoutpall.data.workout
import com.example.workoutpall.data.workviewModel
import com.example.workoutpall.databinding.ActivityWorkOutHistryBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class WorkOutHistry : AppCompatActivity() {
    private lateinit var binding: ActivityWorkOutHistryBinding
    lateinit var array:List<workout>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_work_out_histry)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets

        }
        var viewModel: workviewModel
        var firebaseAuth:FirebaseAuth
        firebaseAuth = FirebaseAuth.getInstance()
        /**val user = firebaseAuth.currentUser
        val uid = user!!.uid
        val db = FirebaseFirestore.getInstance()
        db.collection("work$uid").get().addOnSuccessListener {querySnapshot->
            for(document in querySnapshot.documents) {
                val name = document.getString("workOutName").toString()
                val Calories = document.getDouble("calories")
                val user = workout(0, image = R.drawable.cycle, name, Calories!!.toDouble())
                viewModel = ViewModelProvider(this).get(workviewModel::class.java)
                viewModel.insert(user)
            }
        }**/
        binding=ActivityWorkOutHistryBinding.inflate(layoutInflater)
        setContentView(binding.root)
        array= listOf()
        val adapter= myadapter(array,this)
        val storage = binding.storage
        viewModel= ViewModelProvider(this).get(workviewModel::class.java)
        viewModel.readAllData.observe(this, Observer{workout->
            adapter.setdata(workout)

        })
        storage.adapter=adapter
        storage.layoutManager=LinearLayoutManager(this)
    }
}
