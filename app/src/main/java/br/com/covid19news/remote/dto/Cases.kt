package br.com.covid19news.remote.dto

import android.os.Parcelable
import com.squareup.moshi.Json
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Cases(
    @Json(name = "new") val new: String?,
    @Json(name = "active") val active: Int,
    @Json(name = "critical") val critical: Int,
    @Json(name = "recovered") val recovered: Int,
    @Json(name = "total") val total: Int
) : Parcelable