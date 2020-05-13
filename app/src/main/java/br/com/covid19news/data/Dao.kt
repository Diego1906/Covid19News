package br.com.covid19news.data

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface StatisticsDao {

    @Query("SELECT country, day, time, MAX(cases_newer) as cases_newer, cases_active, cases_critical, cases_recovered, MAX(cases_total) as cases_total, MAX(deaths_newer) as deaths_newer, deaths_total, tests_total FROM currentstatistics GROUP BY country, day ORDER BY country")
    fun getAll(): LiveData<List<ResponseEntity>>

    @Query("SELECT country, day, time, MAX(cases_newer) as cases_newer, cases_active, cases_critical, cases_recovered, MAX(cases_total) as cases_total, MAX(deaths_newer) as deaths_newer, deaths_total, tests_total FROM currentstatistics WHERE country = :country GROUP BY country, day ORDER BY country")
    fun getByPrimaryKey(country: String): ResponseEntity

    @Query("SELECT count(*) FROM currentstatistics WHERE country = :country ")
    fun getCountResult(country: String): Int

    @Query("SELECT count(*) FROM currentstatistics")
    fun getCountTotalResult(): Int

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(vararg response: ResponseEntity)
}