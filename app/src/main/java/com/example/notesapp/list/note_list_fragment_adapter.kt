package com.example.notesapp.list

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.notesapp.data.Note
import com.example.notesapp.databinding.ItemNoteBinding

class ListFragmentAdapter: ListAdapter<Note, ListFragmentAdapter.NoteViewHolder>(diffUtil){

    var onNoteClick : (Note) -> Unit = {}
    var onNoteLongClick: (Note) -> Unit = {}

    inner class NoteViewHolder(
        private val binding: ItemNoteBinding
    ): RecyclerView.ViewHolder(binding.root){
        fun bind(item: Note){
            with(binding){
                root.setOnClickListener { onNoteClick.invoke(item) }
                textViewNote.text = item.text
                root.setOnLongClickListener {
                    onNoteLongClick.invoke(item)
                    true
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListFragmentAdapter.NoteViewHolder {
        return NoteViewHolder(
            ItemNoteBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ListFragmentAdapter.NoteViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

}

val diffUtil = object : DiffUtil.ItemCallback<Note>(){
    override fun areItemsTheSame(oldItem: Note, newItem: Note): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Note, newItem: Note): Boolean {
        return oldItem == newItem
    }

}