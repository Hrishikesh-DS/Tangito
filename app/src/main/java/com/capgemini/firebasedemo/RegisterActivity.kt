package com.capgemini.firebasedemo

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth

import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase

class RegisterActivity : AppCompatActivity() {
   lateinit var mName : EditText
    lateinit var mEmail : EditText
    lateinit var mPassword : EditText
    lateinit var mPhone : EditText

    lateinit var mLogin : TextView
    lateinit var mRegister : Button

    lateinit var fstore : FirebaseFirestore




    val fAuth = FirebaseAuth.getInstance()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
       if(fAuth.currentUser !=null)
       {  val i= Intent(this,MainActivity::class.java)
           startActivity(i)

       }
         mName = findViewById<EditText>(R.id.RNametxt)
         mEmail = findViewById<EditText>(R.id.Remail)
         mPassword = findViewById<EditText>(R.id.RPassword)
         mPhone = findViewById<EditText>(R.id.Rphone)

        mLogin = findViewById<TextView>(R.id.RLogintxt)
        mRegister = findViewById<Button>(R.id.RRegister)





    }

    fun onRegisterClick(view: View) {
        val email = mEmail.text.toString().trim()
        val pass = mPassword.text.toString().trim()
        val phone = mPhone.text.toString().trim()
        val name= mName.text.toString().trim()

        if(email.length==0) {
            Toast.makeText(this, "enter an email", Toast.LENGTH_SHORT).show()
            return
        }
         if(pass.length <6) {
             Toast.makeText(this, "password must be atleast 6 characters", Toast.LENGTH_SHORT).show()
             return
         }
        fAuth.createUserWithEmailAndPassword(email,pass)
            .addOnCompleteListener{
                if(it.isSuccessful)
                {
                    Toast.makeText(this, "USer Created", Toast.LENGTH_SHORT).show()
                    Log.d("register","success")
                   val UID= fAuth.currentUser.uid
                     fstore = FirebaseFirestore.getInstance()
                    val documentreference= fstore.collection("users")
                            .document(UID)

                    val currentUser = HashMap<String, Any>()
                    currentUser.put("name", name)
                    currentUser.put("phone", phone)
                    currentUser.put("email", email)
                    currentUser.put("password", pass)

                    documentreference.set(currentUser).addOnCompleteListener {
                        if(it.isSuccessful)
                        {
                            Log.d("user","created : ${currentUser.toString()}")
                        }
                    }


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

    fun navigateToLogin(view: View) {
        val i= Intent(this,LoginActivity::class.java)
        startActivity(i)
    }
}