package com.example.notesapp.data.mappers

import com.example.notesapp.data.Note
import com.example.notesapp.data.dataBase.model.NoteEntity

class NotesMapper {
    fun fromEntityToUiModel(entity: NoteEntity): Note{
        return Note(
            id = entity.id,
            text = entity.text
        )
    }

    fun fromUiModelToEntity(note: Note): NoteEntity{
        return NoteEntity(
            id = note.id,
            text = note.text
        )
    }
}