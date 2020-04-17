package br.com.covid19news.mapper

import br.com.covid19news.R
import br.com.covid19news.application.CovidApplication
import br.com.covid19news.domain.*
import br.com.covid19news.remote.dto.*
import br.com.covid19news.util.TypeSearch
import br.com.covid19news.util.onFormatDateTime
import br.com.covid19news.util.removePrefix

fun DataStatistics.mapToModel() = DataStatisticsModel(
    get = this.get,
    //parameters = this.parameters.map { it.mapToModel() },
    //errors = this.errors.map { it.mapToModel() },
    results = this.results,
    response = this.response?.map { it.mapToModel() }
)

fun Response.mapToModel() = ResponseModel(
    country = when (this.country) {
        TypeSearch.ALL.value -> CovidApplication.getContext().getString(R.string.all)
        else -> this.country
    },
    cases = this.cases?.mapToModel(),
    deaths = this.deaths?.mapToModel(),
    tests = this.tests?.mapToModel(),
    day = this.day,
    time = this.time?.onFormatDateTime()
)

fun Cases.mapToModel() = CasesModel(
    new = this.new?.removePrefix(),
    active = this.active,
    critical = this.critical,
    recovered = this.recovered,
    total = this.total
)

fun Deaths.mapToModel() = DeathsModel(
    new = this.new?.removePrefix(),
    total = this.total
)

fun Tests.mapToModel() = TestsModel(
    total = this.total ?: CovidApplication.getContext().getString(R.string.quantity_not_reported)
)

fun Parameters.mapToModel() = ParametersModel(
    country = this.country
)

fun Errors.mapToModel() = ErrorsModel(
    errors = this.errors
)

