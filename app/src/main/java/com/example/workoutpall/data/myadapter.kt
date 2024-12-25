package com.example.workoutpall.data
import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.workoutpall.R

class myadapter(
    private var arrayList: List<workout>,
    var context:Activity): RecyclerView.Adapter<myadapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int):ViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.activity_data_item,parent,false)
        return ViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return arrayList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val current=arrayList[position]
        holder.itemView.id=current.id
        holder.workoutname.text=current.WorkOutName
        holder.Calories.text=current.Calories.toString()

    }
    class ViewHolder(view : View): RecyclerView.ViewHolder(view){
        //val timer=view.findViewById<TextView>(R.id.timer)
        val Calories=view.findViewById<TextView>(R.id.Calories)
        //val time=view.findViewById<TextView>(R.id.Date)
        val workoutname =view.findViewById<TextView>(R.id.Workoutid)
    }
    fun setdata(user: List<workout>){
        this.arrayList=user
        notifyDataSetChanged()
    }
}
