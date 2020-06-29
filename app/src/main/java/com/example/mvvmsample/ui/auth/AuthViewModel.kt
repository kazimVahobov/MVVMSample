package com.example.mvvmsample.ui.auth

import android.view.View
import androidx.lifecycle.ViewModel
import com.example.mvvmsample.data.repositories.UserRepository
import com.example.mvvmsample.utils.ApiExceptions
import com.example.mvvmsample.utils.Coroutines

class AuthViewModel : ViewModel() {
    var email: String? = null
    var password: String? = null

    var authListener: AuthListener? = null

    fun onLoginBtnClick(view: View) {
        authListener?.onStarted()
        if (email.isNullOrEmpty() || password.isNullOrEmpty()) {
            authListener?.onFailure("Invalid email or password")
            return
        }

        Coroutines.main {
            try {
                val authResponse = UserRepository().userLogin(email!!, password!!)
                authResponse.user?.let {
                    authListener?.onSuccess(it)
                    return@main
                }
                authListener?.onFailure(authResponse.message!!)
            } catch (e: ApiExceptions) {
                authListener?.onFailure(e.message!!)
            }
        }
    }
}