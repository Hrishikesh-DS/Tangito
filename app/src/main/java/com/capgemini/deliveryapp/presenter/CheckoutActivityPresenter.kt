package com.capgemini.deliveryapp.presenter

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context.NOTIFICATION_SERVICE
import android.content.Intent
import android.os.Build
import android.util.Log
import androidx.core.content.ContextCompat.getSystemService
import com.google.firebase.Timestamp
import com.google.firebase.firestore.FirebaseFirestore
import java.util.*

class CheckoutActivityPresenter(val view : CheckoutView) {

    fun saveORderDetails(UID : String,order : String,totla : Int)
    {
        val docData = hashMapOf(
                "order" to order,
                "location" to view.getSharedPrefLocation(),
                "total" to totla,
                "date" to Timestamp(Date()),

                )


        val  db = FirebaseFirestore.getInstance()
        db.collection("users").document(UID).collection("orders")
                .add(docData)
                .addOnSuccessListener { Log.d("TAG", "DocumentSnapshot successfully written!") }
                .addOnFailureListener { e -> Log.w("TAG", "Error writing document", e) }
    }

 /*   private fun sendNotification() {
        //1. Get the reference of notification manager
        val nManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
        //NotificationManager nManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE) in java


        //2. Create Notification
        lateinit var builder: Notification.Builder
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel("test", "AndroidUI",
                NotificationManager.IMPORTANCE_DEFAULT)

            nManager.createNotificationChannel(channel)//register channel

            builder = Notification.Builder(context,"test")
        }
        else{
            builder = Notification.Builder(context)
        }


      //  builder.setSmallIcon(R.drawable.ic_launcher_foreground)
        builder.setContentTitle("Authentication")
        builder.setContentText("Please complete the Sign-in procedure")
        builder.setAutoCancel(true)









        val myNotification = builder.build()
//        myNotification.flags = Notification.FLAG_AUTO_CANCEL or  Notification.FLAG_NO_CLEAR
        //To make it non clear able


        //3. Send notification
        nManager.notify(1,myNotification)
    }*/

    interface CheckoutView {
        fun showBill(bill : String)

       fun getSharedPrefLocation() : String
        fun showToast(message: String?)
    }
}