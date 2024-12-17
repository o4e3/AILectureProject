package com.rtl.petkinfe.data.remote.api

import com.rtl.petkinfe.data.remote.dto.CreateHealthRecordRequest
import com.rtl.petkinfe.data.remote.dto.CreateHealthRecordResponse
import com.rtl.petkinfe.data.remote.dto.ErrorResponse
import com.rtl.petkinfe.data.remote.dto.HealthRecordByMonthResponse
import com.rtl.petkinfe.data.remote.dto.HealthRecordResponse
import com.rtl.petkinfe.data.remote.dto.UpdateHealthRecordRequest
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path
import retrofit2.http.Query

interface HealthRecordApi {

    // 특정 반려동물 건강 기록 목록 조회
    @GET("/api/pets/{pet_id}/health-records")
    suspend fun getHealthRecordsByPetId(
        @Path("pet_id") petId: Long
    ): List<HealthRecordResponse>

    @GET("/api/pets/{pet_id}/health-records/date")
    suspend fun getHealthRecordsByDate(
        @Path("pet_id") petId: Long,
        @Query("date") date: String // YYYY-MM-DD 형식의 날짜
    ): List<HealthRecordResponse>

    // 특정 달의 건강 기록 조회
    @GET("/api/pets/{pet_id}/health-records")
    suspend fun getHealthRecordsByMonth(
        @Path("pet_id") petId: Long,
        @Query("month") month: String // YYYY-MM 형식의 월
    ): List<HealthRecordByMonthResponse>

    // 특정 건강 기록 생성
    @POST("/api/health-records")
    suspend fun createHealthRecord(
        @Body request: CreateHealthRecordRequest
    ): CreateHealthRecordResponse

    // 특정 건강 기록 세부 정보 조회
    @GET("/api/health-records/{record_id}")
    suspend fun getHealthRecordById(
        @Path("record_id") recordId: Long
    ): HealthRecordResponse

    // 특정 건강 기록 수정
    @PUT("/api/health-records/{record_id}")
    suspend fun updateHealthRecord(
        @Path("record_id") recordId: Long,
        @Body request: UpdateHealthRecordRequest
    ): HealthRecordResponse

    // 특정 건강 기록 삭제
    @DELETE("/api/health-records/{record_id}")
    suspend fun deleteHealthRecord(
        @Path("record_id") recordId: Long
    ): ErrorResponse?
}