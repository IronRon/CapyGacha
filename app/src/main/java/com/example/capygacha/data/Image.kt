package com.example.capygacha.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Image")
data class Image (
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    @ColumnInfo(name = "res_file")
    val resFile: String,
    @ColumnInfo(name = "name")
    val name: String,
    @ColumnInfo(name = "summoned")
    val summoned: Boolean = false
)