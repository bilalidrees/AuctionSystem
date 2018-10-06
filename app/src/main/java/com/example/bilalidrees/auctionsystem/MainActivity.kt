package com.example.bilalidrees.auctionsystem

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.example.bilalidrees.auctionsystem.User.User

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val myThread = object : Thread() {
            override fun run() {
                try {
                    Thread.sleep(3000)
                    val intent = Intent(applicationContext, User::class.java)
                    startActivity(intent)
                    finish()

                } catch (e: InterruptedException) {
                    e.printStackTrace()
                }

            }
        }
        myThread.start()


    }
}
