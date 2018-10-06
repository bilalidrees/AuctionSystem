package com.example.bilalidrees.auctionsystem.Selection_Panel

import android.app.AlertDialog
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.*
import com.example.bilalidrees.auctionsystem.Homepage.HomePage
import com.example.bilalidrees.auctionsystem.ListItems.Bidder_selected_itemslist
import com.example.bilalidrees.auctionsystem.Mobile_Phone.Mobile_Phone
import com.example.bilalidrees.auctionsystem.R
import com.example.bilalidrees.auctionsystem.Response.Response_bidder_status
import com.example.bilalidrees.auctionsystem.User.User
import com.example.bilalidrees.auctionsystem.refrigerator.Refrigerator
import com.google.firebase.auth.FirebaseAuth

class Bidder_Selection_Panel : AppCompatActivity(), View.OnClickListener {


    private lateinit var radiogrp: RadioGroup
    private lateinit var radiobutton: RadioButton
    private  var catagoryRv: RelativeLayout?=null
    private  var POINT: String?=null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bidder__selection__panel)

        radiogrp=findViewById(R.id.radiogroup)



        catagoryRv=findViewById(R.id.selectionrL)


        findViewById<Button>(R.id.proceed).setOnClickListener(this)

        findViewById<Button>(R.id.live_auction).setOnClickListener(this)
        findViewById<Button>(R.id.upcoming_auction).setOnClickListener(this)
        findViewById<Button>(R.id.check_winning_status).setOnClickListener(this)


    }

    override fun onClick(view: View?) {

        when (view!!.getId()) {
            R.id.live_auction -> {

                if(radiobutton.text.toString()=="Mobile Phones"){

                    val intent = Intent(this, Bidder_selected_itemslist::class.java)
                    intent.putExtra("refrence","Mobile_Phones")
                    intent.putExtra("list","live")
                    startActivity(intent)
                }
                else if(radiobutton.text.toString()=="Refrigerator"){
                    val intent = Intent(this, Bidder_selected_itemslist::class.java)
                    intent.putExtra("refrence","Refrigerator")
                    intent.putExtra("list","live")
                    startActivity(intent)


                }

            }

            R.id.upcoming_auction -> {
                if(radiobutton.text.toString()=="Mobile Phones"){

                    val intent = Intent(this, Bidder_selected_itemslist::class.java)
                    intent.putExtra("refrence","Mobile_Phones")
                    intent.putExtra("list","upcoming")
                    startActivity(intent)
                }
                else if(radiobutton.text.toString()=="Refrigerator"){
                    val intent = Intent(this, Bidder_selected_itemslist::class.java)
                    intent.putExtra("refrence","Refrigerator")
                    intent.putExtra("list","upcoming")
                    startActivity(intent)


                }


            }

            R.id.proceed->{


                if (radiogrp.checkedRadioButtonId == -1)
                {
                    // no radio buttons are checked
                    Toast.makeText(getApplicationContext(), "Select any  Catagory", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    // one of the radio buttons is checked
                    var radioId= radiogrp.checkedRadioButtonId
                    radiobutton=findViewById(radioId)

                    catagoryRv!!.visibility = View.VISIBLE
                }

            }

            R.id.check_winning_status->{


                if(radiobutton.text.toString()=="Mobile Phones"){

                    val intent = Intent(this, Response_bidder_status::class.java)
                    intent.putExtra("refrence","Mobile_Phones")
                    intent.putExtra("list","live")
                    startActivity(intent)
                }
                else if(radiobutton.text.toString()=="Refrigerator"){
                    val intent = Intent(this, Response_bidder_status::class.java)
                    intent.putExtra("refrence","Refrigerator")
                    intent.putExtra("list","live")
                    startActivity(intent)


                }
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.menu_welcome, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.Signout -> {
                FirebaseAuth.getInstance().signOut()
                startActivity(Intent(this, User::class.java))

                return true
            }

            else -> return super.onOptionsItemSelected(item)
        }
    }

    override fun onBackPressed() {
        val intent = Intent(this, HomePage::class.java)
        intent.putExtra("activity","res")
        startActivity(intent)
        this.finish()


    }


}
