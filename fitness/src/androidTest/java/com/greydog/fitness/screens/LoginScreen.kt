package com.greydog.fitness.screens

import com.greydog.fitness.R
import com.greydog.fitness.checkText

class LoginScreen {
    private val name = R.id.loginTitle

    fun checkTitle(text: String) {
        name.checkText(text)
    }
}