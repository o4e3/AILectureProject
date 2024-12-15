package com.rtl.petkinfe.domain.usecases

import android.util.Log
import com.rtl.petkinfe.domain.repository.PredictionRepository
import java.io.File
import javax.inject.Inject

class SavePhotoUseCase @Inject constructor(
   private val predictionRepository: PredictionRepository
) {
    suspend operator fun invoke(imageFile: File): String {
        return predictionRepository.savePhoto(imageFile)
    }
}