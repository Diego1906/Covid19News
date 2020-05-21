package br.com.covid19news.data

object Crud {
    const val SELECT_COUNT = "SELECT count(*) FROM ${ConfigDatabase.TABLE_NAME}"
    const val SELECT_ALL_COLUMNS =
        "SELECT country, day, time, MAX(cases_newer) as cases_newer, cases_active, cases_critical, cases_recovered, MAX(cases_total) as cases_total, MAX(deaths_newer) as deaths_newer, deaths_total, tests_total FROM ${ConfigDatabase.TABLE_NAME}"
    const val WHERE = "WHERE country = :country"
    const val GROUP_BY = "GROUP BY country, day"
    const val ORDER_BY = "ORDER BY country"
}