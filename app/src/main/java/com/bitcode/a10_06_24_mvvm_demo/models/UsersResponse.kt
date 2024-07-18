package com.bitcode.a10_06_24_mvvm_demo.models

import com.google.gson.annotations.SerializedName

data class UsersResponse(
    @SerializedName("data")
    var users : ArrayList<User>
)
