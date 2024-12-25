package com.example.workoutpall.data

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.workoutpall.R
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
        binding=ActivityWorkOutHistryBinding.inflate(layoutInflater)
        setContentView(binding.root)
        array= listOf()
        var viewModel: workviewModel
        val adapter=myadapter(array,this)
        val storage = binding.storage
        viewModel= ViewModelProvider(this).get(workviewModel::class.java)
        viewModel.readAllData.observe(this, Observer{workout->
            adapter.setdata(workout)

        })
        storage.adapter=adapter
        storage.layoutManager=LinearLayoutManager(this)
    }
}