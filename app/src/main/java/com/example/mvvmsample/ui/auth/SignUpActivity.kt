package com.example.mvvmsample.ui.auth

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.mvvmsample.R
import com.example.mvvmsample.data.db.entities.User
import com.example.mvvmsample.databinding.ActivitySignUpBinding
import com.example.mvvmsample.ui.home.HomeActivity
import com.example.mvvmsample.utils.hide
import com.example.mvvmsample.utils.show
import com.example.mvvmsample.utils.snackBar
import kotlinx.android.synthetic.main.activity_sign_up.*
import org.kodein.di.KodeinAware
import org.kodein.di.generic.instance
import org.kodein.di.android.kodein

class SignUpActivity : AppCompatActivity(), AuthListener, KodeinAware {

    override val kodein by kodein()
    private val factory by instance<AuthViewModelFactory>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding: ActivitySignUpBinding =
            DataBindingUtil.setContentView(this, R.layout.activity_sign_up)

        val viewModel = ViewModelProviders.of(this, factory).get(AuthViewModel::class.java)

        binding.viewmodel = viewModel
        viewModel.authListener = this

        viewModel.getLoggedInUser().observe(this, Observer { user ->
            if (user != null) {
                Intent(this, HomeActivity::class.java).also {
                    it.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                    startActivity(it)
                }
            }
        })
    }

    override fun onStarted() {
        progressBar.show()
    }

    override fun onSuccess(user: User) {
        progressBar.hide()
        rootLayout.snackBar(user.name!!)
    }

    override fun onFailure(message: String) {
        progressBar.hide()
        rootLayout.snackBar(message)
    }

    override fun onFailure(messageId: Int) {
        progressBar.hide()
        rootLayout.snackBar(getString(messageId))
    }
}
