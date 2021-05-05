package com.capgemini.deliveryapp.presenter

import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth

class LogoutFragmentPresenter (val view : LogoutView){

    fun signoutFireBase()
    {
        val fAuth = FirebaseAuth.getInstance()
        fAuth.signOut()
    }

     fun  deleteSharedPrefLocation()
         {
       val pref=view.gettheSharedPreferences()

        if (pref != null) {
            pref.edit().remove("location").commit()
        }


    }



    interface LogoutView{

        fun gettheSharedPreferences(): SharedPreferences?
        fun navigateToHomeScreen()
    }
}