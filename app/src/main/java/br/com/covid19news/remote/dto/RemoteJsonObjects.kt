package br.com.covid19news.remote.dto

import com.squareup.moshi.Json

data class StatisticsRemote(
    @Json(name = "get") val get: String,
    @Json(name = "results") val results: String,
    @Json(name = "response") val responsesRemote: List<ResponseRemote>
)

data class ResponseRemote(
    @Json(name = "country") val country: String?,
    @Json(name = "cases") val cases: CasesRemote,
    @Json(name = "deaths") val deaths: DeathsRemote,
    @Json(name = "tests") val tests: TestsRemote,
    @Json(name = "day") val day: String?,
    @Json(name = "time") val time: String?
)

data class CasesRemote(
    @Json(name = "new") val new: String?,
    @Json(name = "active") val active: String?,
    @Json(name = "critical") val critical: String?,
    @Json(name = "recovered") val recovered: String?,
    @Json(name = "total") val total: String?
)

data class DeathsRemote(
    @Json(name = "new") val new: String?,
    @Json(name = "total") val total: String?
)

data class TestsRemote(
    @Json(name = "total") val total: String?
)