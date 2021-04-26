package com.capgemini.firebasedemo

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import com.google.firebase.auth.FirebaseAuth
import com.capgemini.firebasedemo.AppData.*
import com.capgemini.firebasedemo.AppData.Menu.Item
import com.capgemini.firebasedemo.AppData.Menu.itemlist
import com.google.firebase.database.DatabaseReference

class MainActivity : AppCompatActivity() {
    val fAuth = FirebaseAuth.getInstance()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

      /*  val wrapper= FireBaseWrapper()
        Log.d("main","main")
        wrapper.getMenu(object : MyMenuCallback{
           override fun onCallback(itemlist: List<Item>) {
               Log.d("plswork",itemlist.toString())
           }
       })*/

        val wrapper= FireBaseWrapper()
        wrapper.getMenu {
            Log.d("lambda",it.toString())
        }
wrapper.getLocationsOfCity("bangalore"){
    Log.d("pickedcity",it.toString())
}


    }

    fun logoutClicked(view: View) {
        fAuth.signOut()

        finish()
    }
}