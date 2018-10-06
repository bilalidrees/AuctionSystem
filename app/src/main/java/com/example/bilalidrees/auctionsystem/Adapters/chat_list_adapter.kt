package com.example.bilalidrees.auctionsystem.Adapters

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.example.bilalidrees.auctionsystem.Chat.chat_data
import com.example.bilalidrees.auctionsystem.R


class chat_list_adapter(context: Context, resource: Int, var items: ArrayList<chat_data>, private var lambda:(Int)->Unit) : RecyclerView.Adapter<chat_list_adapter.ViewHolder>(){


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder
            =ViewHolder( LayoutInflater.from(parent.context).inflate(R.layout.chat_list, parent, false))



    override fun getItemCount(): Int {
        return if (this.items!= null) {
            this.items.size

        } else {
            0
        }
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        var user=items[position]


        holder.name!!.setText(user.name)
        holder.message!!.setText(user.message)

        holder.itemView.setOnClickListener{
            lambda(position)
        }




    }






    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        // Holds the TextView that will add each animal to

        var name : TextView? =view.findViewById(R.id.nameTextView)
        var message: TextView? =view.findViewById(R.id.messageTextView)


    }



}