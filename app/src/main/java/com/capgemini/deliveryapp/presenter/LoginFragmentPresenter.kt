package com.capgemini.deliveryapp.presenter

import android.util.Log
import com.capgemini.firebasedemo.AppData.FireBaseWrapper
import com.google.firebase.auth.FirebaseAuth

class LoginFragmentPresenter(val view: LoginView) {
    val fAuth: FirebaseAuth = FirebaseAuth.getInstance()




    fun isEmailValid(username: String): Boolean {

        //view.showToast("email is invalid")
        return !(username.isNullOrEmpty())


    }

    fun isPasswordValid(password: String): Boolean {
 //       view.showToast("password is invalid")
        return !(password.isNullOrEmpty())
    }

    fun signInFirebaseWithEmailAndPassword(
        email: String,
        password: String,
        navigate: () -> (Unit)
    ) {
        view.showProgressBar()
        fAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener {
            if (it.isSuccessful) {
              //  view.showToast("Login Successful")
                Log.d("registermvp", "success")
                navigate()
            } else {
                view.showToast("Error! ${it.exception?.message}")
                Log.d("register", "fail :: ${it.exception?.message}")
            }
            view.hideProgressBar()
        }
    }

    fun sendResetEmail(email: String) {
        val wrapper = FireBaseWrapper()
      wrapper.resetEmail(email){
          Log.d("resetpass","$it")
          view.ShowSnackBar(it)
      }

    }


    interface LoginView {

        fun showProgressBar()
        fun hideProgressBar()
        fun ShowSnackBar(snack : String)
        fun showToast(message: String?)
    }
}