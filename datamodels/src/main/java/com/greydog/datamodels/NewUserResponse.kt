package com.greydog.datamodels

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class NewUserResponse(
        @field:Json(name = "userid") val userid: String,
        @field:Json(name = "token") val token: String
)