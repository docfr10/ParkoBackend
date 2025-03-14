package com.example.domain.usecase

import com.auth0.jwt.JWTVerifier
import com.example.authentication.JwtService
import com.example.data.model.repository.UserRepositoryImp
import com.example.data.model.tables.UserModel

class UserUseCase(private val userRepositoryImp: UserRepositoryImp, private val jwtService: JwtService) {
    suspend fun getUserByEmail(userEmail: String) = userRepositoryImp.getUserByEmail(userEmail = userEmail)

    suspend fun insertUser(userModel: UserModel) = userRepositoryImp.insertUser(userModel = userModel)

    fun generateToken(userModel: UserModel): String = jwtService.generateToken(userModel = userModel)

    fun getGwtVerifier(): JWTVerifier = jwtService.getVerifier()
}