package com.example.tugas10

import android.content.Context
import android.os.AsyncTask
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase

@Database(entities = [Word::class], version = 1)
abstract class WordRoomDatabase : RoomDatabase() {

    abstract fun wordDao(): WordDao


    companion object {
        private var instance: WordRoomDatabase? = null

        fun getInstance(context: Context): WordRoomDatabase? {
            if (instance == null) {
                synchronized(WordRoomDatabase::class) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        WordRoomDatabase::class.java, "word_database"
                    )
                        .fallbackToDestructiveMigration()
                        .addCallback(roomCallback)
                        .build()
                }
            }
            return instance
        }

        fun destroyInstance() {
            instance = null
        }

        private val roomCallback = object : RoomDatabase.Callback() {
            override fun onCreate(db: SupportSQLiteDatabase) {
                super.onCreate(db)
                PopulateDbAsyncTask(instance)
                    .execute()
            }
        }
    }

    class PopulateDbAsyncTask(db: WordRoomDatabase?) : AsyncTask<Unit, Unit, Unit>() {
        private val wordDao = db?.wordDao()

        override fun doInBackground(vararg p0: Unit?) {
            wordDao?.insert(Word("NAMA 1", "NPM 1", 1))
            wordDao?.insert(Word("NAMA 2", "NPM 2", 2))
        }
    }

}