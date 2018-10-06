package com.example.bilalidrees.auctionsystem.refrigerator
import com.example.bilalidrees.auctionsystem.Mobile_Phone.Mobile_Phone_Data
import java.io.Serializable

data class Refrigerator_Data( var Ownername:String? =null, var Owneremail:String? = null, var OwnerPhone:String? = null,var Owneruid:String? = null, var Companyname:String? = null
                              , var Productid:String? = null,var Product_image:String?=null,var Product_model:String?=null,var Product_type:String?=null,var Product_color:String?=null,
                              var Product_width:String?=null,var Product_height:String?=null,var Date:String?=null,var Start_time:String?=null,var End_time:String?=null,var Product_amount:String?=null,var Product_Status:String?=null,var Winner_uid:String?=null):Serializable {

    override fun equals(other: Any?): Boolean {
        //   return super.equals(other)
        val Refrigerator_Data = other as Refrigerator_Data
        if (Refrigerator_Data.Productid == this.Productid) return true

        return false
    }
}

