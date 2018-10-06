package com.example.bilalidrees.auctionsystem

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.util.Patterns
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.example.bilalidrees.auctionsystem.User.User
import com.example.bilalidrees.auctionsystem.User.User_data
import com.example.bilalidrees.auctionsystem.Selection_Panel.Auctioner_Selection_panel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class Signup : AppCompatActivity(), View.OnClickListener {

    private lateinit var editTextEmail: EditText
    private lateinit var editTextPassword: EditText
    private lateinit var editTextName: EditText
    private lateinit var editTextphone: EditText

    private lateinit var mAuth: FirebaseAuth
    private lateinit  var mFirebaseDatabase: FirebaseDatabase
    private lateinit var mUserDatabaseReference: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup)

        editTextEmail = findViewById(R.id.editTextEmail) as EditText
        editTextPassword = findViewById(R.id.editTextPassword) as EditText

        editTextName = findViewById(R.id.editTextName) as EditText
        editTextphone=findViewById(R.id.editTextPhone) as EditText

        mAuth = FirebaseAuth.getInstance()


        findViewById<Button>(R.id.buttonSignUp).setOnClickListener(this)
        findViewById<TextView>(R.id.textViewLogin).setOnClickListener(this)


    }

    private fun registerUser() {
        val name = editTextName.text.toString().trim { it <= ' ' }
        val email = editTextEmail.text.toString().trim { it <= ' ' }
        val password = editTextPassword.text.toString().trim { it <= ' ' }
        val phone = editTextphone.text.toString().trim { it <= ' ' }


        if (email.isEmpty()) {
            editTextEmail.error = "Email is required"
            editTextEmail.requestFocus()
            return
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            editTextEmail.error = "Please enter a valid email"
            editTextEmail.requestFocus()
            return
        }

        if (password.isEmpty()) {
            editTextPassword.error = "Password is required"
            editTextPassword.requestFocus()
            return
        }

        if (password.length < 6) {
            editTextPassword.error = "Minimum lenght of password should be 6"
            editTextPassword.requestFocus()
            return
        }



        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener { task ->
            if (task.isSuccessful) {


                val user =User_data(name,email,phone,mAuth.uid)

                mFirebaseDatabase = FirebaseDatabase.getInstance()

                mUserDatabaseReference = mFirebaseDatabase!!.getReference().child("Users")
                Log.v("bilal", mUserDatabaseReference.toString())



                mUserDatabaseReference.child(mAuth.uid!!).setValue(user).addOnCompleteListener ({
                    this@Signup.finish()
                    val intent = Intent(this@Signup,User::class.java)
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                    startActivity(intent)
                })

                Toast.makeText(applicationContext, "data pushed", Toast.LENGTH_SHORT).show()




            }
            else {

                if (task.exception!! is FirebaseAuthUserCollisionException) {
                    Toast.makeText(applicationContext, "You are already registered", Toast.LENGTH_SHORT).show()

                } else {
                    Toast.makeText(applicationContext, task.exception!!.message, Toast.LENGTH_SHORT).show()
                }

            }
        }

    }

    public override fun onStart() {
        super.onStart()

        if (mAuth!!.getCurrentUser() != null) {
            finish()
            startActivity(Intent(this, Auctioner_Selection_panel::class.java))

        }
    }


    override fun onBackPressed() {
        finish()
        startActivity(Intent(this, User::class.java))

    }





    override fun onClick(view: View?) {
        when (view!!.getId()) {
            R.id.buttonSignUp -> registerUser()

            R.id.textViewLogin -> {

                startActivity(Intent(this, User::class.java))
                finish()
            }
        }
    }


}
