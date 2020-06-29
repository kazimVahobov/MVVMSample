package com.example.mvvmsample.data.network.responses

import com.example.mvvmsample.data.db.entities.User

data class AuthResponse(
    val isSuccessful: Boolean?,
    val message: String?,
    val user: User?
)