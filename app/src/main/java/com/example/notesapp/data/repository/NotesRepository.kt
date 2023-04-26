package com.example.notesapp.data.repository

import com.example.notesapp.data.Note
import com.example.notesapp.data.dataBase.model.NoteEntity
import kotlinx.coroutines.flow.Flow

interface NotesRepository{
    fun getNotes(): Flow<List<Note>>

    suspend fun addNote(text:String)

    fun deleteNote(note: NoteEntity)
}