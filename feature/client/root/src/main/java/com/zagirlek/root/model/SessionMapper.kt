package com.zagirlek.root.model

import com.zagirlek.common.model.User

fun User.toSessionModel(): SessionModel = SessionModel(
    nickname = this.nickname,
    isGuests = false
)