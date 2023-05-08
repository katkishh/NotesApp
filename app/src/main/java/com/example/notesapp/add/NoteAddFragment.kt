package com.example.notesapp.add

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.NavController
import androidx.navigation.NavDirections
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.notesapp.R
import com.example.notesapp.databinding.FragmentAddNoteBinding
import com.example.notesapp.list.NoteListViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class NoteAddFragment: Fragment(R.layout.fragment_add_note) {
    private val binding by viewBinding(FragmentAddNoteBinding::bind)
    private val viewModelList by viewModels<NoteListViewModel>()

    private fun NavController.safeNavigate(direction: NavDirections) {
        currentDestination?.getAction(direction.actionId)?.run { navigate(direction) }
    }

    private fun submitNote(text:String, image: ByteArray?){
        viewModelList.onAddClicked(text, image)

        findNavController().safeNavigate(
            NoteAddFragmentDirections.actionNoteAddFragmentToNoteListFragment()
        )
    }

    private val pickMedia = registerForActivityResult(ActivityResultContracts.PickVisualMedia()){ uri ->
        if (uri != null) {
            Log.d("PhotoPicker", "Selected URI: $uri")
        } else {
            Log.d("PhotoPicker", "No media selected")
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(binding){
            toolbar.setNavigationOnClickListener {
                val text = editTextNoteText.text.toString()
                
                submitNote(text)
            }

            fabSubmitNote.setOnClickListener {
                val text = editTextNoteText.text.toString()
                submitNote(text)
            }

            buttonAddImage.setOnClickListener {
                pickMedia.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))

                //везде добавить поле, где хранится фото и прописать соответствующие функции!!
            }
        }



    }
}