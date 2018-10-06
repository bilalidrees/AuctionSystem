package com.example.bilalidrees.auctionsystem.User

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
import com.example.bilalidrees.auctionsystem.Homepage.HomePage
import com.example.bilalidrees.auctionsystem.R
import com.example.bilalidrees.auctionsystem.Signup
import com.example.bilalidrees.auctionsystem.Selection_Panel.Auctioner_Selection_panel
import com.google.firebase.auth.FirebaseAuth


class User : AppCompatActivity(),View.OnClickListener {

    private lateinit var mAuth: FirebaseAuth
    private lateinit var editTextEmail: EditText
    private lateinit var editTextPassword: EditText
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user)

        mAuth = FirebaseAuth.getInstance()

        editTextEmail = findViewById(R.id.editTextEmail) as EditText
        editTextPassword = findViewById(R.id.editTextPassword) as EditText

        findViewById<TextView>(R.id.textViewSignup).setOnClickListener(this)

        findViewById<Button>(R.id.buttonLogin).setOnClickListener(this)


       // textViewSignup.setOnClickListener(this)

    }

    private fun userLogin() {
        val email = editTextEmail.text.toString().trim { it <= ' ' }
        val password = editTextPassword.text.toString().trim { it <= ' ' }

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



        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener { task ->
            if (task.isSuccessful) {


                    val intent = Intent(this, HomePage::class.java)
                val token=email.split("@")
                        intent.putExtra("name",token[0])
                intent.putExtra("activity","user")
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                    startActivity(intent)
                    this.finish()




            } else {
                Toast.makeText(this, task.exception!!.message, Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onStart() {
        super.onStart()




        if (mAuth.currentUser != null) {

            val email=mAuth.currentUser!!.email
            val token=email!!.split("@")
            val intent = Intent(this, HomePage::class.java)
            intent.putExtra("name",token[0])
            intent.putExtra("activity","user")
            startActivity(intent)
            this.finish()

        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == 0) {
            this.finish()
        }
    }



    override fun onClick(view: View?) {
        when (view!!.getId()) {
            R.id.textViewSignup -> {

                Log.v("bilal", "nai  chala")

                val intent = Intent(this, Signup::class.java)
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                startActivityForResult(intent, 1)
                this.finish()
            }

            R.id.buttonLogin -> userLogin()
        }
    }
}
