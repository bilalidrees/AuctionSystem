package com.example.bilalidrees.auctionsystem.Product_discription

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import com.example.bilalidrees.auctionsystem.Mobile_Phone.Mobile_Phone_Data
import com.example.bilalidrees.auctionsystem.R
import com.example.bilalidrees.auctionsystem.refrigerator.Refrigerator_Data
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_mobile__phone.view.*

class Product_discription : AppCompatActivity()
{

    private lateinit var refrence:String
    private lateinit var list:String

    //for mobile

    private lateinit var date_image:ImageView
    private lateinit var start_image:ImageView
    private lateinit var end_image:ImageView
    private lateinit var choose:Button
    private lateinit var post:Button

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


    ///for  REfrigdratoer

    private var mImageViewref: ImageView? = null
    private lateinit var editTextcompany_name: EditText
    private lateinit var editTextproduct_model: EditText
    private lateinit var editTextproduct_type: EditText
    private lateinit var editTextproduct_colour: EditText
    private lateinit var editTextproduct_width: EditText
    private lateinit var editTextproduct_height: EditText
    private lateinit var editTextproduct_amountref: EditText

    private lateinit var editText_dateref: TextView
    private lateinit var editText_start_timeref: TextView
    private lateinit var editText_end_timeref: TextView



    private lateinit var mobile_user:Mobile_Phone_Data
    private lateinit var refg_user:Refrigerator_Data

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        refrence=intent.extras.getString("refrence")
        list=intent.extras.getString("list")


        if(refrence=="Mobile_Phones"){

            setContentView(R.layout.activity_mobile__phone)
            mobile_user=intent.extras.get("object") as Mobile_Phone_Data


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


            Picasso.with(this).load(mobile_user.Product_image).fit().centerCrop().into(mImageView)
            editTextproduct_name.setText(mobile_user.Ownername)
            editTextproduct_camera.setText(mobile_user.Product_camera)
            editTextproduct_battery.setText(mobile_user.Product_battery)
            editTextproduct_storage.setText(mobile_user.Product_Storage)
            editTextproduct_ram.setText(mobile_user.Product_Ram)
            editTextproduct_processor.setText(mobile_user.Product_processor)
            editTextproduct_amount.setText(mobile_user.Product_amount)
            editText_date.setText(mobile_user.Date)
            editText_start_time.setText(mobile_user.Start_time)
            editText_end_time.setText(mobile_user.End_time)


                    choose=findViewById(R.id.choosefile)
                    post=findViewById(R.id.post)
                    date_image=findViewById(R.id.date_image)
                    start_image=findViewById(R.id.start_image)
                   end_image= findViewById(R.id.end_image)

            choose.isEnabled=false
            post.isEnabled=false
            date_image.isEnabled=false
            start_image.isEnabled=false
            end_image.isEnabled=false


        }

        else if(refrence=="Refrigerator"){

            setContentView(R.layout.activity_refrigerator)
            refg_user=intent.extras.get("object") as Refrigerator_Data

            mImageViewref = findViewById(R.id.product_image)


            editTextcompany_name = findViewById(R.id.company_name)
            editTextproduct_model=findViewById(R.id.product_model)
            editTextproduct_type=findViewById(R.id.product_type)
            editTextproduct_colour=findViewById(R.id.product_Colour)
            editTextproduct_width=findViewById(R.id.product_Width)
            editTextproduct_height=findViewById(R.id.product_height)
            editTextproduct_amountref=findViewById(R.id.product_amount)
            editText_dateref=findViewById(R.id.date)
            editText_start_timeref=findViewById(R.id.Start_time)
            editText_end_timeref=findViewById(R.id.End_time)


            Picasso.with(this).load(refg_user.Product_image).fit().centerCrop().into(mImageViewref)
            editTextcompany_name.setText( refg_user.Companyname)
            editTextproduct_model.setText( refg_user.Product_model)
            editTextproduct_type.setText( refg_user.Product_type)
            editTextproduct_colour.setText( refg_user.Product_color)
            editTextproduct_width.setText( refg_user.Product_width)
            editTextproduct_height.setText( refg_user.Product_height)
            editTextproduct_amountref.setText( refg_user.Product_amount)
            editText_dateref.setText( refg_user.Date)
            editText_start_timeref.setText( refg_user.Start_time)
            editText_end_timeref.setText( refg_user.End_time)


            choose.isEnabled=false
            post.isEnabled=false
            date_image.isEnabled=false
            start_image.isEnabled=false
            end_image.isEnabled=false
        }

    }



}


