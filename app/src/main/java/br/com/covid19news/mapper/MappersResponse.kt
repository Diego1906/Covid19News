package br.com.covid19news.mapper

import br.com.covid19news.domain.*
import br.com.covid19news.remote.dto.*

fun DataStatistics.mapToModel() = DataStatisticsModel(
    get = this.get,
    //parameters = this.parameters.map { it.mapToModel() },
    //errors = this.errors.map { it.mapToModel() },
    results = this.results,
    response = this.response?.map { it.mapToModel() }
)

fun Response.mapToModel() = ResponseModel(
    country = this.country,
    cases = this.cases?.mapToModel(),
    deaths = this.deaths?.mapToModel(),
    tests = this.tests?.mapToModel(),
    day = this.day,
    time = this.time
)

fun Cases.mapToModel() = CasesModel(
    new = this.new,
    active = this.active,
    critical = this.critical,
    recovered = this.recovered,
    total = this.total
)

fun Deaths.mapToModel() = DeathsModel(
    new = this.new,
    total = this.total
)

fun Tests.mapToModel() = TestsModel(
    total = this.total
)

fun Parameters.mapToModel() = ParametersModel(
    country = this.country
)

fun Errors.mapToModel() = ErrorsModel(
    errors = this.errors
)
