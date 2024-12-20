package com.example.workoutpall.ui.home
import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.workoutpall.R
import com.google.android.material.imageview.ShapeableImageView

class HomeWorkAdapter(var arraylist: ArrayList<homedata>, var context : Activity):
    RecyclerView.Adapter<HomeWorkAdapter.ViewHolder>()  {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeWorkAdapter.ViewHolder {
        val intemView =LayoutInflater.from(parent.context).inflate(R.layout.items,parent,false)
        return ViewHolder(intemView)

    }

    override fun getItemCount(): Int {
        return arraylist.size

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        var currentitem=arraylist[position]
        holder.heading.text=currentitem.heading

    }
    class ViewHolder(viewitem : View): RecyclerView.ViewHolder(viewitem){
        val heading = viewitem.findViewById<TextView>(R.id.textt)

    }
}