package com.example.notesapp.data.dataBase

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.notesapp.data.dataBase.model.NoteEntity

@Database(entities = [NoteEntity::class], version = 1)
abstract class NotesDB: RoomDatabase() {
    abstract fun notesDAO(): NotesDAO
}