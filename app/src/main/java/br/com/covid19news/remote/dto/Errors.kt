package br.com.covid19news.remote.dto

import com.squareup.moshi.Json

data class Errors(
    @Json(name = "errors") val errors: String?
)