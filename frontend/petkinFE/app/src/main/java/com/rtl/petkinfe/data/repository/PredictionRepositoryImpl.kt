package com.rtl.petkinfe.data.repository

import android.util.Log
import com.rtl.petkinfe.data.local.dao.PhotoDao
import com.rtl.petkinfe.data.local.entity.Photo
import com.rtl.petkinfe.data.mapper.toDomain
import com.rtl.petkinfe.data.remote.api.PredictionApi
import com.rtl.petkinfe.domain.model.Prediction
import com.rtl.petkinfe.domain.model.PredictionDetail
import com.rtl.petkinfe.domain.repository.PredictionRepository
import com.rtl.petkinfe.utils.createImagePart
import java.io.File
import javax.inject.Inject

class PredictionRepositoryImpl @Inject constructor(
    private val photoDao: PhotoDao,
    private val predictionApi: PredictionApi
): PredictionRepository {
    override suspend fun requestPrediction(petId: Long, imageFile: File): Prediction {
        // 1. 서버로 예측 요청
        val predictionResponseDto = predictionApi.requestDiseasePrediction(petId, createImagePart(imageFile))
        val prediction = predictionResponseDto.toDomain()

        // 2. 기존 Photo 엔티티를 조회 (이미 저장된 파일의 경로로 조회)
        val photo = photoDao.getPhotoByUri(imageFile.absolutePath)
            ?: throw IllegalStateException("Photo not found for uri: ${imageFile.absolutePath}")

        Log.d("testt", "Prediction: $prediction")


        // 3. Photo 엔티티 업데이트 - 필드를 명시적으로 전달
        photoDao.updatePhoto(
            uri = photo.uri,
            predictionId = prediction.analysisId,
            probA1 = prediction.A1,
            probA2 = prediction.A2,
            probA3 = prediction.A3,
            probA4 = prediction.A4,
            probA5 = prediction.A5,
            probA6 = prediction.A6,
            probA7 = prediction.A7
        )

        Log.d("testt", "Prediction update: $prediction")
        return prediction
    }

    override fun getPredictionById(analysisId: Long): PredictionDetail {
        TODO("Not yet implemented")
    }

    override suspend fun savePhoto(imageFile: File): String {
        Log.d("testt", "savePhoto1: $imageFile")
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
        Log.d("testt", "savePhoto2: $photo")

        photoDao.insertPhoto(photo)
        Log.d("testt", "savePhoto3: $photo")
        return imageFile.absolutePath // 저장된 파일 경로 반환
    }
}