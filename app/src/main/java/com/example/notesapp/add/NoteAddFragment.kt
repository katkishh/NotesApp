package com.example.notesapp.add

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
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
            image = viewModelList.getBitmapFromUri(uri)
            binding.imageViewImage.setImageURI(uri)
            visibilitiesWithImage()
            Log.d("PhotoPicker", image.toString())
        } else {
            Log.d("PhotoPicker", "No media selected")
        }
    }

    private var image: Bitmap? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(binding){
            if(args.note != null){
                args.note?.let { note ->
                    editTextNoteText.setText(note.text)
                    note.image?.let {img ->
                        image = BitmapFactory.decodeByteArray(img, 0, img.size)
                        imageViewImage.setImageBitmap(
                            image
                        )
                        visibilitiesWithImage()
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
                visibilitiesWithoutImage()

                toolbar.setNavigationOnClickListener {
                    submitNote(image)
                }

                fabSubmitNote.setOnClickListener {
                    submitNote(image)
                }
            }

            buttonAddImage.setOnClickListener {
                pickMedia.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
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

    private fun submitNote(image: Bitmap?){
        val text = binding.editTextNoteText.text.toString()
        if(text != "" || image!=null){
            viewModelList.onAddClicked(text, image)

            findNavController().safeNavigate(
                NoteAddFragmentDirections.actionNoteAddFragmentToNoteListFragment()
            )
        }else{
            Toast.makeText(context, R.string.empty_note_warning, Toast.LENGTH_SHORT).show()
        }

    }

    private fun editNote(id: Long, image: Bitmap?){
        val text = binding.editTextNoteText.text.toString()
        if (text != "" || image!=null){
            viewModelList.onEditClicked(id, text, image)

            findNavController().safeNavigate(
                NoteAddFragmentDirections.actionNoteAddFragmentToNoteListFragment()
            )
        } else{
            Toast.makeText(context, R.string.empty_note_warning, Toast.LENGTH_SHORT).show()
        }

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

}