package com.example.tugas10

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "word_table")
data class Word(

    var title: String,

    var description: String,

    var priority: Int
) {

    @PrimaryKey(autoGenerate = true)
    var id: Int = 0

}