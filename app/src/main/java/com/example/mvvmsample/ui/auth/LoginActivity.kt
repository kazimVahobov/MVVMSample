package com.example.mvvmsample.ui.auth

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProviders
import com.example.mvvmsample.R
import com.example.mvvmsample.data.db.entities.User
import com.example.mvvmsample.databinding.ActivityLoginBinding
import com.example.mvvmsample.utils.hide
import com.example.mvvmsample.utils.show
import com.example.mvvmsample.utils.snackBar
import com.example.mvvmsample.utils.toast
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity(), AuthListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding: ActivityLoginBinding =
            DataBindingUtil.setContentView(this, R.layout.activity_login)
        val viewModel = ViewModelProviders.of(this).get(AuthViewModel::class.java)

        binding.viewmodel = viewModel
        viewModel.authListener = this
    }

    override fun onStarted() {
        progressBar.show()
    }

    override fun onSuccess(user: User) {
        progressBar.hide()
        rootLayout.snackBar("${user.name} is Logged in")
    }

    override fun onFailure(message: String) {
        progressBar.hide()
        toast(message)
    }
}
