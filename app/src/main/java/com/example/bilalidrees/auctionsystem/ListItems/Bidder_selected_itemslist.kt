package com.example.bilalidrees.auctionsystem.ListItems

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
import android.widget.Toast
import com.example.bilalidrees.auctionsystem.Adapters.auction_selected_list_adapter_Mobile
import com.example.bilalidrees.auctionsystem.Adapters.auction_selected_list_adapter_Refrigerator
import com.example.bilalidrees.auctionsystem.Homepage.HomePage
import com.example.bilalidrees.auctionsystem.Mobile_Phone.Mobile_Phone_Data
import com.example.bilalidrees.auctionsystem.R
import com.example.bilalidrees.auctionsystem.Selection_Panel.Bidder_item_selection_panel
import com.example.bilalidrees.auctionsystem.User.User
import com.example.bilalidrees.auctionsystem.refrigerator.Refrigerator
import com.example.bilalidrees.auctionsystem.refrigerator.Refrigerator_Data
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import java.text.SimpleDateFormat
import java.util.*
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter


class Bidder_selected_itemslist : AppCompatActivity() {



    private lateinit var  mMessageRecyclerview: RecyclerView

    private lateinit var  mMessageAdapter: RecyclerView.Adapter<*>


    private lateinit var layoutManager: RecyclerView.LayoutManager

    private lateinit var  mMessageEditText: EditText
    private lateinit var  mSendButton: Button

    private lateinit var  mUsername:String

    private lateinit var  mFirebaseDatabase: FirebaseDatabase
    private lateinit var  mMessagesDatabaseReference: DatabaseReference
    private  var  mChildEventListener: ChildEventListener?=null

    private lateinit var  mFirebaseAuth: FirebaseAuth
    private var  mAuthStateListener: FirebaseAuth.AuthStateListener?= null

    var userlist = ArrayList<Mobile_Phone_Data>()
    var  seconduser=ArrayList<Refrigerator_Data>()

    private lateinit var refrence:String
    private lateinit var list:String


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bidder_selected_items)

        mFirebaseDatabase = FirebaseDatabase.getInstance()
        mFirebaseAuth = FirebaseAuth.getInstance()


        refrence=intent.extras.getString("refrence")
        list=intent.extras.getString("list")

        Log.v("REFRENCE",refrence)
        Log.v("LIST",list)



        mMessageRecyclerview = findViewById(R.id.recyclerview) as RecyclerView
        layoutManager = LinearLayoutManager(this)
        mMessageRecyclerview.layoutManager=layoutManager


        if(refrence=="Mobile_Phones"){

            mMessageAdapter= auction_selected_list_adapter_Mobile(this, R.layout.auction_selected_items,userlist,{ position, user->

                Toast.makeText(this@Bidder_selected_itemslist, "you  clicked "+position, Toast.LENGTH_SHORT).show()

                val intent = Intent(this, Bidder_item_selection_panel::class.java)

                intent.putExtra("refrence",refrence)
                intent.putExtra("list",list)
                intent.putExtra("object",user)
                startActivity(intent)

            })


        }
        else if(refrence=="Refrigerator"){

            mMessageAdapter= auction_selected_list_adapter_Refrigerator(this, R.layout.auction_selected_items,seconduser,{ position, user->

                Toast.makeText(this@Bidder_selected_itemslist, "you  clicked "+position, Toast.LENGTH_SHORT).show()

                val intent = Intent(this, Bidder_item_selection_panel::class.java)

                intent.putExtra("refrence",refrence)
                intent.putExtra("list",list)
                intent.putExtra("object",user)
                startActivity(intent)

            })
        }


        mMessageRecyclerview .adapter=mMessageAdapter

        mAuthStateListener=FirebaseAuth.AuthStateListener {

            var  user=it.currentUser

            if(user!=null){

                mMessagesDatabaseReference = mFirebaseDatabase.reference.child(refrence)
                Log.v("REFRENCE",mMessagesDatabaseReference.toString())



                attachDatabaseReadListener()

            }
        }


    }


    override fun onPause() {

        super.onPause()
        if (mAuthStateListener != null) {
            mFirebaseAuth.removeAuthStateListener(mAuthStateListener!!)

        }
        detachDatabaseReadListener()
        seconduser.clear()
        userlist.clear()
    }


    override fun onResume() {

        super.onResume()

        mFirebaseAuth.addAuthStateListener(mAuthStateListener!!)
    }



    private fun attachDatabaseReadListener() {
        if (mChildEventListener == null) {

            mChildEventListener = object : ChildEventListener {
                override fun onChildAdded(dataSnapshot: DataSnapshot, s: String?) {

                    if(refrence=="Mobile_Phones"){

                            val fm = dataSnapshot.getValue<Mobile_Phone_Data>(Mobile_Phone_Data::class.java)

                            if(list=="live"){

                                val df = SimpleDateFormat("k:m")
                                val time = df.format(Calendar.getInstance().time)

                                val smf = SimpleDateFormat("M/d/yyyy")
                                val date=Date()
                                val currentdate=smf.format(date)
                                val userdate = smf.parse(fm!!.Date.toString())


                                val s=fm!!.Start_time!!.toString().compareTo(time.toString())
                                val e=fm!!.End_time!!.toString().compareTo(time.toString())

                                val res=fm.Date.toString().compareTo(currentdate.toString())
                                Log.v("DATE",res.toString())

                                if(!mFirebaseAuth.uid.equals(fm.Owneruid)){

                                    if(res==0){

                                        if(s<0 && e>0){

                                            userlist.add(fm!!)
                                            mMessageAdapter.notifyDataSetChanged()

                                        }
                                    }
                                }

                            }

                            else if(list=="upcoming"){

                                val df = SimpleDateFormat("k:m")
                                val time = df.format(Calendar.getInstance().time)

                                val smf = SimpleDateFormat("M/d/yyyy")
                                val date=Date()
                                val userdate = smf.parse(fm!!.Date.toString())
                                val currentdate=smf.format(date)

                  val res=fm.Date.toString().compareTo(currentdate.toString())



                   val  s=fm!!.Start_time!!.toString().compareTo(time.toString())

                                if(!mFirebaseAuth.uid.equals(fm.Owneruid)) {
                                    if (res == 0) {
                                        if (s > 0) {
                                            userlist.add(fm!!)
                                            mMessageAdapter.notifyDataSetChanged()
                                        }
                                    } else if (userdate.after(date)) {
                                        userlist.add(fm!!)
                                        mMessageAdapter.notifyDataSetChanged()
                                    }
                                }
                            }

                        }

                    else if (refrence=="Refrigerator"){
                            val fm = dataSnapshot.getValue<Refrigerator_Data>(Refrigerator_Data::class.java)

                            if(list=="live"){

                                val df = SimpleDateFormat("k:m")
                                val time = df.format(Calendar.getInstance().time)
                                val smf = SimpleDateFormat("M/d/yyyy")
                                val date=Date()
                                val currentdate=smf.format(date)

                                val s=fm!!.Start_time!!.compareTo(time)
                                val e=fm!!.End_time!!.compareTo(time)
                                val res=fm.Date.toString().compareTo(currentdate.toString())

                          if(!mFirebaseAuth.uid.equals(fm.Owneruid)){
                                if(res==0){
                                    if(s<0 && e>0){
                                        seconduser.add(fm!!)
                                        mMessageAdapter.notifyDataSetChanged()
                                    }
                                }
                          }

                            }
                            else if(list=="upcoming"){

                                val df = SimpleDateFormat("k:m")
                                val time = df.format(Calendar.getInstance().time)

                                val smf = SimpleDateFormat("M/d/yyyy")
                                val date=Date()
                                val userdate = smf.parse(fm!!.Date)

                                val currentdate=smf.format(date)
                                val res=fm.Date.toString().compareTo(currentdate.toString())

                                val  s=fm!!.Start_time!!.compareTo(time)


                                if(!mFirebaseAuth.uid.equals(fm.Owneruid)) {
                                    if (res == 0) {
                                        if (s > 0) {
                                            seconduser.add(fm!!)
                                            mMessageAdapter.notifyDataSetChanged()
                                        }
                                    } else if (userdate.after(date)) {
                                        seconduser.add(fm!!)
                                        mMessageAdapter.notifyDataSetChanged()
                                    }
                                }
                            }

                        }





                }

                override fun onChildChanged(dataSnapshot: DataSnapshot, s: String?) {
                    if (refrence == "Mobile_Phones") {
                        val fm = dataSnapshot.getValue<Mobile_Phone_Data>(Mobile_Phone_Data::class.java)

                        val i = userlist.indexOf(fm)
                        Toast.makeText(this@Bidder_selected_itemslist, i.toString() +
                                " ", Toast.LENGTH_SHORT).show()

                        if(i>-1) {

                            userlist[i]= fm!!
                            Log.v("VALUE",i.toString())

                        }

                        mMessageAdapter.notifyDataSetChanged()


                    } else if (refrence == "Refrigerator") {

                        val fm = dataSnapshot.getValue<Refrigerator_Data>(Refrigerator_Data::class.java)
                        seconduser.add(fm!!)
                        mMessageAdapter.notifyDataSetChanged()

                    }
                }

                override fun onChildRemoved(dataSnapshot: DataSnapshot) {


                    if(refrence=="Mobile_Phones"){
                        val fm = dataSnapshot.getValue<Mobile_Phone_Data>(Mobile_Phone_Data::class.java)
                        userlist.remove(fm!!)

                    }

                    else if(refrence=="Refrigerator"){

                        val fm = dataSnapshot.getValue<Refrigerator_Data>(Refrigerator_Data::class.java)

                        seconduser.remove(fm!!)
                    }

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
