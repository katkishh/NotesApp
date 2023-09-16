package com.example.notesapp.domain

import android.content.ContentResolver
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import javax.inject.Inject

class GetContentUriUseCase @Inject constructor(
    private val contentResolver: ContentResolver
) {
    operator fun invoke(uri: Uri): Bitmap?{
        return contentResolver.openInputStream(uri).use{
            BitmapFactory.decodeStream(it)
        }
    }
}