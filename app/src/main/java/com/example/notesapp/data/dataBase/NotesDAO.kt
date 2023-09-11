package com.example.notesapp.data.dataBase

import androidx.room.*
import com.example.notesapp.data.dataBase.model.NoteEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface NotesDAO {
    @Insert
    suspend fun addNote(note: NoteEntity): Long

    @Update
    suspend fun editNote(note: NoteEntity)

    @Query("SELECT * FROM notes")
    fun getNotes(): Flow<List<NoteEntity>>

    @Delete
    fun deleteNote(note: NoteEntity)
}