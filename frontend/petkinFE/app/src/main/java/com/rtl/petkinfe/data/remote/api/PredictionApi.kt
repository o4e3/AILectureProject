package com.rtl.petkinfe.data.remote.api

import com.rtl.petkinfe.data.remote.dto.PredictionDetailResponseDto
import com.rtl.petkinfe.data.remote.dto.PredictionResponseDto
import okhttp3.MultipartBody
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Path

interface PredictionApi {
    // 질병 분석 요청 (사진 업로드)
    @POST("/api/pets/{pet_id}/prediction")
    @Multipart
    suspend fun requestDiseasePrediction(
        @Path("pet_id") petId: Long,
        @Part image: MultipartBody.Part
    ): PredictionResponseDto

    // 질병 분석 상세 조회
    @GET("/api/pets/prediction/results/{analysis_id}")
    suspend fun getPredictionDetails(
        @Path("analysis_id") analysisId: Long
    ): PredictionDetailResponseDto
}