package br.com.covid19news.data

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface StatisticsDao {
    @Query(
        "SELECT " +
                "id, country, day, time, cases_active, cases_critical, cases_recovered, " +
                "MAX(cases_total) as cases_total, deaths_total, tests_total " +
                "FROM currentstatistics " +
                "GROUP BY country, day " +
                "ORDER BY country"
    )
    fun getAll(): LiveData<List<ResponseEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(vararg response: ResponseEntity)
}