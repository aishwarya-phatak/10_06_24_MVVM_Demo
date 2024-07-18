package com.bitcode.a10_06_24_mvvm_demo.repository

import com.bitcode.a10_06_24_mvvm_demo.models.User
import com.bitcode.a10_06_24_mvvm_demo.models.UserPostModel
import com.bitcode.a10_06_24_mvvm_demo.models.UserPostResponse
import com.bitcode.a10_06_24_mvvm_demo.models.UsersResponse
import com.bitcode.a10_06_24_mvvm_demo.network.UsersApiService

class UsersRepository(private val usersApiService: UsersApiService) {

    suspend fun fetchUsers(pageNumber : Int) : ArrayList<User>{
        return usersApiService.fetchUsers(pageNumber).users
    }

    suspend fun addUser(
        userPostModel: UserPostModel
    ) : UserPostResponse {
        return usersApiService.addUser(userPostModel)
    }

}