package com.greydog.fitness

import android.preference.PreferenceManager
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.test.platform.app.InstrumentationRegistry
import com.github.tomakehurst.wiremock.core.WireMockConfiguration
import com.github.tomakehurst.wiremock.junit.WireMockRule
import com.greydog.fitness.screens.LoginScreen
import com.greydog.fitness.screens.StepsScreen
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class LoginTest: BaseInstrumentedTest() {
    private val loginScreen = LoginScreen()
    private val stepsScreen = StepsScreen()

    private val config : WireMockConfiguration = setupWireMockMappings()

    @get:Rule
    val wireMockRule = WireMockRule(config)

    @Before
    fun before() {
        val context = InstrumentationRegistry.getInstrumentation().targetContext
        val prefs = PreferenceManager.getDefaultSharedPreferences(context)
        prefs.edit().putString("environment", "http://127.0.0.1:8080")
    }

    @Test
    fun checkContactDetailsLoad() {
        launchFragmentInContainer<LoginFragment>()

        loginScreen.checkTitle("Login")
    }

    @Test
    fun checkLoginAndNavigateToSteps() {
        launchFragmentInContainer<LoginFragment>()

        loginScreen.checkTitle("Login")

        stepsScreen.checkTitleText("Steps")
        stepsScreen.checkStepsText("0")
    }
}