package com.capgemini.deliveryapp.presenter

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import android.widget.Toast
import androidx.core.app.ActivityCompat.requestPermissions
import androidx.core.content.ContextCompat

class MainActivityPresenter(val view1 : MainView) {

    fun checkPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if ( view1.checkSelfPermission()
            ) {

                view1.requestPermissions()
            } else {
               //removed debub toast
                // view1.showToast("location permission granted")
            }
        }
    }
    interface MainView
    {
        fun requestPermissions ()
        fun checkSelfPermission() : Boolean
        fun showToast(message: String?)
    }
}