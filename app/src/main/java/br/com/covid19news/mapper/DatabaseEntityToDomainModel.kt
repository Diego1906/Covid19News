package br.com.covid19news.mapper

import br.com.covid19news.R
import br.com.covid19news.application.App
import br.com.covid19news.data.ResponseEntity
import br.com.covid19news.domain.CasesDomainModel
import br.com.covid19news.domain.DeathsDomainModel
import br.com.covid19news.domain.ResponseDomainModel
import br.com.covid19news.domain.TestsDomainModel
import br.com.covid19news.util.TypeSearch
import br.com.covid19news.util.onToUpperCase

fun List<ResponseEntity>.mapToDomainModel() = map { it.mapToDomainModel() }

fun ResponseEntity.mapToDomainModel() = ResponseDomainModel(
    country = when (this.country) {
        TypeSearch.All.name.onToUpperCase() -> {
            App.getContext().getString(R.string.entire_world).onToUpperCase()
        }
        else -> this.country
    },
    cases = CasesDomainModel(
        new = this.cases.newer,
        active = this.cases.active,
        critical = this.cases.critical,
        recovered = this.cases.recovered,
        total = this.cases.total
    ),
    deaths = DeathsDomainModel(
        new = this.deaths.newer,
        total = this.deaths.total
    ),
    tests = TestsDomainModel(
        total = this.tests.total
    ),
    day = this.day,
    time = this.time
)