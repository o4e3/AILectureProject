package com.rtl.petkinfe.data.repository

import com.rtl.petkinfe.data.local.dao.PhotoDao
import com.rtl.petkinfe.data.local.entity.Photo
import com.rtl.petkinfe.domain.model.Prediction
import com.rtl.petkinfe.domain.model.PredictionDetail
import com.rtl.petkinfe.domain.repository.PredictionRepository
import java.io.File
import javax.inject.Inject

class PredictionRepositoryImpl @Inject constructor(
    private val photoDao: PhotoDao
): PredictionRepository {
    override fun requestPrediction(petId: Long, imageFile: File): Prediction {
        TODO("Not yet implemented")
    }

    override fun getPredictionById(analysisId: Long): PredictionDetail {
        TODO("Not yet implemented")
    }

    override suspend fun savePhoto(imageFile: File): String {
       val photo =  Photo(
            uri = imageFile.absolutePath,
            predictionId = null,
            recordId = null,
            probabilityA1 = null,
            probabilityA2 = null,
            probabilityA3 = null,
            probabilityA4 = null,
            probabilityA5 = null,
            probabilityA6 = null,
            probabilityA7 = null
        )

        photoDao.insertPhoto(photo)
        return imageFile.absolutePath // 저장된 파일 경로 반환
    }
}