package com.rtl.petkinfe.data.remote.api

import com.rtl.petkinfe.data.remote.dto.PredictionDetailResponseDto
import com.rtl.petkinfe.data.remote.dto.PredictionResponseDto
import okhttp3.MultipartBody
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Path
import retrofit2.http.Query
import java.io.File

interface PredictionApi {
    // 질병 분석 요청 (사진 업로드)
    @POST("api/ai/predict/{pet_id}")
    @Multipart
    suspend fun requestDiseasePrediction(
        @Path("pet_id") petId: Long,
        @Part image: MultipartBody.Part
    ): PredictionResponseDto

    // 특정 날짜와 펫 ID를 기준으로 분석 기록 조회
    @GET("api/ai/records/{pet_id}")
    suspend fun getRecordsByPetAndDate(
        @Path("pet_id") petId: Long,
        @Query("date") date: String
    ): List<PredictionDetailResponseDto>
}