package com.example.bilalidrees.auctionsystem.Selection_Panel

import android.app.AlertDialog
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.*
import com.example.bilalidrees.auctionsystem.Homepage.HomePage
import com.example.bilalidrees.auctionsystem.ListItems.Auctioner_selected_itemslist
import com.example.bilalidrees.auctionsystem.Mobile_Phone.Mobile_Phone
import com.example.bilalidrees.auctionsystem.R
import com.example.bilalidrees.auctionsystem.User.User
import com.example.bilalidrees.auctionsystem.refrigerator.Refrigerator
import com.google.firebase.auth.FirebaseAuth

class Auctioner_Selection_panel : AppCompatActivity(), View.OnClickListener  {

    private lateinit var radiogrp:RadioGroup
    private lateinit var radiobutton: RadioButton
    private  var catagoryRv: RelativeLayout?=null
    private  var POINT: String?=null


private lateinit var selectedoption:TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_selection_panel)

        radiogrp=findViewById(R.id.radiogroup)

        selectedoption=findViewById(R.id.selected_option)
        catagoryRv=findViewById(R.id.categoryrv)
        findViewById<Button>(R.id.placeorder).setOnClickListener(this)
        findViewById<Button>(R.id.checkresponse).setOnClickListener(this)
        findViewById<Button>(R.id.proceed).setOnClickListener(this)











    }


    override fun onClick(view: View?) {

        when (view!!.getId()) {
            R.id.placeorder -> {
                catagoryRv!!.visibility=View.VISIBLE
                POINT="order"
                selectedoption.setText("you  selected  order")

            }

            R.id.checkresponse -> {
                catagoryRv!!.visibility=View.VISIBLE
                POINT="response"
                selectedoption.setText("you  selected  response")
            }

            R.id.proceed->{


                if (radiogrp.checkedRadioButtonId == -1)
                {
                    // no radio buttons are checked
                    Toast.makeText(getApplicationContext(), "Select any  Catagory", Toast.LENGTH_SHORT).show();
                }
                else{

                    var radioId= radiogrp.checkedRadioButtonId
                    radiobutton= findViewById(radioId)

                    if (POINT == "order") {

                        if (radiobutton.text.toString() == "Mobile Phones") {

                            val intent = Intent(this, Mobile_Phone::class.java)
                            startActivity(intent)


                        } else if (radiobutton.text.toString() == "Refrigerator") {
                            val intent = Intent(this, Refrigerator::class.java)

                            startActivity(intent)


                        }

                    } else if (POINT == "response") {
                        if (radiobutton.text.toString() == "Mobile Phones") {

                            val intent = Intent(this, Auctioner_ResonseSelection_panel::class.java)
                            intent.putExtra("refrence","Mobile_Phones")
                            startActivity(intent)


                        } else if (radiobutton.text.toString() == "Refrigerator") {
                            val intent = Intent(this, Auctioner_ResonseSelection_panel::class.java)
                            intent.putExtra("refrence","Refrigerator")
                            startActivity(intent)

                        }

                    }



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
