package com.rtl.petkinfe.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "photos")
data class Photo(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val uri: String,
    val predictionId: Long? = null, // 서버에서 받은 prediction_id
    val recordId: Long? = null, // 서버에서 받은 record_id
    val probabilityA1: Float? = null, // A1 확률값
    val probabilityA2: Float? = null, // A2 확률값
    val probabilityA3: Float? = null, // A3 확률값
    val probabilityA4: Float? = null, // A4 확률값
    val probabilityA5: Float? = null, // A5 확률값
    val probabilityA6: Float? = null, // A6 확률값
    val probabilityA7: Float? = null // A7 확률값
)
