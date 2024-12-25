package com.example.workoutpall.home
import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.workoutpall.R
class HomeWorkAdapter(
    private var arraylist: ArrayList<homedata>,
    var context: Activity ): RecyclerView.Adapter<HomeWorkAdapter.ViewHolder>()  {

        private lateinit var myListener: onItemClickListener
        interface onItemClickListener{
            fun onItemClick(position:Int)

        }
    fun setItemClickListener(Listener: onItemClickListener){
        myListener=Listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView =LayoutInflater.from(parent.context).inflate(R.layout.items,parent,false)
        return ViewHolder(itemView,myListener)

    }

    override fun getItemCount(): Int {
        return arraylist.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val current=arraylist[position]
        holder.image.setImageResource(current.image)
        holder.heading.text=current.heading
    }

    class ViewHolder(view : View,Listener: onItemClickListener): RecyclerView.ViewHolder(view){
        val image:ImageView = view. findViewById(R.id.headingImage)
        val heading = view.findViewById<TextView>(R.id.textt)
        init{
            itemView.setOnClickListener{
                Listener.onItemClick(adapterPosition)
            }
        }
    }
    }