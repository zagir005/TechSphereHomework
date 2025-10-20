package com.zagirlek.auth.model

data class AuthToken(
    val token: String
){
    companion object{
        val CLIENT_TOKEN = AuthToken("CLIENT")
        val ADMIN_TOKEN = AuthToken("ADMIN")
    }
}