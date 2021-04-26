package com.capgemini.deliveryapp

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.navigation.findNavController
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

// TODO: Rename parameter arguments, choose names that match

class RegisterFragment : Fragment() {

    lateinit var signT: TextView
    lateinit var nameE: EditText
    lateinit var emailE: EditText
    lateinit var passE: EditText
    lateinit var phoneE: EditText

    lateinit var registerB: Button

    lateinit var fstore: FirebaseFirestore
    val fAuth = FirebaseAuth.getInstance()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        if (fAuth.currentUser != null) {
            Toast.makeText(activity, "User Already Logged In", Toast.LENGTH_LONG).show()
        }

        return inflater.inflate(R.layout.fragment_register, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        signT = view.findViewById<EditText>(R.id.signT)
        nameE = view.findViewById<EditText>(R.id.rnameE)
        emailE = view.findViewById<EditText>(R.id.remailE)
        passE = view.findViewById<EditText>(R.id.rpassE)
        phoneE = view.findViewById<EditText>(R.id.rphoneE)
        registerB = view.findViewById<Button>(R.id.registerB)

        signT.setOnClickListener {
            it.findNavController().navigate(R.id.action_registerFragment_to_loginFragment)
        }

        registerB.setOnClickListener {
            onRegisterClick()
        }

        super.onViewCreated(view, savedInstanceState)
    }

    private fun onRegisterClick() {
        val name = nameE.text.toString().trim()
        val email = emailE.text.toString().trim()
        val pass = passE.text.toString().trim()
        val phone = phoneE.text.toString().trim()

        if (email.isEmpty()) {
            Toast.makeText(activity, "enter an email", Toast.LENGTH_SHORT).show()
        }
        if (pass.length < 6) {
            Toast.makeText(activity, "password must be at-least 6 characters", Toast.LENGTH_SHORT)
                .show()
        }
        fAuth.createUserWithEmailAndPassword(email, pass)
            .addOnCompleteListener {
                if (it.isSuccessful) {
                    Log.d("register", "success")
                    val UID = fAuth.currentUser.uid
                    fstore = FirebaseFirestore.getInstance()
                    val documentReference = fstore
                                            .collection("users")
                                            .document(UID)

                    val currentUser = HashMap<String, Any>()
                    currentUser["name"] = name
                    currentUser["phone"] = phone
                    currentUser["email"] = email
                    currentUser["password"] = pass

                    documentReference.set(currentUser).addOnCompleteListener {
                        if (it.isSuccessful) {
                            Log.d("user", "created : $currentUser")
                        }
                    }


                    Toast.makeText(activity,
                        "User Created", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(activity,
                        "Error! ${it.exception?.message}", Toast.LENGTH_SHORT).show()
                    Log.d("register",
                        "fail :${it.exception?.message}")
                }
            }

    }


}