package br.com.covid19news.mapper

import br.com.covid19news.R
import br.com.covid19news.application.App
import br.com.covid19news.domain.*
import br.com.covid19news.remote.dto.*
import br.com.covid19news.util.*

fun StatisticsRemote.mapToDomainModel() = StatisticsDomainModel(
    get = this.get.onCheckDataReported(),
    results = this.results.onCheckDataReported(),
    responses = this.responsesRemote.map { it.mapToDomainModel() }
)

fun ResponseRemote.mapToDomainModel() = ResponseDomainModel(
    country = when (this.country) {
        App.getContext().getString(R.string.all) -> {
            App.getContext().getString(R.string.entire_world).onToUpperCase()
        }
        else -> this.country?.trim()?.onToUpperCase()
    } ?: this.country.onCheckDataReported(),
    cases = this.cases.mapToDomainModel(),
    deaths = this.deaths.mapToDomainModel(),
    tests = this.tests.mapToDomainModel(),
    day = this.day.onCheckDataReported(),
    time = this.time?.onFormatDateTime() ?: onGetDateCalendar()
)

fun CasesRemote.mapToDomainModel() = CasesDomainModel(
    new = this.new.onRemovePrefix(),
    active = this.active.onCheckDataReported(),
    critical = this.critical.onCheckDataReported(),
    recovered = this.recovered.onCheckDataReported(),
    total = this.total.onCheckDataReported()
)

fun DeathsRemote.mapToDomainModel() = DeathsDomainModel(
    new = this.new.onRemovePrefix(),
    total = this.total.onCheckDataReported()
)

fun TestsRemote.mapToDomainModel() = TestsDomainModel(
    total = this.total.onCheckDataReported()
)