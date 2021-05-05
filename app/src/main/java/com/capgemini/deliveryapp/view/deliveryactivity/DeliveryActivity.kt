package com.capgemini.deliveryapp.view.deliveryactivity

import android.content.Context
import android.content.SharedPreferences
import android.net.ConnectivityManager
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.navigation.NavigationView
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import androidx.drawerlayout.widget.DrawerLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.app.ActivityCompat
import androidx.fragment.app.FragmentManager
import com.capgemini.deliveryapp.R
import com.capgemini.deliveryapp.Repository.DBWrapper
import com.capgemini.deliveryapp.presenter.DeliveryActivityPresenter
import com.capgemini.firebasedemo.AppData.FireBaseWrapper
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.nav_header_main.*

class DeliveryActivity : AppCompatActivity(), DeliveryActivityPresenter.DeliveryView {

    private lateinit var appBarConfiguration: AppBarConfiguration
    val PREF_NAME = "Credentials"
    override fun onResume() {
        super.onResume()
        checkInternetConnectivity()
        updateCartBadge()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_delivery)
        val presenter = DeliveryActivityPresenter(this)
   //     val fAuth = FirebaseAuth.getInstance()
        presenter.getNameFromFauth()
     //updateCartBadge()

        // Toast.makeText(this,fAuth.toString(),Toast.LENGTH_LONG).show()
        Log.d("session", "session")

        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

        val fab: FloatingActionButton = findViewById(R.id.fab)
        fab.setOnClickListener { view ->
           // val internet=checkInternetConnectivity().toString()
          //  Toast.makeText(this,internet,Toast.LENGTH_LONG).show()
            presenter.showBottomSheet()
            // Log.d("sum is : ",wrapper.calculateBill().toString())
            //  Snackbar.make(view, "$x", Snackbar.LENGTH_LONG)
            //  .setAction("Action", null).show()
        }
        val drawerLayout: DrawerLayout = findViewById(R.id.drawer_layout)
        val navView: NavigationView = findViewById(R.id.nav_view)
        val navController = findNavController(R.id.nav_host_fragment)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.sendButton,
                R.id.nav_menu,
                R.id.orderFragment,
                R.id.nav_logout,
            ), drawerLayout
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)


        /*   val addresstxt = findViewById<TextView>(R.id.slideMenuAddresstxt)
           addresstxt.text ="Tangito - "+getSharedPrefLocation()*/
    }
//called on onResume
fun updateCartBadge()
{   Log.d("cart","called")
    val wrapper= DBWrapper(this)
    val badge= findViewById<TextView>(R.id.badgeT)
    badge.text=wrapper.getTotalQuantity().toString()
}
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.delivery, menu)

        return true
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }

    fun updateADdressTextView(newaddress: String) {
        val addresstxt = findViewById<TextView>(R.id.slideMenuAddresstxt)
        addresstxt.text = newaddress
    }





    override fun updatenameText(name: String) {
    if(slideMenuAddresstxt!=null)
        slidemenuNametxt.text = name
    }

    override fun updateAddressText(address: String) {
       if(slideMenuAddresstxt !=null)
        slideMenuAddresstxt.text=address
    }

    override fun gettheSharedPreferences(): SharedPreferences? {

        return getSharedPreferences(PREF_NAME, MODE_PRIVATE)
    }

    override fun getfromSupportFragmentManager(): FragmentManager {
        return supportFragmentManager
    }
    fun checkInternetConnectivity() : Boolean
    {

        val connectivityManager : ConnectivityManager
                =  getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetworkInfo = connectivityManager.getActiveNetworkInfo()
        if(activeNetworkInfo != null && activeNetworkInfo.isConnected())
        {

            return true
//do nothing
        }
        else
        {       Log.d("internet", "internet")
            val builder = AlertDialog.Builder(this)
            //set title for alert dialog
            builder.setTitle("No network connection")
            //set message for alert dialog
            builder.setMessage("please check your connection")
           // builder.setIcon(android.R.drawable.)

            //performing positive action
            builder.setPositiveButton("OK"){dialogInterface, which ->
                checkInternetConnectivity()
            }
            //performing cancel action
            builder.setNeutralButton("Cancel"){dialogInterface , which ->
              checkInternetConnectivity()
            }
            val alertDialog: AlertDialog = builder.create()
            // Set other dialog properties
            alertDialog.setCancelable(false)
            alertDialog.show()
            return false
        }
     //   return activeNetworkInfo != null && activeNetworkInfo.isConnected()

    }
}