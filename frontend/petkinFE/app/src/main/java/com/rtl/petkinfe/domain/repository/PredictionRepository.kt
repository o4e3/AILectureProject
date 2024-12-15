package com.rtl.petkinfe.domain.repository

import com.rtl.petkinfe.domain.model.Prediction
import com.rtl.petkinfe.domain.model.PredictionWithImage
import java.io.File
import java.time.LocalDate

interface PredictionRepository {
    suspend fun requestPrediction(petId: Long, imageFile: File): Prediction
    fun getPredictionById(analysisId: Long): Prediction
    suspend fun savePhoto(imageFile: File): String
    suspend fun getPredictionsByDate(petId: Long, date: LocalDate): PredictionWithImage?
}