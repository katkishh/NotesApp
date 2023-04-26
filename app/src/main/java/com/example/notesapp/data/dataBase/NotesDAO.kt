package com.example.notesapp.data.dataBase

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.notesapp.data.dataBase.model.NoteEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface NotesDAO {
    @Insert
    suspend fun addNote(note: NoteEntity): Long

    @Query("SELECT * FROM notes")
    fun getNotes(): Flow<List<NoteEntity>>

    @Delete
    fun deleteNote(note: NoteEntity)
}