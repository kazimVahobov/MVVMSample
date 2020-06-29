package com.example.mvvmsample.data.repositories

import com.example.mvvmsample.data.network.API
import com.example.mvvmsample.data.network.SafeApiRequest
import com.example.mvvmsample.data.network.responses.AuthResponse

class UserRepository: SafeApiRequest() {

    suspend fun userLogin(email: String, password: String): AuthResponse {
        return apiRequest { API().userLogin(email, password) }
    }
}
