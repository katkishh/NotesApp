package com.example.notesapp.data.mappers

import com.example.notesapp.data.Note
import com.example.notesapp.data.dataBase.model.NoteEntity
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class NotesMapper @Inject constructor() {
    fun fromEntityToUiModel(entity: NoteEntity): Note{
        return Note(
            id = entity.id,
            text = entity.text,
            image = entity.image
        )
    }

    fun fromUiModelToEntity(note: Note): NoteEntity{
        return NoteEntity(
            id = note.id,
            text = note.text,
            image = note.image
        )
    }
}