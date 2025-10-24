package com.zagirlek.auth.model

data class AuthToken(
    val userId: Long,
    val tokenType: TokenType
){
    enum class TokenType{
        CLIENT, ADMIN, GUEST
    }
    companion object{
        val GUEST_TOKEN = AuthToken(userId = -1, tokenType = TokenType.GUEST)
    }
}