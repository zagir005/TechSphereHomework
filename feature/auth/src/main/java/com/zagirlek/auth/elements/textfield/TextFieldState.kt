package com.zagirlek.auth.elements.textfield

import com.zagirlek.auth.elements.textfield.textfielderror.TextFieldError

data class TextFieldState<E: TextFieldError> (
    val value: String = "",
    val error: E?  = null
){
    fun isNotEmpty() = value.isNotEmpty()

    fun isValid() = error == null
}