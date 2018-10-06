package com.example.bilalidrees.auctionsystem.Selection_Panel

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.Toast
import com.example.bilalidrees.auctionsystem.ListItems.Auctioner_selected_itemslist
import com.example.bilalidrees.auctionsystem.ListItems.Bidder_selected_itemslist
import com.example.bilalidrees.auctionsystem.R
import com.example.bilalidrees.auctionsystem.User.User
import com.google.firebase.auth.FirebaseAuth

class Auctioner_ResonseSelection_panel : AppCompatActivity() , View.OnClickListener{

    private lateinit var refrence:String


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_auctioner__resonse_selection_panel)

        refrence=intent.extras.getString("refrence")
        Toast.makeText(this@Auctioner_ResonseSelection_panel, refrence, Toast.LENGTH_SHORT).show()

        findViewById<Button>(R.id.past_auction).setOnClickListener(this)

        findViewById<Button>(R.id.live_auction).setOnClickListener(this)
        findViewById<Button>(R.id.upcoming_auction).setOnClickListener(this)



    }

    override fun onClick(view: View?) {

        when (view!!.getId()) {
            R.id.live_auction -> {

                Toast.makeText(this@Auctioner_ResonseSelection_panel, "pressed live", Toast.LENGTH_SHORT).show()

                if(refrence.toString()=="Mobile_Phones"){

                    val intent = Intent(this, Auctioner_selected_itemslist::class.java)
                    intent.putExtra("refrence","Mobile_Phones")
                    intent.putExtra("list","live")
                    startActivity(intent)
                }
                else if(refrence=="Refrigerator"){
                    val intent = Intent(this, Auctioner_selected_itemslist::class.java)
                    intent.putExtra("refrence","Refrigerator")
                    intent.putExtra("list","live")
                    startActivity(intent)


                }

            }

            R.id.upcoming_auction -> {
                Toast.makeText(this@Auctioner_ResonseSelection_panel, "pressed upcoming", Toast.LENGTH_SHORT).show()
                if(refrence=="Mobile_Phones"){

                    val intent = Intent(this, Auctioner_selected_itemslist::class.java)
                    intent.putExtra("refrence","Mobile_Phones")
                    intent.putExtra("list","upcoming")
                    startActivity(intent)
                }
                else if(refrence=="Refrigerator"){
                    val intent = Intent(this, Auctioner_selected_itemslist::class.java)
                    intent.putExtra("refrence","Refrigerator")
                    intent.putExtra("list","upcoming")
                    startActivity(intent)


                }


            }

            R.id.past_auction->{
                Toast.makeText(this@Auctioner_ResonseSelection_panel, "pressed past", Toast.LENGTH_SHORT).show()

                if(refrence=="Mobile_Phones"){
                    Toast.makeText(this@Auctioner_ResonseSelection_panel, "you  clicked past", Toast.LENGTH_SHORT).show()
                    val intent = Intent(this, Auctioner_selected_itemslist::class.java)
                    intent.putExtra("refrence","Mobile_Phones")
                    intent.putExtra("list","past")
                    startActivity(intent)
                }
                else if(refrence=="Refrigerator"){
                    val intent = Intent(this, Auctioner_selected_itemslist::class.java)
                    intent.putExtra("refrence","Refrigerator")
                    intent.putExtra("list","past")
                    startActivity(intent)


                }

            }
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

    override fun onBackPressed() {

        this.finish()

    }

}
