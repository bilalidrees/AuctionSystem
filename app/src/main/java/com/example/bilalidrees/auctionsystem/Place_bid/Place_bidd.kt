package com.example.bilalidrees.auctionsystem.Place_bid

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.view.View
import android.widget.*
import com.example.bilalidrees.auctionsystem.ListItems.Bidder_selected_itemslist
import com.example.bilalidrees.auctionsystem.Mobile_Phone.Mobile_Phone_Data
import com.example.bilalidrees.auctionsystem.R
import com.example.bilalidrees.auctionsystem.Response.Response_Data
import com.example.bilalidrees.auctionsystem.Selection_Panel.Bidder_Selection_Panel
import com.example.bilalidrees.auctionsystem.User.User_data
import com.example.bilalidrees.auctionsystem.refrigerator.Refrigerator_Data
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.google.firebase.storage.StorageReference
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit
import javax.xml.datatype.DatatypeConstants.MINUTES
import javax.xml.datatype.DatatypeConstants.HOURS



class Place_bidd : AppCompatActivity(), View.OnClickListener {

    private var muserEventListener: ChildEventListener? = null
    private lateinit var mAuth: FirebaseAuth
    private lateinit var mFirebaseDatabase: FirebaseDatabase
    private lateinit var mStorageRef: StorageReference
    private lateinit var mDatabaseRef: DatabaseReference

    private lateinit var mUserDatabaseReference: DatabaseReference


    private lateinit var mobile_user: Mobile_Phone_Data
    private lateinit var refg_user: Refrigerator_Data

    private lateinit var refrence: String
    private lateinit var list: String

    private lateinit var radiobuttonfor10: RadioButton

    private lateinit var editText_own: TextView
    private lateinit var editText_pro_name: TextView
    private lateinit var editText_pro_price: TextView
    private lateinit var editText_bid: EditText
    private lateinit var submit: Button

    private lateinit var editText_time_counter: TextView
    private lateinit var countDownTimer: CountDownTimer

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_place_bidd)

        refrence = intent.extras.getString("refrence")
        list = intent.extras.getString("list")

        editText_own = findViewById(R.id.info_owner_name)
        editText_pro_name = findViewById(R.id.info_pro_name)
        editText_pro_price = findViewById(R.id.info_pro_price)


        editText_time_counter = findViewById(R.id.time_counter)

        editText_bid = findViewById(R.id.bid)


        mAuth = FirebaseAuth.getInstance()
        mFirebaseDatabase = FirebaseDatabase.getInstance()
        mDatabaseRef = mFirebaseDatabase!!.getReference().child("RESPONSES")


        mUserDatabaseReference = mFirebaseDatabase!!.getReference().child("Users")

        if (refrence == "Mobile_Phones") {

            mobile_user = intent.extras.get("object") as Mobile_Phone_Data
            editText_own.setText(mobile_user.Ownername)
            editText_pro_name.setText(mobile_user.Productname)
            editText_pro_price.setText(mobile_user.Product_amount)


        } else if (refrence == "Refrigerator") {
            refg_user = intent.extras.get("object") as Refrigerator_Data
            editText_own.setText(refg_user.Ownername)
            editText_pro_name.setText(refg_user.Companyname)
            editText_pro_price.setText(refg_user.Product_amount)
        }


        editText_bid.isEnabled = false

        findViewById<Button>(R.id.submit_response).isEnabled = false


        findViewById<RadioButton>(R.id.check_price).setOnClickListener(this)

        findViewById<Button>(R.id.submit_response).setOnClickListener(this)


    }

    override fun onStart() {
        super.onStart()


if(list.equals("live")) {

    val df = SimpleDateFormat("k:m")
    val time = df.format(Calendar.getInstance().time)

    if (refrence == "Mobile_Phones") {

       val usertime= mobile_user.End_time

        var currenttoken=time.split(":")
        var endtoken=usertime!!.split(":")

        var hour=Integer.parseInt(endtoken[0])-Integer.parseInt(currenttoken[0])
        var min:Int=0

        if(Integer.parseInt(endtoken[1])<Integer.parseInt(currenttoken[1])){
            min=Integer.parseInt(currenttoken[1])-Integer.parseInt(endtoken[1])
        }
        else if(Integer.parseInt(endtoken[1])>Integer.parseInt(currenttoken[1])){
            min=Integer.parseInt(endtoken[1])-Integer.parseInt(currenttoken[1])
        }


        var exact=""+hour+":"+min+":"+"00"

        val milihour=hour*3600000
        val milimin=min*60000

        val  ans=milihour+milimin

        editText_time_counter.setText(exact)


        countDownTimer=object :CountDownTimer(ans.toLong(), 1000){

            override fun onTick(millisUntilFinished: Long) {
                val millis = millisUntilFinished
                val hms = String.format("%02d:%02d:%02d", TimeUnit.MILLISECONDS.toHours(millis),
                        TimeUnit.MILLISECONDS.toMinutes(millis) - TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(millis)),
                        TimeUnit.MILLISECONDS.toSeconds(millis) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millis)))
                println(hms)

                editText_time_counter.setText(hms)
            }

            override fun onFinish() {

                editText_time_counter.setText("finished")

                back()

            }

        }.start()
    }

    else if (refrence == "Refrigerator") {
        val usertime=refg_user.End_time

        var currenttoken=time.split(":")
        var endtoken=usertime!!.split(":")

        var hour=Integer.parseInt(endtoken[0])-Integer.parseInt(currenttoken[0])
        var min=Integer.parseInt(endtoken[1])-Integer.parseInt(currenttoken[1])

        var exact=""+hour+":"+min+":"+"00"

        editText_time_counter.setText(exact)

        countDownTimer=object :CountDownTimer(180000, 1000){

            override fun onTick(millisUntilFinished: Long) {
                val millis = millisUntilFinished
                val hms = String.format("%02d:%02d:%02d", TimeUnit.MILLISECONDS.toHours(millis),
                        TimeUnit.MILLISECONDS.toMinutes(millis) - TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(millis)),
                        TimeUnit.MILLISECONDS.toSeconds(millis) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millis)))
                println(hms)

                editText_time_counter.setText(hms)
            }

            override fun onFinish() {

                editText_time_counter.setText("finished")
                back()
            }

        }.start()
    }
}

else if(list.equals("upcoming")){

    editText_time_counter.setText("UPCOMING!!!")
}


    }

     fun back() {

         val intent = Intent(this, Bidder_Selection_Panel::class.java)
         startActivity(intent)
    }


    override fun onClick(view: View?) {
        when (view!!.getId()) {

            R.id.check_price -> {

                editText_bid.isEnabled = true
                findViewById<Button>(R.id.submit_response).isEnabled = true


            }
            R.id.submit_response -> {

                if (findViewById<RadioButton>(R.id.check_price).isChecked) {
                  val price= Integer.parseInt(editText_pro_price.text.toString())
                    val bid= Integer.parseInt(editText_bid.text.toString())

                    if(price>bid){
                        Toast.makeText(this@Place_bidd, "Select  higher rice  than Product Price", Toast.LENGTH_SHORT).show()
                    }
                    else {
                        getuserinfo()
                    }

                }
                else {
                    Toast.makeText(this@Place_bidd, "you have  unchecked the  term", Toast.LENGTH_SHORT).show()
                }
            }

        }
    }


    private fun getuserinfo() {

        mUserDatabaseReference = mFirebaseDatabase!!.getReference().child("Users")
        muserEventListener = object : ChildEventListener {

            override fun onChildAdded(dataSnapshot: DataSnapshot, s: String?) {
                val fm = dataSnapshot.getValue<User_data>(User_data::class.java)

                if (fm!!.uid.equals(mAuth.currentUser!!.uid)) {

                    push(fm)

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
        mUserDatabaseReference.addChildEventListener(muserEventListener!!)


    }


    private fun push(user: User_data) {


        if (refrence == "Mobile_Phones") {

            mobile_user

            val sendresposne = Response_Data(mobile_user.Ownername, mobile_user.Owneremail, mobile_user.Owneremail, mobile_user.Owneruid
                    , mobile_user.Productname, mobile_user.Productid, mobile_user.Date, mobile_user.Start_time, mobile_user.End_time,
                    mobile_user.Product_amount, user.name, user.email, user.phone, user.uid, editText_bid.text.toString())

            mDatabaseRef.child(mobile_user.Productid!!).setValue(sendresposne).addOnCompleteListener({
                Toast.makeText(this, "data  pushed", Toast.LENGTH_SHORT).show()

                editText_own.setText("")
                editText_pro_name.setText("")
                editText_pro_price.setText("")
                editText_bid.setText("")
                back()

                
            })
        } else if (refrence == "Refrigerator") {


            val sendresposne = Response_Data(refg_user.Ownername, refg_user.Owneremail, refg_user.Owneremail, refg_user.Owneruid
                    , refg_user.Companyname, refg_user.Productid, refg_user.Date, refg_user.Start_time, refg_user.End_time,
                    refg_user.Product_amount, user.name, user.email, user.phone, user.uid, editText_bid.text.toString())

            mDatabaseRef.child(mAuth.uid!!).setValue(sendresposne).addOnCompleteListener({
                Toast.makeText(this, "data  pushed", Toast.LENGTH_SHORT).show()

                editText_own.setText("")
                editText_pro_name.setText("")
                editText_pro_price.setText("")
                editText_bid.setText("")

                back()
            })


        }
    }

}