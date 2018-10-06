package com.example.bilalidrees.auctionsystem.Mobile_Phone

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Intent
import android.net.Uri
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import com.example.bilalidrees.auctionsystem.R
import com.squareup.picasso.Picasso
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;
import android.webkit.MimeTypeMap
import android.content.ContentResolver
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Handler
import android.widget.Toast
import com.example.bilalidrees.auctionsystem.MainActivity
import android.support.annotation.NonNull
import com.example.bilalidrees.auctionsystem.Selection_Panel.Auctioner_Selection_panel
import com.example.bilalidrees.auctionsystem.Selection_Panel.Bidder_Selection_Panel
import com.example.bilalidrees.auctionsystem.User.User_data
import com.google.firebase.database.*
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*


class Mobile_Phone : AppCompatActivity() , View.OnClickListener{

    private var date:String=""
    private var start_time:String=""
    private var end_time:String=""



    private var mImageView: ImageView? = null
    private lateinit var editTextproduct_name: EditText
    private lateinit var editTextproduct_camera: EditText
    private lateinit var editTextproduct_battery: EditText
    private lateinit var editTextproduct_storage: EditText
    private lateinit var editTextproduct_ram: EditText
    private lateinit var editTextproduct_processor: EditText
    private lateinit var editTextproduct_amount: EditText

    private lateinit var editText_date: TextView
    private lateinit var editText_start_time: TextView
    private lateinit var editText_end_time: TextView

    private val PICK_IMAGE_REQUEST = 1
    private var mImageUri: Uri? = null

    private  var  muserEventListener: ChildEventListener?=null
    private lateinit var mAuth: FirebaseAuth
    private lateinit  var mFirebaseDatabase: FirebaseDatabase
    private lateinit var mStorageRef: StorageReference
    private lateinit var mDatabaseRef: DatabaseReference

    private lateinit var mUserDatabaseReference: DatabaseReference

    private var mUploadTask: StorageTask<*>? = null

    private var mDateSetListener: DatePickerDialog.OnDateSetListener? = null

    private var st_listner: TimePickerDialog.OnTimeSetListener? = null
    private var end_listner:TimePickerDialog.OnTimeSetListener? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mobile__phone)

        mAuth = FirebaseAuth.getInstance()

        mImageView = findViewById(R.id.product_image)
        editTextproduct_name= findViewById(R.id.product_name)
        editTextproduct_camera=findViewById(R.id.product_camera)
        editTextproduct_battery=findViewById(R.id.product_battery)
        editTextproduct_storage=findViewById(R.id.product_Storage)
        editTextproduct_ram=findViewById(R.id.product_ram)
        editTextproduct_processor=findViewById(R.id.product_processor)
        editTextproduct_amount=findViewById(R.id.product_amount)
        editText_date=findViewById(R.id.date)
        editText_start_time=findViewById(R.id.Start_time)
        editText_end_time=findViewById(R.id.End_time)


        findViewById<Button>(R.id.choosefile).setOnClickListener(this)
        findViewById<Button>(R.id.post).setOnClickListener(this)
        findViewById<ImageView>(R.id.date_image).setOnClickListener(this)
        findViewById<ImageView>(R.id.start_image).setOnClickListener(this)
        findViewById<ImageView>(R.id.end_image).setOnClickListener(this)

        mFirebaseDatabase = FirebaseDatabase.getInstance()

        mStorageRef = FirebaseStorage.getInstance().getReference("uploads")

Log.v("storage",mStorageRef.toString())

        mDatabaseRef =mFirebaseDatabase!!.getReference().child("Mobile_Phones")


        Log.v("database",mDatabaseRef.toString())
    }




    override fun onClick(view: View?) {
        when (view!!.getId()) {
            R.id.choosefile -> {
                openfilechooser()
            }

            R.id.post->{

               val  name= editTextproduct_name.text.toString().trim { it <= ' ' }
               val camera =editTextproduct_camera.text.toString().trim { it <= ' ' }
                val  battery=editTextproduct_battery.text.toString().trim { it <= ' ' }
                val  storage=editTextproduct_storage.text.toString().trim { it <= ' ' }
                val  ram=editTextproduct_ram.text.toString().trim { it <= ' ' }
                val  process=editTextproduct_processor.text.toString().trim { it <= ' ' }
                val  amount=editTextproduct_amount.text.toString().trim { it <= ' ' }
                val  Dates=   editText_date.text.toString().trim { it <= ' ' }
                val  st_time= editText_start_time.text.toString().trim { it <= ' ' }
                val  e_time=editText_end_time.text.toString().trim { it <= ' ' }


                if (name.isEmpty()) {
                    editTextproduct_name.error = "Product Name is required"
                    editTextproduct_name.requestFocus()
                    return
                }
                if (camera.isEmpty()) {
                    editTextproduct_camera.error = "ProductCamera is required"
                    editTextproduct_camera.requestFocus()
                    return
                }
                if (battery.isEmpty()) {
                    editTextproduct_battery.error ="Product  battery is required"
                    editTextproduct_battery.requestFocus()
                    return
                }
                if (storage.isEmpty()) {
                    editTextproduct_storage.error = "Product storage is required"
                    editTextproduct_storage.requestFocus()
                    return
                }

                if (process.isEmpty()) {
                    editTextproduct_processor.error = "Product Processor is required"
                    editTextproduct_processor.requestFocus()
                    return
                }
                if (ram.isEmpty()) {
                    editTextproduct_ram.error = "Product Ram is required"
                    editTextproduct_ram.requestFocus()
                    return
                }
                if (amount.isEmpty()) {
                    editTextproduct_amount.error = "Product Amount is required"
                    editTextproduct_amount.requestFocus()
                    return
                }

                if (date==""||editText_date.text=="DATE") {
                    Toast.makeText(this, "Invalid Date slection", Toast.LENGTH_SHORT).show()
                    return
                }

                if (start_time==""||editText_start_time.text=="Start_Time") {
                    Toast.makeText(this, "Invalid Start time slection", Toast.LENGTH_SHORT).show()

                    return
                }


                if (end_time==""||editText_end_time.text=="END_Time") {
                    Toast.makeText(this, "Invalid End time slection", Toast.LENGTH_SHORT).show()
                    return
                }




                    val smf = SimpleDateFormat("MM/dd/yyyy")
                    Log.v("DATE",date)
                    val dates = smf.parse(date)

                    val df = SimpleDateFormat("k:m")
                    val time = df.format(Calendar.getInstance().time)

                    val get = start_time!!.compareTo(time)
                    val gets = end_time!!.compareTo(time)

                    if (!dates.after(Date()) && (get < 0 || gets < 0)) run {

                        Toast.makeText(view.context, "You have  changed date from future  to present", Toast.LENGTH_SHORT).show()

                    }
                    else{

                        if (date == "" || start_time == "" || end_time == "") run{
                            Toast.makeText(view.context, "Invalid slection", Toast.LENGTH_SHORT).show()
                        }

                        else{

                            if (mUploadTask != null && mUploadTask!!.isInProgress()) {
                                Toast.makeText(this, "Upload in progress", Toast.LENGTH_SHORT).show();
                            }
                            else {
                                uploadFile()
                            }

                        }
                    }



            }


            R.id.date_image->{

                var cal = Calendar.getInstance()
                var year = cal.get(Calendar.YEAR)
                var Month = cal.get(Calendar.MONTH)
                var day = cal.get(Calendar.DAY_OF_MONTH)

                var dialog = DatePickerDialog(
                        this,
                        android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                        mDateSetListener,
                        year, Month, day)

                dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
                dialog.setTitle("BOOKING DATE")
                dialog.datePicker.minDate = System.currentTimeMillis() - 1000

                Log.v("CRASHED", "CRASHED")
                dialog.show()

                mDateSetListener=object :DatePickerDialog.OnDateSetListener{

                    override fun onDateSet(view: DatePicker?, year: Int, month: Int, dayOfMonth: Int) {

                        var mmonth=month

                        mmonth=mmonth+1

                        Log.d("DATE", "onDateSet: mm/dd/yyy: $month/$day/$year")


                        date = ""+mmonth + "/" + day + "/" + year

                        if (date == "") {
                            Toast.makeText(view!!.getContext(), "Invalid Date slection", Toast.LENGTH_SHORT).show()
                            var dialog = DatePickerDialog(view.getContext(),
                                    android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                                    mDateSetListener,
                                    year, month, day)
                            dialog.setTitle("BOOKING DATE")
                            dialog.datePicker.minDate = System.currentTimeMillis() - 1000
                            dialog.show()

                        } else {
                            editText_date.setText(date)

                        }
                    }
                }
            }

            R.id.start_image->{
                val cale = Calendar.getInstance()
                val hour = cale.get(Calendar.HOUR_OF_DAY)
                val minute = cale.get(Calendar.MINUTE)

                val timePickerDialog = TimePickerDialog(this, st_listner, hour, minute, false)

                timePickerDialog.setTitle("Start_time")

                Log.v("CRASHED", "CRASHED")

                if (date == "") {
                    Toast.makeText(view.context, "Invalid Date slection", Toast.LENGTH_SHORT).show()

                } else {
                    timePickerDialog.show()

                }

                st_listner=object :TimePickerDialog.OnTimeSetListener{

                    override fun onTimeSet(view: TimePicker?, hourOfDay: Int, minute: Int) {

                        try {
                            val c = Calendar.getInstance()
                            c.set(Calendar.HOUR_OF_DAY, hourOfDay)
                            c.set(Calendar.MINUTE, minute)


                            val smf = SimpleDateFormat("MM/dd/yyyy")
                            val dates = smf.parse(date)

                            if (c.timeInMillis < Calendar.getInstance().timeInMillis && !dates.after(Date())) {

                                Toast.makeText(view!!.getContext(), "Invalid time slection", Toast.LENGTH_SHORT).show()
                                val timePickerDialog = TimePickerDialog(view.getContext(), st_listner, c.get(Calendar.HOUR_OF_DAY), c.get(Calendar.MINUTE), false)

                                timePickerDialog.setTitle("Start_time")


                                Log.v("CRASHED", "CRASHED")
                                timePickerDialog.show()

                            } else {
                                start_time = hourOfDay.toString() + ":" + minute
                                editText_start_time.setText(start_time)
                            }

                        } catch (e: ParseException) {
                            e.printStackTrace()
                        }
                    } }

            }

            R.id.end_image->{


                val calee = Calendar.getInstance()
                val hours = calee.get(Calendar.HOUR_OF_DAY)
                val minutes = calee.get(Calendar.MINUTE)

                val timePickerdialog = TimePickerDialog(this, end_listner, hours, minutes, false)

                timePickerdialog.setTitle("BOOKING DATE")


                Log.v("CRASHED", "CRASHED")
                if (date == "") {
                    Toast.makeText(view.context, "Invalid Date slection", Toast.LENGTH_SHORT).show()

                } else {
                    timePickerdialog.show()

                }

                end_listner=object :TimePickerDialog.OnTimeSetListener{

                    override fun onTimeSet(view: TimePicker?, hourOfDay: Int, minute: Int) {

                        try {
                            val c = Calendar.getInstance()
                            c.set(Calendar.HOUR_OF_DAY, hourOfDay)
                            c.set(Calendar.MINUTE, minute)

                            val smf = SimpleDateFormat("MM/dd/yyyy")
                            val dates = smf.parse(date)
                            if (c.timeInMillis < Calendar.getInstance().timeInMillis && !dates.after(Date())) {

                                Toast.makeText(view!!.getContext(), "Invalid time slection", Toast.LENGTH_SHORT).show()
                                val timePickerDialog = TimePickerDialog(view.getContext(), st_listner, c.get(Calendar.HOUR_OF_DAY), c.get(Calendar.MINUTE), false)

                                timePickerDialog.setTitle("End_time")


                                Log.v("CRASHED", "CRASHED")
                                timePickerDialog.show()

                            } else {
                                end_time = hourOfDay.toString() + ":" + minute
                                editText_end_time.setText(end_time)
                            }

                        } catch (e: ParseException) {
                            e.printStackTrace()
                        } } }
            }



        }
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK
                && data != null && data.data != null) {
            mImageUri = data.data

            Picasso.with(this).load(mImageUri).fit().centerCrop().into(mImageView)

        }
    }


    private fun getFileExtension(uri: Uri): String? {
        val cR = contentResolver
        val mime = MimeTypeMap.getSingleton()
        return mime.getExtensionFromMimeType(cR.getType(uri))
    }


    private fun uploadFile()
    {


        if (mImageUri != null) {

            var fileReference=mStorageRef.child("Mobile_Phones/"+System.currentTimeMillis()
                    + "." + getFileExtension(mImageUri!!))

            mUploadTask = fileReference.putFile(mImageUri!!)
                    .addOnSuccessListener(OnSuccessListener<UploadTask.TaskSnapshot> { taskSnapshot ->

                        fileReference.downloadUrl.addOnCompleteListener {taskSnapshot ->

                            Toast.makeText(this@Mobile_Phone, "Upload successful", Toast.LENGTH_LONG).show()
                            var url = taskSnapshot.result

                            getOwnerinfo(url.toString())
                        }

                    })
                    .addOnFailureListener(OnFailureListener { e -> Toast.makeText(this@Mobile_Phone, e.message, Toast.LENGTH_SHORT).show() })


        }

    }

    private fun getOwnerinfo(url: String) {

        mUserDatabaseReference = mFirebaseDatabase!!.getReference().child("Users")

        muserEventListener = object : ChildEventListener {

            override fun onChildAdded(dataSnapshot: DataSnapshot, s: String?) {
                val fm = dataSnapshot.getValue<User_data>(User_data::class.java)

                if (fm!!.uid.equals(mAuth.currentUser!!.uid)) {

                   push(fm,url)

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


    private fun push(user:User_data,url:String){

        var proid=mDatabaseRef.push().key
        var push:String?=mDatabaseRef.push().key

    var  mbd=Mobile_Phone_Data(user.name,user.email,user.phone,user.uid,editTextproduct_name.text.toString(),proid,url,editTextproduct_camera.text.toString(),
            editTextproduct_battery.text.toString(),editTextproduct_storage.text.toString(),editTextproduct_ram.text.toString(),
            editTextproduct_processor.text.toString(),date,start_time,end_time,editTextproduct_amount.text.toString(),"UNSOLD","")

        mDatabaseRef.child(proid!!).setValue(mbd).addOnCompleteListener({
            Toast.makeText(this, "data  pushed", Toast.LENGTH_SHORT).show()

            mImageView!!.setImageResource(android.R.color.transparent)
            editTextproduct_name.setText("")
            editTextproduct_camera.setText("")
            editTextproduct_battery.setText("")
            editTextproduct_storage.setText("")
            editTextproduct_ram.setText("")
            editTextproduct_processor.setText("")
            editTextproduct_amount.setText("")
            editText_date.setText("DATE")
            editText_start_time.setText("Start_Time")
            editText_end_time.setText("END_Time")


            val intent = Intent(this, Auctioner_Selection_panel::class.java)
            startActivity(intent)




        })


    }




    private fun  openfilechooser(){

        val intent = Intent()
        intent.type = "image/*"
        intent.action = Intent.ACTION_GET_CONTENT
        startActivityForResult(intent, PICK_IMAGE_REQUEST)
    }



}
