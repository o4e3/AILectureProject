package com.rtl.petkinfe.utils

import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File

fun createImagePart(imageFile: File): MultipartBody.Part {
    // 파일 확장자로부터 MIME 타입 결정
    val mimeType = when (imageFile.extension.lowercase()) {
        "jpg", "jpeg" -> "image/jpeg"
        "png" -> "image/png"
        "bmp" -> "image/bmp"
        "gif" -> "image/gif"
        else -> throw IllegalArgumentException("Unsupported file type: ${imageFile.extension}")
    }

    val requestBody = imageFile.asRequestBody(mimeType.toMediaTypeOrNull())
    return MultipartBody.Part.createFormData("image", imageFile.name, requestBody)
}
