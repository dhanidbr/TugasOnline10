package com.example.tugas10

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface WordDao {

    @Insert
    fun insert(word: Word)

    @Update
    fun update(word: Word)

    @Delete
    fun delete(word: Word)

    @Query("DELETE FROM word_table")
    fun deleteAllWord()

    @Query("SELECT * FROM word_table ORDER BY priority DESC")
    fun getAllWord(): LiveData<List<Word>>

}