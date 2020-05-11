package br.com.covid19news.remote.dto

import android.os.Parcelable
import com.squareup.moshi.Json
import kotlinx.android.parcel.Parcelize

@Parcelize
data class CasesRemote(
    @Json(name = "new") val new: String?,
    @Json(name = "active") val active: String?,
    @Json(name = "critical") val critical: String?,
    @Json(name = "recovered") val recovered: String?,
    @Json(name = "total") val total: String?
) : Parcelable