package com.example.notesapp.data.repository

import com.example.notesapp.data.Note
import com.example.notesapp.data.dataBase.NotesDAO
import com.example.notesapp.data.dataBase.model.NoteEntity
import com.example.notesapp.data.mappers.NotesMapper
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class NotesRepositoryImpl @Inject constructor(
    private val notesMapper: NotesMapper,
    private val notesDAO: NotesDAO,
): NotesRepository {
    override fun getNotes(): Flow<List<Note>> {
        return notesDAO.getNotes().map { list ->
            list.map { notesMapper.fromEntityToUiModel(it) }
        }
    }

    override suspend fun addNote(text: String?, image: ByteArray?) {
        notesDAO.addNote(NoteEntity(text = text, image = image))
    }

    override suspend fun editNote(note: NoteEntity) {
        notesDAO.editNote(note)
    }

    override fun deleteNote(note: NoteEntity){
        notesDAO.deleteNote(note)
    }
}