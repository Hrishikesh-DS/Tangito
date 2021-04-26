package com.capgemini.firebasedemo

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth

lateinit var mEmail : EditText
lateinit var mPassword: EditText
lateinit var mLogin : Button
class LoginActivity : AppCompatActivity() {
    val fAuth = FirebaseAuth.getInstance()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        mEmail = findViewById<EditText>(R.id.Lemail)
        mPassword = findViewById<EditText>(R.id.LPassword)
        mLogin = findViewById<Button>(R.id.LLogin)

    }

    fun navigateToREgister(view: View) {
        val i= Intent(this,RegisterActivity::class.java)
        startActivity(i)
    }

    fun LoginClicked(view: View) {
       val email= mEmail.text.toString().trim()
        val pass= mPassword.text.toString().trim()
        fAuth.signInWithEmailAndPassword(email,pass).addOnCompleteListener {
            if(it.isSuccessful)
            {
                Toast.makeText(this, "Login successful ", Toast.LENGTH_LONG).show()
                Log.d("register","success")
                val i= Intent(this,MainActivity::class.java)
                startActivity(i)
            }
            else
            {
                Toast.makeText(this, "Error! ${it.exception?.message}", Toast.LENGTH_SHORT).show()
                Log.d("register","fail :: ${it.exception?.message}")
            }
        }
    }
}