package com.example.bilalidrees.auctionsystem.Selection_Panel

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.Toast
import com.example.bilalidrees.auctionsystem.ListItems.Bidder_selected_itemslist
import com.example.bilalidrees.auctionsystem.Mobile_Phone.Mobile_Phone_Data
import com.example.bilalidrees.auctionsystem.Place_bid.Place_bidd
import com.example.bilalidrees.auctionsystem.Product_discription.Product_discription
import com.example.bilalidrees.auctionsystem.R
import com.example.bilalidrees.auctionsystem.Response.Response
import com.example.bilalidrees.auctionsystem.refrigerator.Refrigerator_Data
import com.squareup.picasso.Downloader

class Bidder_item_selection_panel : AppCompatActivity(), View.OnClickListener {


    private lateinit var refrence:String
    private lateinit var list:String

    private lateinit var mobile_user:Mobile_Phone_Data
    private lateinit var refg_user:Refrigerator_Data

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bidder_item_selection_panel)

        refrence=intent.extras.getString("refrence")
        list=intent.extras.getString("list")

        if(refrence=="Mobile_Phones"){

            mobile_user=intent.extras.get("object") as Mobile_Phone_Data
        }
        else  if(refrence=="Refrigerator"){
            refg_user=intent.extras.get("object") as Refrigerator_Data

        }

        findViewById<Button>(R.id.product_discription).setOnClickListener(this)

        findViewById<Button>(R.id.place_bid).setOnClickListener(this)
        findViewById<Button>(R.id.other_response).setOnClickListener(this)

    }


    override fun onClick(view: View?) {
        when (view!!.getId()) {
            R.id.product_discription -> {

                val intent = Intent(this,Product_discription::class.java)

                intent.putExtra("refrence",refrence)
                intent.putExtra("list",list)

                if(refrence=="Mobile_Phones"){
                    intent.putExtra("object",mobile_user)
                }
                else  if(refrence=="Refrigerator"){
                    intent.putExtra("object",refg_user)
                }
                //user will be pass
                startActivity(intent)


            }

            R.id.place_bid -> {

                val intent = Intent(this,Place_bidd::class.java)

                intent.putExtra("refrence",refrence)
                intent.putExtra("list",list)

                if(refrence=="Mobile_Phones"){
                    intent.putExtra("object",mobile_user)
                }
                else  if(refrence=="Refrigerator"){
                    intent.putExtra("object",refg_user)
                }
                //user will be pass
                startActivity(intent)


            }

            R.id.other_response-> {

                val intent = Intent(this,Response::class.java)

                intent.putExtra("refrence",refrence)
                intent.putExtra("list",list)
                intent.putExtra("activity","bidder")

                if(refrence=="Mobile_Phones"){
                    intent.putExtra("object",mobile_user)
                }
                else  if(refrence=="Refrigerator"){
                    intent.putExtra("object",refg_user)
                }
                //user will be pass
                startActivity(intent)
            }

        }

    }
}