package com.zagirlek.nytimes.ui.screen.login.cmp.state.textfield

import com.zagirlek.nytimes.ui.screen.login.cmp.state.textfield.textfielderror.TextFieldError

data class TextFieldState<E: TextFieldError> (
    val value: String = "",
    val error: E?  = null
){
    fun isNotEmpty() = value.isNotEmpty()

    fun isValid() = error == null
}