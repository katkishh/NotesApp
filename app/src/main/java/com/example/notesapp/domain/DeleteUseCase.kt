package com.example.notesapp.domain

import com.example.notesapp.data.dataBase.model.NoteEntity
import com.example.notesapp.data.repository.NotesRepository
import javax.inject.Inject

class DeleteUseCase @Inject constructor(
    private val notesRepository: NotesRepository
) {
    fun execute(note: NoteEntity){
        notesRepository.deleteNote(note)
    }
}