package com.example.notesapp.data.repository

import com.example.notesapp.data.Note
import com.example.notesapp.data.NotesDataSource
import com.example.notesapp.data.dataBase.model.NoteEntity
import com.example.notesapp.data.mappers.NotesMapper
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class NotesRepositoryImpl(
    private val notesDataSource: NotesDataSource = NotesDataSource,
    private val notesMapper: NotesMapper = NotesMapper()
): NotesRepository {
    override fun getNotes(): Flow<List<Note>> {
        return notesDataSource.getNotes().map { list ->
            list.map { notesMapper.fromEntityToUiModel(it) }
        }
    }

    override suspend fun addNote(text: String) {
        notesDataSource.addNote(text)
    }

    override fun deleteNote(note: NoteEntity){
        notesDataSource.deleteNote(note)
    }
}