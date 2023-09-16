package com.example.notesapp.list

import android.graphics.Bitmap
import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.notesapp.data.Note
import com.example.notesapp.data.dataBase.model.NoteEntity
import com.example.notesapp.domain.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.ByteArrayOutputStream
import javax.inject.Inject

@HiltViewModel
class NoteListViewModel @Inject constructor(
    private val getNotesUseCase: GetNotesUseCase,
    private val addNoteUseCase: AddNoteUseCase,
    private val deleteUseCase: DeleteUseCase,
    private val editNoteUseCase: EditNoteUseCase,
    private val getContentUriUseCase: GetContentUriUseCase,
) : ViewModel() {
    private val _notesListLiveData = MutableLiveData<List<Note>>()
    val notesListLiveData: LiveData<List<Note>> = _notesListLiveData

    fun onAddClicked(text: String?, image: Bitmap?){
        if (image != null) {
            viewModelScope.launch {
                addNoteUseCase.execute(text, getCompressedByteArray(image))
            }
        }
        else{
            viewModelScope.launch {
                addNoteUseCase.execute(text, null)
            }
        }

    }

    fun onDeleteClicked(note: NoteEntity){
        viewModelScope.launch {
            withContext(Dispatchers.IO){deleteUseCase.execute(note)}
        }
    }

    fun onEditClicked(id: Long, text: String?, image: Bitmap?){
        if (image != null) {
            viewModelScope.launch {
                editNoteUseCase.execute(NoteEntity(
                    id,
                    text,
                    getCompressedByteArray(image)
                ))
            }


        }
        else{
            viewModelScope.launch {
                editNoteUseCase.execute(NoteEntity(
                    id,
                    text,
                    null
                ))
            }
        }
    }

    fun getNotes(){
        viewModelScope.launch {
            getNotesUseCase.execute().collect{ list ->
                _notesListLiveData.value = list.map { note ->
                    note.copy(
                        text = note.text
                    )
                }
            }
        }
    }

    fun getBitmapFromUri(uri: Uri?):Bitmap?{
        return uri?.let {
            getContentUriUseCase(it)
        }
    }

    private fun getCompressedByteArray(bitmap: Bitmap?):ByteArray?{
        return bitmap?.let {
            val stream = ByteArrayOutputStream()
            it.compress(Bitmap.CompressFormat.JPEG, 100, stream)
            stream.toByteArray()
        }
    }
}