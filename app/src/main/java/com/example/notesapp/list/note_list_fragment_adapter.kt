package com.example.notesapp.list

import android.graphics.BitmapFactory
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.notesapp.data.Note
import com.example.notesapp.databinding.ItemNoteBinding
import javax.inject.Inject

class ListFragmentAdapter @Inject constructor() : ListAdapter<Note, ListFragmentAdapter.NoteViewHolder>(diffUtil){

    var onNoteClick : (Note) -> Unit = {}
    var onNoteLongClick: (Note) -> Unit = {}

    inner class NoteViewHolder(
        private val binding: ItemNoteBinding
    ): RecyclerView.ViewHolder(binding.root){
        fun bind(item: Note){
            with(binding){
                root.setOnClickListener { onNoteClick.invoke(item) }
                item.text?.let {
                    if(it.length > 40){
                        val text = it.slice(0..39) + "..."
                        textViewNote.text = text
                    }
                    else{ textViewNote.text = it }
                }
                root.setOnLongClickListener {
                    onNoteLongClick.invoke(item)
                    true
                }
                item.image?.let {
                    imageViewNoteImage.visibility = View.VISIBLE
                    imageViewNoteImage.setImageBitmap(
                        BitmapFactory.decodeByteArray(it, 0, it.size)
                    )
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