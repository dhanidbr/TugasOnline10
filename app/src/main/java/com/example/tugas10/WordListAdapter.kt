package com.example.tugas10

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.recyclerview_item.view.*

class WordListAdapter : ListAdapter<Word, WordListAdapter.WordHolder>(DIFF_CALLBACK) {

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Word>() {
            override fun areItemsTheSame(oldItem: Word, newItem: Word): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: Word, newItem: Word): Boolean {
                return oldItem.title == newItem.title && oldItem.description == newItem.description
                        && oldItem.priority == newItem.priority
            }
        }
    }

    private var listener: OnItemClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WordHolder {
        val itemView: View = LayoutInflater.from(parent.context).inflate(R.layout.recyclerview_item, parent, false)
        return WordHolder(itemView)
    }

    override fun onBindViewHolder(holder: WordHolder, position: Int) {
        val currentWord: Word = getItem(position)

        holder.textViewTitle.text = currentWord.title
        holder.textViewPriority.text = currentWord.priority.toString()
        holder.textViewDescription.text = currentWord.description
    }

    fun getWordAt(position: Int): Word {
        return getItem(position)
    }

    inner class WordHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        init {
            itemView.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    listener?.onItemClick(getItem(position))
                }
            }
        }

        var textViewTitle: TextView = itemView.text_view_title
        var textViewPriority: TextView = itemView.text_view_priority
        var textViewDescription: TextView = itemView.text_view_description
    }

    interface OnItemClickListener {
        fun onItemClick(word: Word)
    }

    fun setOnItemClickListener(listener: OnItemClickListener) {
        this.listener = listener
    }
}