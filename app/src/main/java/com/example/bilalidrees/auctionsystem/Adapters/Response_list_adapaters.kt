package com.example.bilalidrees.auctionsystem.Adapters

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.example.bilalidrees.auctionsystem.Mobile_Phone.Mobile_Phone_Data
import com.example.bilalidrees.auctionsystem.R
import com.example.bilalidrees.auctionsystem.Response.Response
import com.example.bilalidrees.auctionsystem.Response.Response_Data

class Response_list_adapaters (context: Context, resource: Int, var items: ArrayList<Response_Data>, private var lambda:(Int, Response_Data)->Unit) : RecyclerView.Adapter<Response_list_adapaters.ViewHolder>(){


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder
            =ViewHolder( LayoutInflater.from(parent.context).inflate(R.layout.response_items, parent, false))



    override fun getItemCount(): Int {
        return if (this.items!= null) {
            this.items.size

        } else {
            0
        }
    }



    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        var user=items[position]

        holder.sendername!!.setText(user.sendername)
        holder.senderem!!.setText(user.senderemail)
        holder.senderph!!.setText(user.senderPhone)
        holder.senderbid!!.setText(user.bid)



        holder.itemView.setOnClickListener{
            lambda(position,user)
        }
    }









    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        // Holds the TextView that will add each animal to

        var sendername : TextView? =view.findViewById(R.id.sender_name)
        var senderem : TextView? =view.findViewById(R.id.sender_email)
        var senderph: TextView? =view.findViewById(R.id.sender_phone)
        var senderbid: TextView? =view.findViewById(R.id.sender_bid)



    }
}

