package com.example.bilalidrees.auctionsystem.Homepage

import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import android.os.Binder
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.provider.ContactsContract
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.example.bilalidrees.auctionsystem.R
import com.example.bilalidrees.auctionsystem.Selection_Panel.Auctioner_Selection_panel
import com.example.bilalidrees.auctionsystem.Selection_Panel.Bidder_Selection_Panel
import com.example.bilalidrees.auctionsystem.Signup
import com.example.bilalidrees.auctionsystem.User.User
import com.google.firebase.auth.FirebaseAuth

class HomePage : AppCompatActivity(), View.OnClickListener {

    private lateinit var na:String
    private lateinit var mAuth: FirebaseAuth
    private lateinit var  mMessageEditText: TextView

    private lateinit var activity:String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home_page)

        mAuth = FirebaseAuth.getInstance()

        mMessageEditText=findViewById(R.id.selected_option)

        activity=intent.extras.getString("activity")

        if(activity=="user"){
            na=intent.extras.getString("name")
            mMessageEditText.setText(na)
        }
        else if(activity=="res"){

        }



        findViewById<ImageView>(R.id.auctioner).setOnClickListener(this)
        findViewById<ImageView>(R.id.bidder).setOnClickListener(this)



    }



    override fun onClick(view: View?) {
        when (view!!.getId()) {
            R.id.auctioner -> {

                val intent = Intent(this,Auctioner_Selection_panel::class.java)

                startActivityForResult(intent, 1)

            }

            R.id.bidder -> {
                val intent = Intent(this, Bidder_Selection_Panel::class.java)
                startActivityForResult (intent, 1)
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

        val a_builder = AlertDialog.Builder(this@HomePage)
        a_builder.setMessage("Do you want to Close this App !!!")
                .setCancelable(false)
                .setPositiveButton("Yes") { dialog, which -> finish() }
                .setNegativeButton("No") { dialog, which -> dialog.cancel() }
        val alert = a_builder.create()
        alert.setTitle("Alert !!!")
        alert.show()


    }


    override fun onResume() {

        super.onResume()
        mMessageEditText=findViewById(R.id.selected_option)
        na=intent.extras.getString("name")
        mMessageEditText.setText(na)

    }
    override fun onStart() {
        super.onStart()
        mMessageEditText=findViewById(R.id.selected_option)
        na=intent.extras.getString("name")
        mMessageEditText.setText(na)

    }

    }

