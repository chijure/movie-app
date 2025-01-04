package com.landgorilla.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.landgorilla.data.local.entity.MovieEntity

@Dao
interface MovieDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMovieList(movieList: List<MovieEntity>)

    @Query("SELECT * FROM MovieEntity WHERE page = :page")
    suspend fun getMovieList(page: Int): List<MovieEntity>

    @Query("SELECT * FROM MovieEntity WHERE page <= :page")
    suspend fun getAllMovieList(page: Int): List<MovieEntity>

    @Query("SELECT * FROM MovieEntity WHERE name LIKE '%' || :query || '%'")
    suspend fun searchMovie(query: String): List<MovieEntity>
}