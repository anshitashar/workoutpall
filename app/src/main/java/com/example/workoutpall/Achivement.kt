package com.example.workoutpall
import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.workoutpall.data.workviewModel
import com.example.workoutpall.databinding.ActivityAchivementBinding
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class Achivement : AppCompatActivity() {
    private lateinit var binding: ActivityAchivementBinding
    private lateinit var viewModel: workviewModel
    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAchivementBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val sdf = SimpleDateFormat("yyyy-MM", Locale.getDefault())
        val currentDate = sdf.format(Date())
        val list=currentDate.split("-")
        val calender = binding.calendarView
        calender.setOnDateChangeListener { _, year, month, dayOfMonth ->
            val selectedDate = "$dayOfMonth-${month + 1}-$year"
            Toast.makeText(this, "Selected Date: $selectedDate", Toast.LENGTH_SHORT).show()
        }
        viewModel= ViewModelProvider(this).get(workviewModel::class.java)
        viewModel.read.observe(this) { data ->
            data.forEach{ element->
                val monthYear = element.month
                val l=monthYear.split("-")
                if(l==list){
                    if (l[1] == "01") {binding.TEXT.text="January"
                        binding.monthcal.text=element.Tc.toString()}
                    if (l[1].toInt() == 2){binding.TEXT.text="Febuary"
                        binding.monthcal.text=element.Tc.toString()}
                    if (l[1].toInt() == 3){binding.TEXT.text="March"
                        binding.monthcal.text=element.Tc.toString()}
                    if (l[1].toInt() == 4){binding.TEXT.text="April"
                        binding.monthcal.text=element.Tc.toString()}
                    if (l[1].toInt() == 5){binding.TEXT.text="May"
                        binding.monthcal.text=element.Tc.toString()}
                    if (l[1].toInt() == 6){binding.TEXT.text="June"
                        binding.monthcal.text=element.Tc.toString()}
                    if (l[1].toInt() == 7){binding.TEXT.text="July"
                        binding.monthcal.text=element.Tc.toString()}
                    if (l[1].toInt() == 8){binding.TEXT.text="August"
                        binding.monthcal.text=element.Tc.toString()}
                    if (l[1].toInt() == 9){binding.TEXT.text="September"
                        binding.monthcal.text=element.Tc.toString()}
                    if (l[1].toInt() == 10){binding.TEXT.text="October"
                        binding.monthcal.text=element.Tc.toString()}
                    if (l[1].toInt() == 11){binding.TEXT.text="November"
                        binding.monthcal.text=element.Tc.toString()}
                    if (l[1].toInt() == 12){binding.TEXT.text="December"
                        binding.monthcal.text=element.Tc.toString()}
                }
            }
            binding.button.setOnClickListener{
                val intent = Intent(this, goalsetting::class.java)
                startActivity(intent)
            }

        }
    }
}
