package com.example.notesapp.data

import com.example.notesapp.data.dataBase.NotesDBObject
import com.example.notesapp.data.dataBase.model.NoteEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow

object NotesDataSource {

    fun getNotes(): Flow<List<NoteEntity>>{
        return  NotesDBObject.dao?.getNotes() ?: emptyFlow()
    }

    suspend fun addNote(text:String){
        NotesDBObject.dao?.addNote(NoteEntity(text = text))
    }

    fun deleteNote(note: NoteEntity){
        NotesDBObject.dao?.deleteNote(note)
    }
}