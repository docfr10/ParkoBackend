package com.example.domain.usecase

import com.example.data.model.requests.UserModel
import com.example.data.repositoryimp.UserRepositoryImp

class UserUseCase(private val userRepositoryImp: UserRepositoryImp) {
    suspend fun getUserByEmail(userEmail: String) = userRepositoryImp.getUserByEmail(userEmail = userEmail)

    suspend fun insertUser(userModel: UserModel) = userRepositoryImp.insertUser(userModel = userModel)
}