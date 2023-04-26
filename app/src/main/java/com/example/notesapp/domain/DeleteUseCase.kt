package com.example.notesapp.domain

import com.example.notesapp.data.dataBase.model.NoteEntity
import com.example.notesapp.data.repository.NotesRepository
import com.example.notesapp.data.repository.NotesRepositoryImpl

class DeleteUseCase(
    private val notesRepository: NotesRepository = NotesRepositoryImpl()
) {
    fun execute(note: NoteEntity){
        notesRepository.deleteNote(note)
    }
}