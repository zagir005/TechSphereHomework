package com.zagirlek.authhomework.ui.screen.login.cmp.state

data class TextFieldState (
    val value: String = "",
    val error: String? = null
){
    fun hasError() = error != null
}