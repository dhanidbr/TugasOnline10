package com.example.tugas10

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData

class WordViewModel (application: Application) : AndroidViewModel(application) {
    private var repository: WordRepository =
        WordRepository(application)
    private var allWord: LiveData<List<Word>> = repository.getAllWords()

    fun insert(word: Word) {
        repository.insert(word)
    }

    fun update(word: Word) {
        repository.update(word)
    }

    fun delete(word: Word) {
        repository.delete(word)
    }

    fun deleteAllWord() {
        repository.deleteAllWords()
    }

    fun getAllWord(): LiveData<List<Word>> {
        return allWord
    }
}