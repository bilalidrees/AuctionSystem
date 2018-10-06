package com.example.bilalidrees.auctionsystem.Response

import com.example.bilalidrees.auctionsystem.Mobile_Phone.Mobile_Phone_Data
import java.io.Serializable

data class Response_Data ( var Ownername:String? =null, var Owneremail:String? = null, var OwnerPhone:String? = null,var Owneruid:String? = null, var Productname:String? = null
                           , var Productid:String? = null,var Date:String?=null,var Start_time:String?=null,var End_time:String?=null,var Product_amount:String?=null

                            ,var sendername:String? =null ,var senderemail:String? = null, var senderPhone:String? = null,var senderuid:String? = null,var bid:String? = null): Serializable
{

    override fun equals(other: Any?): Boolean {
        //   return super.equals(other)
        val Response_Data = other as Response_Data
        if (Response_Data.Productid == this.Productid && Response_Data.senderuid==this.senderuid ) return true

        return false
    }
}


