package com.greydog.fitness.screens

import com.greydog.fitness.R
import com.greydog.fitness.checkText

class StepsScreen {
    private val title = R.id.stepsTitle
    private val about = R.id.stepsCount

    fun checkTitleText(text: String) {
        title.checkText(text)
    }

    fun checkStepsText(text: String) {
        about.checkText(text)
    }
}