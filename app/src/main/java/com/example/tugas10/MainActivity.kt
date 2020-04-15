package com.example.tugas10

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    companion object {
        const val ADD_WORD_REQUEST = 1
        const val EDIT_WORD_REQUEST = 2
    }

    private lateinit var wordViewModel: WordViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        buttonAddNote.setOnClickListener {
            startActivityForResult(
                Intent(this, NewWordActivity::class.java),
                ADD_WORD_REQUEST
            )
        }

        recycler_view.layoutManager = LinearLayoutManager(this)
        recycler_view.setHasFixedSize(true)

        var adapter = WordListAdapter()

        recycler_view.adapter = adapter

        wordViewModel = ViewModelProviders.of(this).get(WordViewModel::class.java)

        wordViewModel.getAllWord().observe(this, Observer<List<Word>> {
            adapter.submitList(it)
        })

        ItemTouchHelper(object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT.or(
            ItemTouchHelper.RIGHT)) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                wordViewModel.delete(adapter.getWordAt(viewHolder.adapterPosition))
                Toast.makeText(baseContext, "Word Deleted!", Toast.LENGTH_SHORT).show()
            }
        }
        ).attachToRecyclerView(recycler_view)

        adapter.setOnItemClickListener(object : WordListAdapter.OnItemClickListener {
            override fun onItemClick(word: Word) {
                var intent = Intent(baseContext, NewWordActivity::class.java)
                intent.putExtra(NewWordActivity.EXTRA_ID, word.id)
                intent.putExtra(NewWordActivity.EXTRA_TITLE, word.title)
                intent.putExtra(NewWordActivity.EXTRA_DESCRIPTION, word.description)
                intent.putExtra(NewWordActivity.EXTRA_PRIORITY, word.priority)

                startActivityForResult(intent, EDIT_WORD_REQUEST)
            }
        })
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        return when (item?.itemId) {
            R.id.delete_all_word -> {
                wordViewModel.deleteAllWord()
                Toast.makeText(this, "All Word deleted!", Toast.LENGTH_SHORT).show()
                true
            }
            else -> {
                super.onOptionsItemSelected(item)
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == ADD_WORD_REQUEST && resultCode == Activity.RESULT_OK) {
            val newWord = Word(
                data!!.getStringExtra(NewWordActivity.EXTRA_TITLE),
                data.getStringExtra(NewWordActivity.EXTRA_DESCRIPTION),
                data.getIntExtra(NewWordActivity.EXTRA_PRIORITY, 1)
            )
            wordViewModel.insert(newWord)

            Toast.makeText(this, "Word saved!", Toast.LENGTH_SHORT).show()
        } else if (requestCode == EDIT_WORD_REQUEST && resultCode == Activity.RESULT_OK) {
            val id = data?.getIntExtra(NewWordActivity.EXTRA_ID, -1)

            if (id == -1) {
                Toast.makeText(this, "Could not update! Error!", Toast.LENGTH_SHORT).show()
            }

            val updateWord = Word(
                data!!.getStringExtra(NewWordActivity.EXTRA_TITLE),
                data.getStringExtra(NewWordActivity.EXTRA_DESCRIPTION),
                data.getIntExtra(NewWordActivity.EXTRA_PRIORITY, 1)
            )
            updateWord.id = data.getIntExtra(NewWordActivity.EXTRA_ID, -1)
            wordViewModel.update(updateWord)

        } else {
            Toast.makeText(this, "Word not saved!", Toast.LENGTH_SHORT).show()
        }


    }
}