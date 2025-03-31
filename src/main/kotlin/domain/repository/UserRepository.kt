package com.example.domain.repository

import com.example.data.model.requests.UserModel

interface UserRepository {
    suspend fun getUserByEmail(userEmail: String): UserModel?

    suspend fun insertUser(userModel: UserModel)
}