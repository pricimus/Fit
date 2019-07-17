package com.greydog.datamodels

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Steps(
        val step_count: Int = 0
)