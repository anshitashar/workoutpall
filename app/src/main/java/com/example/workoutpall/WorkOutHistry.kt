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
        binding=ActivityWorkOutHistryBinding.inflate(layoutInflater)
        setContentView(binding.root)
        array= listOf()
        val adapter= myadapter(array,this)
        val storage = binding.storage
        storage.adapter=adapter
        viewModel= ViewModelProvider(this).get(workviewModel::class.java)
        viewModel.readAllData.observe(this, Observer{Workout->
            adapter.setdata(Workout)
        })

        storage.layoutManager=LinearLayoutManager(this)
    }
}
