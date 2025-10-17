package com.zagirlek.auth.model

data class AuthToken(
    val token: String
){
    companion object{
        val DEFAULT_TOKEN = AuthToken("TOKEN")
    }
}