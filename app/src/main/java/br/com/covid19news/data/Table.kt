package br.com.covid19news.data

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = ConfigDatabase.TABLE_NAME,
    indices = [Index(value = [ConfigDatabase.INDEX_NAME], unique = true)]
)
data class ResponseEntity(
    @PrimaryKey var country: String,
    @Embedded(prefix = "cases_") var cases: CasesEntity,
    @Embedded(prefix = "deaths_") var deaths: DeathsEntity,
    @Embedded(prefix = "tests_") var tests: TestsEntity,
    var day: String,
    var time: String
)

data class CasesEntity(
    var newer: String = "",
    var active: String = "",
    var critical: String = "",
    var recovered: String = "",
    var total: String = ""
)

data class DeathsEntity(
    var newer: String = "",
    var total: String = ""
)

data class TestsEntity(
    var total: String = ""
)