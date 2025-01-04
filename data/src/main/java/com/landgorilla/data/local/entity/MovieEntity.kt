package com.landgorilla.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class MovieEntity(
    val page: Int,
    @PrimaryKey val name: String,
    val url: String,
)