package com.greydog.datamodels

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class User(
        val email: String? = null,
        val step_count: Int = 0
)