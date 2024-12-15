package com.rtl.petkinfe.utils

import android.content.Context
import android.net.Uri
import java.io.File
import java.io.InputStream


fun convertUriToFile(context: Context, uri: Uri): File {
    val contentResolver = context.contentResolver
    val inputStream: InputStream? = contentResolver.openInputStream(uri)
    val tempFile = File.createTempFile("selected_photo", ".jpg", context.cacheDir)
    tempFile.outputStream().use { outputStream ->
        inputStream?.copyTo(outputStream)
    }
    return tempFile
}