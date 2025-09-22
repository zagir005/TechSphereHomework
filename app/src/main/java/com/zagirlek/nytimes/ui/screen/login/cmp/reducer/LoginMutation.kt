package com.zagirlek.nytimes.ui.screen.login.cmp.reducer

import com.zagirlek.nytimes.core.base.reducer.Mutation

sealed class LoginMutation: Mutation {
    data class LoginTextChanged(val text: String): LoginMutation()

    data class PasswordTextChanged(val text: String): LoginMutation()
}