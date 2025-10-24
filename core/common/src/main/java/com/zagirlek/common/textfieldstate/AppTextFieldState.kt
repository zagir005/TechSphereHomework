package com.zagirlek.common.textfieldstate

data class AppTextFieldState<E: AppTextFieldError> (
    val value: String = "",
    val error: E?  = null
){
    fun isNotEmpty() = value.isNotEmpty()

    fun isValid() = error == null
}