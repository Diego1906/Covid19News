package br.com.covid19news.domain

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class StatisticsDomainModel(
    val get: String,
    val results: String,
    val responses: List<ResponseDomainModel>
) : Parcelable

@Parcelize
data class ResponseDomainModel(
    val country: String,
    val cases: CasesDomainModel,
    val deaths: DeathsDomainModel,
    val tests: TestsDomainModel,
    val day: String,
    val time: String
) : Parcelable

@Parcelize
data class CasesDomainModel(
    val new: String,
    val active: String,
    val critical: String,
    val recovered: String,
    val total: String
) : Parcelable

@Parcelize
data class DeathsDomainModel(
    val new: String,
    val total: String
) : Parcelable

@Parcelize
data class TestsDomainModel(
    val total: String
) : Parcelable