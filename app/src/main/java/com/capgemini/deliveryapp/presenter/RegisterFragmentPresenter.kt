package com.capgemini.deliveryapp.presenter

import android.content.Intent
import android.util.Log
import android.widget.Toast
import com.capgemini.deliveryapp.Repository.DBWrapper
import com.capgemini.deliveryapp.view.deliveryactivity.DeliveryActivity
import com.capgemini.firebasedemo.AppData.FireBaseWrapper
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class RegisterFragmentPresenter(val view: View) {
      val fAuth : FirebaseAuth
       init {
                fAuth = FirebaseAuth.getInstance()
       }
        fun validatePassword(password: String): Boolean {
                if (password.isNullOrEmpty() || password.length < 6) {
                    view.showToast("password must be at-least 6 characters")

                        return false

                }
                else
                        return true
        }
                fun validateEmail(email: String) : Boolean{
                        if (email.isNullOrEmpty() || email.length<6) {
                            view.showToast("Enter a valid email")
                            return false
                        }
                        else
                        {
                                return true
                        }
                }

                fun CheckUserLoggedIn(navigate: () -> Unit) {
                    val fAuth = FirebaseAuth.getInstance()
                    if (fAuth.currentUser != null) {
                        //Toast.makeText(activity,  "User Already Logged In", Toast.LENGTH_LONG).show()
                        Log.d("current user", fAuth.currentUser.toString())
                        val wrapper = FireBaseWrapper()
                        wrapper.getUserFromId(fAuth.currentUser.uid) {
                            Log.d("obtainedusernamereg", it.name)
                        }
                       navigate()
                    }

                }

                fun createFirebaseUserWithEmailAndPassword(name: String, phone: String,email: String, password: String, navigate : ()->(Unit)) {
                      view.showProgressBar()
                        fAuth.createUserWithEmailAndPassword(email, password)
                                .addOnCompleteListener {
                                        if (it.isSuccessful) {
                                                //store new user data into firebase
                                                Log.d("register", "success")
                                                StoreInfoOfCreatedUser(name,phone,email,password)
                                            navigate()

                                        } else {
                                              view.showToast("${it.exception?.message}")
                                            view.hideProgressBar()
                                                Log.d("register",
                                                        "fail :${it.exception?.message}")
                                        }
                                }

                }

                fun StoreInfoOfCreatedUser(name: String, phone: String,email: String, password: String) {
                   // progress
                        val UID = fAuth.currentUser.uid
                       val  fstore = FirebaseFirestore.getInstance()
                        val documentReference = fstore
                                .collection("users")
                                .document(UID)

                        val currentUser = HashMap<String, Any>()
                        currentUser["name"] = name
                        currentUser["phone"] = phone
                        currentUser["email"] = email
                      //  currentUser["password"] = password (getting rid of password)

                        documentReference.set(currentUser).addOnCompleteListener {
                                if (it.isSuccessful) {
                                        Log.d("user", "created : $currentUser")

                                        view.showToast("user created")
                                }
                          //  progress

                        }


                }



    interface View {

        fun showProgressBar()
        fun hideProgressBar()
        fun navigateToDelivery()
        fun showToast(message: String?)
    }
}