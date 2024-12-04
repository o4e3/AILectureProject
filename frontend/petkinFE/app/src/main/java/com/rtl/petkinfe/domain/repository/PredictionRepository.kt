package com.rtl.petkinfe.domain.repository

import com.rtl.petkinfe.domain.model.Prediction
import com.rtl.petkinfe.domain.model.PredictionDetail
import java.io.File

interface PredictionRepository {
    fun requestPrediction(petId: Long, imageFile: File): Prediction
    fun getPredictionById(analysisId: Long): PredictionDetail
}