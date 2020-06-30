package com.example.mvvmsample.data.repositories

import com.example.mvvmsample.data.db.AppDB
import com.example.mvvmsample.data.db.entities.User
import com.example.mvvmsample.data.network.API
import com.example.mvvmsample.data.network.SafeApiRequest
import com.example.mvvmsample.data.network.responses.AuthResponse

class UserRepository(private val api: API, private val db: AppDB) : SafeApiRequest() {

    suspend fun userLogin(email: String, password: String): AuthResponse {
        return apiRequest { api.userLogin(email, password) }
    }

    suspend fun saveUser(user: User) = db.getUserDao().insert(user)

    fun getUser() = db.getUserDao().getUser()
}
