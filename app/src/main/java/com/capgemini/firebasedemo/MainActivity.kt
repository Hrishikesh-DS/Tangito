package com.capgemini.firebasedemo

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.google.firebase.auth.FirebaseAuth

class MainActivity : AppCompatActivity() {
    val fAuth = FirebaseAuth.getInstance()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
     /*  val i= Intent(this,RegisterActivity::class.java)
        startActivity(i)*/
    }

    fun logoutClicked(view: View) {
        fAuth.signOut()

        finish()
    }
}