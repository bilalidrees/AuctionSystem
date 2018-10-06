package com.example.bilalidrees.auctionsystem.Response

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.example.bilalidrees.auctionsystem.Adapters.Response_list_adapaters
import com.example.bilalidrees.auctionsystem.Chat.chat_list
import com.example.bilalidrees.auctionsystem.Mobile_Phone.Mobile_Phone_Data
import com.example.bilalidrees.auctionsystem.R
import com.example.bilalidrees.auctionsystem.User.User
import com.example.bilalidrees.auctionsystem.refrigerator.Refrigerator_Data
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import java.util.ArrayList

class Response_bidder_status : AppCompatActivity() {


    private lateinit var  mMessageRecyclerview: RecyclerView

    private lateinit var  mMessageAdapter: RecyclerView.Adapter<*>


    private lateinit var layoutManager: RecyclerView.LayoutManager

    private lateinit var  mMessageEditText: EditText
    private lateinit var  mSendButton: Button

    private lateinit var  mUsername:String

    private lateinit var  mFirebaseDatabase: FirebaseDatabase
    private lateinit var  mMessagesDatabaseReference: DatabaseReference


    private lateinit var maucdebref: DatabaseReference

    private  var  mChildEventListener: ChildEventListener?=null
    private  var  mEventListener: ChildEventListener?=null



    private lateinit var  mFirebaseAuth: FirebaseAuth
    private var  mAuthStateListener: FirebaseAuth.AuthStateListener?= null

    var responselist = ArrayList<Response_Data>()
    var moblist = ArrayList<Mobile_Phone_Data>()
    var refglist = ArrayList<Refrigerator_Data>()
    var winner:Response_Data?=null

    private lateinit var refrence:String
    private lateinit var list:String
    private lateinit var activity:String

    //private lateinit var mobile_user: Mobile_Phone_Data
    //private lateinit var refg_user: Refrigerator_Data

    private lateinit var editText_winner: TextView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_response_bidder_status)

        mFirebaseDatabase = FirebaseDatabase.getInstance()
        mFirebaseAuth = FirebaseAuth.getInstance()

        refrence=intent.extras.getString("refrence")

        mMessageRecyclerview = findViewById(R.id.recyclerview) as RecyclerView
        layoutManager = LinearLayoutManager(this)
        mMessageRecyclerview.layoutManager=layoutManager

        mMessageAdapter= Response_list_adapaters(this, R.layout.response_items,responselist, { position, user ->

            Toast.makeText(this@Response_bidder_status, "you  clicked " + position, Toast.LENGTH_SHORT).show()

            check(user)

        })


        mMessageRecyclerview .adapter=mMessageAdapter
        mAuthStateListener=FirebaseAuth.AuthStateListener {

            var  user=it.currentUser

            if(user!=null){

                mMessagesDatabaseReference = mFirebaseDatabase.reference.child("RESPONSES").child(mFirebaseAuth.uid!!)
                Log.v("REFRENCE",mMessagesDatabaseReference.toString())


                attachDatabaseReadListener()

            }
        }

    }

    private fun check(user:Response_Data){


        if (refrence == "Mobile_Phones") {

            maucdebref =mFirebaseDatabase!!.getReference().child("Mobile_Phones").child(user.Owneruid!!)
            Log.v("REFRENCE",maucdebref.toString())
            databaselist(user)

        } else if (refrence == "Refrigerator") {

            maucdebref =mFirebaseDatabase!!.getReference().child("Refrigerator").child(user.Owneruid!!)
            Log.v("REFRENCE",maucdebref.toString())
            databaselist(user)

        }


    }
    private fun databaselist(user:Response_Data){

        if (mEventListener == null) {

            mEventListener = object : ChildEventListener {
                override fun onChildAdded(dataSnapshot: DataSnapshot, s: String?) {


                    if (refrence == "Mobile_Phones") {

                        val fm = dataSnapshot.getValue<Mobile_Phone_Data>(Mobile_Phone_Data::class.java)
                        if(fm!!.Productid.equals(user.Productid)){

                            if(fm.Winner_uid.equals(user.senderuid)){

                                Toast.makeText(this@Response_bidder_status, "Congrulation you won the bid " , Toast.LENGTH_SHORT).show()
                                new(user)

                            }
                            else{
                                Toast.makeText(this@Response_bidder_status, "Sorry  you  did not win" , Toast.LENGTH_SHORT).show()
                            }
                        }
                    }
                    else if (refrence == "Refrigerator") {

                        val fm = dataSnapshot.getValue<Refrigerator_Data>(Refrigerator_Data::class.java)
                        if(fm!!.Productid.equals(user.Productid)){

                            if(fm.Winner_uid.equals(user.senderuid)){

                                Toast.makeText(this@Response_bidder_status, "Congrulation you won the bid " , Toast.LENGTH_SHORT).show()
                                new(user)

                            }
                            else{
                                Toast.makeText(this@Response_bidder_status, "Sorry  you  did not win" , Toast.LENGTH_SHORT).show()
                            }
                        }
                    }




                }

                override fun onChildChanged(dataSnapshot: DataSnapshot, s: String?) {

                }

                override fun onChildRemoved(dataSnapshot: DataSnapshot) {

                }

                override fun onChildMoved(dataSnapshot: DataSnapshot, s: String?) {

                }

                override fun onCancelled(databaseError: DatabaseError) {

                }
            }
            maucdebref.addChildEventListener(mEventListener!!)
        }

    }

    private fun  new(user:Response_Data){

        val intent = Intent(this, chat_list::class.java)
//            intent.putExtra("postion",position)
        intent.putExtra("refrence",refrence)
        // intent.putExtra("list",list)
        intent.putExtra("object",user)
        intent.putExtra("activity","bidder")
        startActivity(intent)
    }
    override fun onPause() {

        super.onPause()
        if (mAuthStateListener != null) {
            mFirebaseAuth.removeAuthStateListener(mAuthStateListener!!)

        }
        detachDatabaseReadListener()
        responselist.clear()

    }


    override fun onResume() {

        super.onResume()

        mFirebaseAuth.addAuthStateListener(mAuthStateListener!!)
    }


    private fun attachDatabaseReadListener() {
        if (mChildEventListener == null) {

            mChildEventListener = object : ChildEventListener {
                override fun onChildAdded(dataSnapshot: DataSnapshot, s: String?) {

                    val fm = dataSnapshot.getValue<Response_Data>(Response_Data::class.java)
                    responselist.add(fm!!)
                    mMessageAdapter.notifyDataSetChanged()
                }

                override fun onChildChanged(dataSnapshot: DataSnapshot, s: String?) {

                }

                override fun onChildRemoved(dataSnapshot: DataSnapshot) {

                }

                override fun onChildMoved(dataSnapshot: DataSnapshot, s: String?) {

                }

                override fun onCancelled(databaseError: DatabaseError) {

                }
            }
            mMessagesDatabaseReference.addChildEventListener(mChildEventListener!!)
        }

    }


    private fun detachDatabaseReadListener() {

        if (mChildEventListener != null) {
            mMessagesDatabaseReference.removeEventListener(mChildEventListener!!)
            mChildEventListener = null
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



}
