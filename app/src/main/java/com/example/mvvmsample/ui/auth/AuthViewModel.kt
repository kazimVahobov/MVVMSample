package com.example.mvvmsample.ui.auth

import android.content.Intent
import android.view.View
import androidx.lifecycle.ViewModel
import com.example.mvvmsample.R
import com.example.mvvmsample.data.repositories.UserRepository
import com.example.mvvmsample.utils.ApiExceptions
import com.example.mvvmsample.utils.Coroutines
import com.example.mvvmsample.utils.NoInternetExceptions

class AuthViewModel(private val repository: UserRepository) : ViewModel() {
    var name: String? = null
    var email: String? = null
    var password: String? = null
    var passwordConfirm: String? = null

    var authListener: AuthListener? = null

    fun getLoggedInUser() = repository.getUser()

    fun onLoginBtnClick(view: View) {
        authListener?.onStarted()
        if (email.isNullOrEmpty()) {
            authListener?.onFailure(R.string.email_is_required)
            return
        }
        if (password.isNullOrEmpty()) {
            authListener?.onFailure(R.string.pwd_is_required)
            return
        }

        Coroutines.main {
            try {
                val authResponse = repository.userLogin(email!!, password!!)
                authResponse.user?.let {
                    authListener?.onSuccess(it)
                    repository.saveUser(it)
                    return@main
                }
                authListener?.onFailure(authResponse.message!!)
            } catch (e: ApiExceptions) {
                authListener?.onFailure(e.message!!)
            } catch (e: NoInternetExceptions) {
                authListener?.onFailure(e.message!!)
            }
        }
    }

    fun openLoginFormBtnClick(view: View) {
        Intent(view.context, LoginActivity::class.java).also {
            view.context.startActivity(it)
        }
    }

    fun openSignUpFormBtnClick(view: View) {
        Intent(view.context, SignUpActivity::class.java).also {
            view.context.startActivity(it)
        }
    }

    fun onSignUpBtnClick(view: View) {
        authListener?.onStarted()
        if (name.isNullOrEmpty()) {
            authListener?.onFailure(R.string.name_is_required)
            return
        }
        if (email.isNullOrEmpty()) {
            authListener?.onFailure(R.string.email_is_required)
            return
        }
        if (password.isNullOrEmpty()) {
            authListener?.onFailure(R.string.pwd_is_required)
            return
        }
        if (passwordConfirm.isNullOrEmpty()) {
            authListener?.onFailure(R.string.confirm_pwd_is_required)
            return
        }
        if (password.toString() != passwordConfirm.toString()) {
            authListener?.onFailure(R.string.pwd_mismatch)
            return
        }

        Coroutines.main {
            try {
                val authResponse = repository.userSignUp(email!!, password!!, name!!)
                authResponse.user?.let {
                    authListener?.onSuccess(it)
                    repository.saveUser(it)
                    return@main
                }
                authListener?.onFailure(authResponse.message!!)
            } catch (e: ApiExceptions) {
                authListener?.onFailure(e.message!!)
            } catch (e: NoInternetExceptions) {
                authListener?.onFailure(e.message!!)
            }
        }
    }
}