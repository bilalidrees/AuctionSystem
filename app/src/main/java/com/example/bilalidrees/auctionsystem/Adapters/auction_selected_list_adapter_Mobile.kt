package com.example.bilalidrees.auctionsystem.Adapters

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.example.bilalidrees.auctionsystem.Mobile_Phone.Mobile_Phone_Data
import com.example.bilalidrees.auctionsystem.R

class auction_selected_list_adapter_Mobile (context: Context, resource: Int, var items: ArrayList<Mobile_Phone_Data>, private var lambda:(Int, Mobile_Phone_Data)->Unit) : RecyclerView.Adapter<auction_selected_list_adapter_Mobile.ViewHolder>(){


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder
            =ViewHolder( LayoutInflater.from(parent.context).inflate(R.layout.auction_selected_items, parent, false))



    override fun getItemCount(): Int {
        return if (this.items!= null) {
            this.items.size

        } else {
            0
        }
    }



    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        var user=items[position]

        holder.ownername!!.setText(user.Ownername)
        holder.name!!.setText(user.Productname)
        holder.status!!.setText(user.Product_Status)
        holder.dates!!.setText(user.Date)
        holder.sttime!!.setText(user.Start_time)
        holder.ettime!!.setText(user.End_time)


        holder.itemView.setOnClickListener{
        lambda(position,user)
}
    }









    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        // Holds the TextView that will add each animal to

        var ownername : TextView? =view.findViewById(R.id.Owner_name)
        var name : TextView? =view.findViewById(R.id.nameTextView)
        var status: TextView? =view.findViewById(R.id.pro_status)
        var dates: TextView? =view.findViewById(R.id.pro_date)
        var sttime: TextView? =view.findViewById(R.id.pro_starttime)
        var ettime: TextView? =view.findViewById(R.id.pro_endtime)


    }
}
