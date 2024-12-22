package com.example.workoutpall.home
import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.workoutpall.R

class HomeWorkAdapter(var arraylist: Array<String>, var context: Activity):

    RecyclerView.Adapter<HomeWorkAdapter.ViewHolder>()  {

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val intemView =LayoutInflater.from(parent.context).inflate(R.layout.items,parent,false)
        return ViewHolder(intemView)
    }

    override fun getItemCount(): Int {
        return arraylist.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        var currentitem=arraylist[position]
        holder.heading.text=arraylist[position]
    }

    class ViewHolder(viewitem : View): RecyclerView.ViewHolder(viewitem){
        val heading = viewitem.findViewById<TextView>(R.id.textt)
    }
}