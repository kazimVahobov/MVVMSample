package com.example.mvvmsample.ui.auth

interface AuthListener {
    fun onStarted()
    fun onSuccess()
    fun onFailure(message: String)
}