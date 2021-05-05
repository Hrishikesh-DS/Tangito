package com.capgemini.firebasedemo.AppData

import android.util.Log
import com.capgemini.deliveryapp.Repository.CurrentUser.User
import com.capgemini.firebasedemo.AppData.Menu.Item
import com.capgemini.firebasedemo.AppData.Menu.Orders
import com.capgemini.firebasedemo.AppData.Menu.itemlist
import com.capgemini.firebasedemo.AppData.Restaurants.CityItem
import com.capgemini.firebasedemo.AppData.Restaurants.Location
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.*
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase

interface MyMenuCallback {
    fun onCallback(itemlist: List<Item>)
}



class FireBaseWrapper {
    fun getMenu(mycallback:MyMenuCallback){
        val database: FirebaseDatabase = FirebaseDatabase.getInstance()
        val myRef: DatabaseReference = database.getReference("menu/item")
        val listofItems= mutableListOf<Item>()

        myRef.addValueEventListener(object : ValueEventListener {
            override fun onCancelled(error: DatabaseError) {
                //
            }

            override fun onDataChange(snapshot: DataSnapshot) {


                snapshot.children.forEach {
                    Log.d("menu", it.toString())
                    val i = it.getValue(Item:: class.java)

                    if (i != null) {
                        listofItems.add(i)
                        itemlist.add(i)


                    }


                }
                mycallback.onCallback(listofItems)

            }
        })


    }
    fun getListOfCities(callbackfun : (List<String>)->(Unit))
    {
        //hordcoded now,replace with firebase call later
        val citylist= listOf<String>("bengaluru","noida","pune","kanpur","kolkata","gurugram","hyderabad")
        callbackfun(citylist)

    }

    fun getMenu(callbackfun : (List<Item>)->(Unit)){
        val database: FirebaseDatabase = FirebaseDatabase.getInstance()
        val myRef: DatabaseReference = database.getReference("menu/item")
        val listofItems= mutableListOf<Item>()

        myRef.addValueEventListener(object : ValueEventListener {
            override fun onCancelled(error: DatabaseError) {
                //
            }

            override fun onDataChange(snapshot: DataSnapshot) {


                snapshot.children.forEach {
                    Log.d("menu", it.toString())
                    val i = it.getValue(Item:: class.java)

                    if (i != null) {
                        listofItems.add(i)
                        itemlist.add(i)


                    }


                }
               // mycallback.onCallback(listofItems)
                callbackfun(listofItems)

            }
        })


    }
    fun getLocationsOfCity(cityname: String,callbackfun: (List<Location>) -> Unit)
    {   Log.d("city", "in here")
        val database: FirebaseDatabase = FirebaseDatabase.getInstance()
        val myRef: DatabaseReference = database.getReference("city")
        myRef.addValueEventListener(object : ValueEventListener {
            override fun onCancelled(error: DatabaseError) {
                //
            }

            override fun onDataChange(snapshot: DataSnapshot) {

            snapshot.children.forEach {
                Log.d("city1",it.toString())
                val city = it.getValue(CityItem::class.java)
               Log.d("city.toString",city.toString())

                    Log.d("aaa", city?.locations!![0].address)

                if(city!=null) {
                    if (city.name.equals(cityname,true))
                    {  Log.d("citypicked", city.locations.toString())
                    callbackfun(city.locations)
                    }
                }

            }


            }
        })


    }
    fun getUserFromId(UID : String,callback :(User)->(Unit))
    {
        val  fstore = FirebaseFirestore.getInstance()
        val documentReference = fstore
                .collection("users")
                .document(UID)
        documentReference.get()
                .addOnSuccessListener { document ->
                    if (document != null) {
                        Log.d("getuser", "DocumentSnapshot data: ${document.data}")
                        val user = document.toObject<User>()
                        Log.d("getusersnapshot", "DocumentSnapshot data: ${user.toString()}")
                        if (user != null) {
                            callback(user)
                        }
                    } else {
                        Log.d("getuser", "No such document")
                    }
                }
                .addOnFailureListener { exception ->
                    Log.d("getuser", "get failed with ", exception)
                }

    }
    fun getPreviousOrdersById(UID : String,callback :(List<Orders>)->(Unit))
    {
        val  fstore = FirebaseFirestore.getInstance()
        val documentReference = fstore
            .collection("users")
            .document(UID).collection("orders").orderBy("date")
        documentReference.get()
            .addOnSuccessListener { document ->
                if (document != null) {

                   val orders : List<Orders> = document.toObjects(Orders :: class.java)
                    Log.d("getuserord", orders.toString())
                   // Log.d("getusersnapshot", "DocumentSnapshot data: ${user.toString()}")
                    if (orders != null) {
                        callback(orders)
                    }
                } else {
                    Log.d("getuser", "No such document")
                }
            }
            .addOnFailureListener { exception ->
                Log.d("getuser", "get failed with ", exception)
            }

    }

    //pass user.email
    fun resetEmail(email : String, callback :(String)->(Unit))
    {
        Firebase.auth.sendPasswordResetEmail(email)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        Log.d("reset", "Email sent.")
                            callback("Reset email sent to $email ")



                    }


                }
            .addOnFailureListener {
               callback(it.message.toString())
            }



    }
    //fun creatUserWithCredentials()
}
/*
/ Read from the database
myRef.addValueEventListener(new ValueEventListener() {
    @Override
    public void onDataChange(DataSnapshot dataSnapshot) {
        // This method is called once with the initial value and again
        // whenever data at this location is updated.
        String value = dataSnapshot.getValue(String.class);
        Log.d(TAG, "Value is: " + value);
    }

    @Override
    public void onCancelled(DatabaseError error) {
        // Failed to read value
        Log.w(TAG, "Failed to read value.", error.toException());
    }
});


 */