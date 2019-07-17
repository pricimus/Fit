package com.greydog.extensions.testutils

import androidx.annotation.IdRes
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import org.hamcrest.Matchers

@IdRes
fun @receiver:IdRes Int.checkText(text: String) {
    onView(withId(this)).check(
        matches(
            Matchers.allOf(
                isDisplayed(),
                ViewMatchers.withText(Matchers.containsString(text))
            )
        )
    )
}