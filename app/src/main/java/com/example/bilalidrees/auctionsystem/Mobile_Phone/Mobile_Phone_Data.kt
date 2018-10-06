package com.example.bilalidrees.auctionsystem.Mobile_Phone

import java.io.Serializable

data class Mobile_Phone_Data(var Ownername: String? = null, var Owneremail: String? = null, var OwnerPhone: String? = null, var Owneruid: String? = null, var Productname: String? = null
                             , var Productid: String? = null, var Product_image: String? = null, var Product_camera: String? = null, var Product_battery: String? = null
                             , var Product_Storage: String? = null, var Product_Ram: String? = null, var Product_processor: String? = null
                             , var Date: String? = null, var Start_time: String? = null, var End_time: String? = null, var Product_amount: String? = null, var Product_Status: String? = null, var Winner_uid: String? = null) : Serializable {

    override fun equals(other: Any?): Boolean {
        //   return super.equals(other)
        val mobile_Phone_Data = other as Mobile_Phone_Data
        if (mobile_Phone_Data.Productid == this.Productid ) return true

        return false
    }
}

