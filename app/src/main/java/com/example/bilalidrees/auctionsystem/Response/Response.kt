    package com.example.bilalidrees.auctionsystem.Response

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.example.bilalidrees.auctionsystem.Adapters.Response_list_adapaters
import com.example.bilalidrees.auctionsystem.Adapters.auction_selected_list_adapter_Mobile
import com.example.bilalidrees.auctionsystem.Mobile_Phone.Mobile_Phone_Data
import com.example.bilalidrees.auctionsystem.Product_discription.Product_discription
import com.example.bilalidrees.auctionsystem.R
import com.example.bilalidrees.auctionsystem.Selection_Panel.Bidder_item_selection_panel
import com.example.bilalidrees.auctionsystem.User.User
import com.example.bilalidrees.auctionsystem.refrigerator.Refrigerator_Data
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import java.text.SimpleDateFormat
import java.util.*
import java.nio.file.Files.delete
import android.view.LayoutInflater
import java.io.Serializable
import com.example.bilalidrees.auctionsystem.Chat.chat_list


    class Response : AppCompatActivity(), View.OnClickListener  {


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

    private lateinit var  mFirebaseAuth: FirebaseAuth
    private var  mAuthStateListener: FirebaseAuth.AuthStateListener?= null

    var responselist = ArrayList<Response_Data>()
    var winner:Response_Data?=null

    private lateinit var refrence:String
    private lateinit var list:String
    private lateinit var activity:String

    private  var mobile_user: Mobile_Phone_Data?=null
    private  var refg_user: Refrigerator_Data?=null

    private lateinit var editText_winner: TextView




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_response)


        mFirebaseDatabase = FirebaseDatabase.getInstance()
        mFirebaseAuth = FirebaseAuth.getInstance()
        refrence=intent.extras.getString("refrence")
        list=intent.extras.getString("list")
        activity=intent.extras.getString("activity")


        if (refrence == "Mobile_Phones") {

            mobile_user = intent.extras.get("object") as Mobile_Phone_Data

        } else if (refrence == "Refrigerator") {
            refg_user = intent.extras.get("object") as Refrigerator_Data

        }

        editText_winner=findViewById(R.id.checkwinner)

        if(activity=="bidder"){
            editText_winner.visibility=View.INVISIBLE
        }

        else if(activity=="auctioner"){
            editText_winner.isEnabled=false
            editText_winner.visibility=View.VISIBLE

            checkforwinner()
        }








        mMessageRecyclerview = findViewById(R.id.recyclerview) as RecyclerView
        layoutManager = LinearLayoutManager(this)
        mMessageRecyclerview.layoutManager=layoutManager


        mMessageAdapter= Response_list_adapaters(this, R.layout.response_items,responselist, { position, user ->

            Toast.makeText(this@Response, "you  clicked " + position, Toast.LENGTH_SHORT).show()


        })


        mMessageRecyclerview .adapter=mMessageAdapter

        mAuthStateListener=FirebaseAuth.AuthStateListener {

            var  user=it.currentUser

            if(user!=null){

                mMessagesDatabaseReference = mFirebaseDatabase.reference.child("RESPONSES")
                Log.v("REFRENCE",mMessagesDatabaseReference.toString())


                attachDatabaseReadListener()

            }
        }


    }

    private fun checkforwinner(){

        val smf = SimpleDateFormat("M/d/yyyy")
        val date= Date()
        val currentdate=smf.format(date)
        val df = SimpleDateFormat("k:m")
        val time = df.format(Calendar.getInstance().time)

        if(refrence=="Mobile_Phones"){
            val res=mobile_user!!.Date.toString().compareTo(currentdate.toString())
            val  e=mobile_user!!.End_time!!.toString().compareTo(time.toString())

            if (res == 0) {

                if (e < 0) {

                    editText_winner.isEnabled=true
                    editText_winner.setOnClickListener(this)
                }
            }
            else{

                editText_winner.setText("Time left")
            }
        }
        else  if(refrence=="Refrigerator"){

            val res=refg_user!!.Date.toString().compareTo(currentdate.toString())
            val  e=refg_user!!.End_time!!.toString().compareTo(time.toString())
            if (res == 0) {
                if (e < 0) {
                    editText_winner.isEnabled=true
                    editText_winner.setOnClickListener(this)
                }
            }
            else{
                editText_winner.setText("Time left")
            }

        }
    }

    override fun onClick(view: View?) {

        when (view!!.getId()) {
            R.id.checkwinner -> {
                var bid=0

                for(check in responselist){

                 if(Integer.parseInt(check.bid)>bid){

                     bid=Integer.parseInt(check.bid)
                        winner=check
                    }
                }


                if (refrence == "Mobile_Phones") {

                    maucdebref =mFirebaseDatabase!!.getReference().child("Mobile_Phones")
                    maucdebref.child(mobile_user!!.Productid!!).child("Product_Status").setValue("SOLD")
                    maucdebref.child(mobile_user!!.Productid!!).child("Winner_uid").setValue(winner!!.senderuid)

                } else if (refrence == "Refrigerator") {

                    maucdebref =mFirebaseDatabase!!.getReference().child("Refrigerator")
                    maucdebref.child(refg_user!!.Productid!!).child("Product_Status").setValue("SOLD")
                    maucdebref.child(refg_user!!.Productid!!).child("Winner_uid").setValue(winner!!.senderuid)
                }



                showResult()
            }

            R.id.chat->{

                //chat module
                val intent = Intent(this, chat_list::class.java)
//            intent.putExtra("postion",position)
                intent.putExtra("refrence",refrence)

                intent.putExtra("object",winner)
                intent.putExtra("activity","auctioner")
                startActivity(intent)

            }

        }

    }

    private fun  showResult(){
        val builder = AlertDialog.Builder(this)
        val inflater = layoutInflater

        val dialogueView = inflater.inflate(R.layout.show_winner_detail, null)

        builder.setView(dialogueView)

        builder.setTitle("WINNER DETAILS")

        val deleteDialogue = builder.create()
        deleteDialogue.show()

         var Bidder_name: TextView=dialogueView.findViewById(R.id.bidder_name)
        var Bidder_email: TextView=dialogueView.findViewById(R.id.bidder_email)
        var Bidder_phone: TextView=dialogueView.findViewById(R.id.bidder_phone)
        var Bidder_bid:TextView=dialogueView.findViewById(R.id.bidder_bid)

        Bidder_name.setText(winner!!.sendername)
        Bidder_email.setText(winner!!.senderemail)
        Bidder_phone.setText(winner!!.senderPhone)
        Bidder_bid.setText(winner!!.bid)

        dialogueView.findViewById<Button>(R.id.chat).setOnClickListener(this)


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

                    if (!mFirebaseAuth.uid!!.equals(dataSnapshot.key)) {


                        val fm = dataSnapshot.getValue<Response_Data>(Response_Data::class.java)

                            if (!mFirebaseAuth.uid.equals(fm!!.senderuid)) {

                                if (refrence == "Mobile_Phones") {

                                    if (mobile_user!!.Productid == fm!!.Productid) {

                                        responselist.add(fm)
                                        mMessageAdapter.notifyDataSetChanged()
                                    }

                                } else if (refrence == "Refrigerator") {

                                    if (refg_user!!.Productid == fm!!.Productid) {

                                        responselist.add(fm)
                                        mMessageAdapter.notifyDataSetChanged()
                                    }
                                }

                            }

                }
                }

                override fun onChildChanged(dataSnapshot: DataSnapshot, s: String?) {
                    if (refrence == "Mobile_Phones") {

                        val fm = dataSnapshot.getValue<Response_Data>(Response_Data::class.java)

                        val i = responselist.indexOf(fm)
                        if(i>-1) {
                            responselist[i]= fm!!
                        }
                        mMessageAdapter.notifyDataSetChanged()


                    } else if (refrence == "Refrigerator") {

                        val fm = dataSnapshot.getValue<Response_Data>(Response_Data::class.java)

                        val i = responselist.indexOf(fm)
                        if(i>-1) {
                            responselist[i]= fm!!
                        }

                        mMessageAdapter.notifyDataSetChanged()

                    }

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
