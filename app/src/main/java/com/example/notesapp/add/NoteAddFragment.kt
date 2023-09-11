package com.example.notesapp.add

import android.net.Uri
import android.os.Bundle
import android.util.Base64
import android.util.Log
import android.view.View
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.NavController
import androidx.navigation.NavDirections
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.notesapp.R
import com.example.notesapp.databinding.FragmentAddNoteBinding
import com.example.notesapp.list.NoteListViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class NoteAddFragment: Fragment(R.layout.fragment_add_note) {
    private val binding by viewBinding(FragmentAddNoteBinding::bind)
    private val viewModelList by viewModels<NoteListViewModel>()
    private val args by navArgs<NoteAddFragmentArgs>()

    private val pickMedia = registerForActivityResult(ActivityResultContracts.PickVisualMedia()){ uri ->
        if (uri != null) {
            image = uri
        } else {
            Log.d("PhotoPicker", "No media selected")
        }
    }

    private var image: Uri? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(binding){
            if(args.note != null){
                args.note?.let { note ->
                    editTextNoteText.setText(note.text)
                    note.image?.let {img ->
                        imageViewImage.setImageURI(
                            getUriFromByteArray(img)
                        )
                        visibilitiesWithImage()
                        image = getUriFromByteArray(img)
                    }

                    toolbar.setNavigationOnClickListener {
                        editNote(note.id, image)
                    }

                    fabSubmitNote.setOnClickListener {
                        editNote(note.id, image)
                    }
                }
            }
            else{
                toolbar.setNavigationOnClickListener {
                    submitNote(image)
                }

                fabSubmitNote.setOnClickListener {
                    submitNote(image)
                }
            }

            buttonAddImage.setOnClickListener {
                pickMedia.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
                image?.let {
                    visibilitiesWithImage()
                }
            }

            imageViewCancel.setOnClickListener {
                image = null
                visibilitiesWithoutImage()
            }
        }


    }

    private fun NavController.safeNavigate(direction: NavDirections) {
        currentDestination?.getAction(direction.actionId)?.run { navigate(direction) }
    }

    private fun submitNote(image: Uri?){
        val text = binding.editTextNoteText.text.toString()
        viewModelList.onAddClicked(text, image)

        findNavController().safeNavigate(
            NoteAddFragmentDirections.actionNoteAddFragmentToNoteListFragment()
        )
    }

    private fun editNote(id: Long, image: Uri?){
        val text = binding.editTextNoteText.text.toString()
        viewModelList.onEditClicked(id, text, image)

        findNavController().safeNavigate(
            NoteAddFragmentDirections.actionNoteAddFragmentToNoteListFragment()
        )
    }

    private fun visibilitiesWithImage(){
        with(binding){
            buttonAddImage.visibility = View.GONE
            imageViewImage.visibility = View.VISIBLE
            imageViewCancel.visibility = View.VISIBLE
        }
    }

    private fun visibilitiesWithoutImage(){
        with(binding){
            imageViewCancel.visibility = View.GONE
            imageViewImage.visibility = View.GONE
            buttonAddImage.visibility = View.VISIBLE
        }
    }

    private fun getUriFromByteArray(byteArray: ByteArray): Uri {
        return Uri.parse("data:image/jpeg;base64," + Base64.encodeToString(byteArray, Base64.NO_WRAP))
    }


}