package com.example.notesapp.domain

import com.example.notesapp.data.repository.NotesRepository
import javax.inject.Inject

class AddNoteUseCase @Inject constructor(
    private val notesRepository: NotesRepository
) {
    suspend fun execute(text:String, image: ByteArray?){
        notesRepository.addNote(text, image)
    }
}