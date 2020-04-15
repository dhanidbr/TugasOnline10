package com.example.tugas10

import android.app.Application
import android.os.AsyncTask
import androidx.lifecycle.LiveData

class WordRepository(application: Application) {

    private var wordDao: WordDao

    private var allWord: LiveData<List<Word>>

    init {
        val database: WordRoomDatabase = WordRoomDatabase.getInstance(
            application.applicationContext
        )!!
        wordDao = database.wordDao()
        allWord = wordDao.getAllWord()
    }

    fun insert(word: Word) {
        val insertWordAsyncTask = InsertWordAsyncTask(wordDao).execute(word)
    }

    fun update(word: Word) {
        val updateWordAsyncTask = UpdateWordAsyncTask(wordDao).execute(word)
    }


    fun delete(word: Word) {
        val deleteWordAsyncTask = DeleteWordAsyncTask(wordDao).execute(word)
    }

    fun deleteAllWords() {
        val deleteAllWordsAsyncTask = DeleteAllWordsAsyncTask(
            wordDao
        ).execute()
    }

    fun getAllWords(): LiveData<List<Word>> {
        return allWord
    }

    companion object {
        private class InsertWordAsyncTask(wordDao: WordDao) : AsyncTask<Word, Unit, Unit>() {
            val WordDao = wordDao

            override fun doInBackground(vararg p0: Word?) {
                WordDao.insert(p0[0]!!)
            }
        }

        private class UpdateWordAsyncTask(wordDao: WordDao) : AsyncTask<Word, Unit, Unit>() {
            val WordDao = wordDao

            override fun doInBackground(vararg p0: Word?) {
                WordDao.update(p0[0]!!)
            }
        }

        private class DeleteWordAsyncTask(wordDao: WordDao) : AsyncTask<Word, Unit, Unit>() {
            val WordDao = wordDao

            override fun doInBackground(vararg p0: Word?) {
                WordDao.delete(p0[0]!!)
            }
        }

        private class DeleteAllWordsAsyncTask(wordDao: WordDao) : AsyncTask<Unit, Unit, Unit>() {
            val WordDao = wordDao

            override fun doInBackground(vararg p0: Unit?) {
                WordDao.deleteAllWord()
            }
        }
    }
}