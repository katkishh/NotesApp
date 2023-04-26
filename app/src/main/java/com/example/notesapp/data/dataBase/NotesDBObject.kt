package com.example.notesapp.data.dataBase

import androidx.room.Room
import com.example.notesapp.NotesApplication

object NotesDBObject {
    private const val DB_NAME = "notesDB"

    var dao: NotesDAO? = null
        private set
        get(){
            return if (field == null){
                field = db?.notesDAO()
                field
            } else {
                field
            }
        }

    var db: NotesDB? = null
        private set
        get(){
            return if (field == null){
                field = getNotesDB()
                field
            } else{
                field
            }
        }

    private fun getNotesDB(): NotesDB? {
        return NotesApplication.getApplicationContext()?.let { context ->
            Room.databaseBuilder(
                context,
                NotesDB::class.java,
                DB_NAME,
            ).build()
        }
    }
}