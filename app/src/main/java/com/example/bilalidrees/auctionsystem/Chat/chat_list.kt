package com.example.bilalidrees.auctionsystem.Chat

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.example.bilalidrees.auctionsystem.Adapters.chat_list_adapter
import com.example.bilalidrees.auctionsystem.Mobile_Phone.Mobile_Phone_Data
import com.example.bilalidrees.auctionsystem.R
import com.example.bilalidrees.auctionsystem.Response.Response_Data
import com.example.bilalidrees.auctionsystem.User.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import java.util.ArrayList

class chat_list : AppCompatActivity() {

    private lateinit var  mMessageRecyclerview: RecyclerView

    private lateinit var  mMessageAdapter: RecyclerView.Adapter<*>

    internal lateinit var layoutManager: RecyclerView.LayoutManager

    private lateinit var  mMessageEditText: EditText
    private lateinit var  mSendButton: Button

    private lateinit var  mUsername:String

    private lateinit var  mFirebaseDatabase: FirebaseDatabase
    private lateinit var  mMessagesDatabaseReference: DatabaseReference
    private  var  mChildEventListener: ChildEventListener?=null
    private  var  muserEventListener: ChildEventListener?=null

    private lateinit var mUserDatabaseReference: DatabaseReference
    private lateinit var  mFirebaseAuth: FirebaseAuth
    private lateinit var  mAuthStateListener: FirebaseAuth.AuthStateListener
    var msglist = ArrayList<chat_data>()
    var winner: Response_Data?=null

    private  var NNAME:String?=null
    private lateinit var position:String
    private lateinit var getuname:String
    private lateinit var getuemail:String
    private lateinit var getuid:String

    private lateinit var refrence:String
    private lateinit var list:String
    private lateinit var activity:String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat_list)

        mFirebaseDatabase = FirebaseDatabase.getInstance()
        mFirebaseAuth = FirebaseAuth.getInstance()

        refrence=intent.extras.getString("refrence")
        //// list=intent.extras.getString("list")
        winner = intent.extras.get("object") as Response_Data
        activity=intent.extras.getString("activity")
        //position= intent.extras.getString("postion")




        mMessageRecyclerview = findViewById(R.id.recyclerview) as RecyclerView
        layoutManager = LinearLayoutManager(this)
        mMessageRecyclerview.layoutManager=layoutManager


        mMessageEditText=findViewById(R.id.messageEditText) as EditText
        mSendButton = findViewById(R.id.sendButton) as Button



        //  var msg : List<Message>=Array
        mMessageAdapter=chat_list_adapter(this,R.layout.chat_list,msglist,{
            Toast.makeText(this@chat_list, "you  clicked "+it, Toast.LENGTH_SHORT).show()

        })

        mMessageRecyclerview.adapter=mMessageAdapter


        mSendButton.setOnClickListener(View.OnClickListener {

            if(activity=="bidder"){

                var   mymsg=chat_data(winner!!.sendername,mMessageEditText.text.toString(), winner!!.senderuid)

                mMessagesDatabaseReference.push().setValue(mymsg)
                mMessageEditText.setText("")
                Toast.makeText(this@chat_list, "data  pushed!", Toast.LENGTH_SHORT).show()


            }

            else if(activity=="auctioner"){


                var   mymsg=chat_data(winner!!.Ownername,mMessageEditText.text.toString(), winner!!.Owneruid)

                mMessagesDatabaseReference.push().setValue(mymsg)
                mMessageEditText.setText("")
                Toast.makeText(this@chat_list, "data  pushed!", Toast.LENGTH_SHORT).show()

            }




        })

        mAuthStateListener= FirebaseAuth.AuthStateListener {
            var  user=it.currentUser

            if(user!=null){


    mMessagesDatabaseReference = mFirebaseDatabase.reference.child("Chat").child(winner!!.Owneruid+"_"+winner!!.senderuid)
       Log.v("RRRREEEFRRREMNCE",mMessagesDatabaseReference.toString())
                attachDatabaseReadListener()


            }
        }
    }

    private fun attachDatabaseReadListener() {

        if (mChildEventListener == null) {

            mChildEventListener = object : ChildEventListener {
                override fun onChildAdded(dataSnapshot: DataSnapshot, s: String?) {
                    val fm = dataSnapshot.getValue<chat_data>(chat_data::class.java)

                    msglist.add(fm!!)
                    mMessageAdapter.notifyDataSetChanged()


                }

                override fun onChildChanged(dataSnapshot: DataSnapshot, s: String?) {

                }

                override fun onChildRemoved(dataSnapshot: DataSnapshot) {
                    val fm = dataSnapshot.getValue<chat_data>(chat_data::class.java)

                    msglist.remove(fm)
                    mMessageAdapter.notifyDataSetChanged()


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

    override fun onPause() {

        super.onPause()
        if (mAuthStateListener != null) {
            mFirebaseAuth.removeAuthStateListener(mAuthStateListener)

        }
        detachDatabaseReadListener()
        msglist.clear()

    }


    override fun onResume() {

        super.onResume()
        mFirebaseAuth.addAuthStateListener(mAuthStateListener)
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
                finish()

                return true
            }

            else -> return super.onOptionsItemSelected(item)
        }
    }



}
