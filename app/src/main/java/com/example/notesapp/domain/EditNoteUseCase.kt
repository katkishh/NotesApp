package com.example.notesapp.domain

import com.example.notesapp.data.dataBase.model.NoteEntity
import com.example.notesapp.data.repository.NotesRepository
import javax.inject.Inject

class EditNoteUseCase @Inject constructor(
    private val notesRepository: NotesRepository
) {
    suspend fun execute(note: NoteEntity){
        notesRepository.editNote(note)
    }
}