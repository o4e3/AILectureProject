package com.rtl.petkinfe.data.local

import android.content.Context
import android.content.SharedPreferences
import javax.inject.Inject

class SharedPrefManager @Inject constructor(context: Context) {
    private val sharedPreferences: SharedPreferences =
        context.getSharedPreferences("PetkinPrefs", Context.MODE_PRIVATE)
    fun savePetId(petId: Long) {
        sharedPreferences.edit().putLong("PET_ID", petId).apply()
    }

    // Long 타입 petId 불러오기
    fun getPetId(): Long? {
        return if (sharedPreferences.contains("PET_ID")) {
            sharedPreferences.getLong("PET_ID", -1L).takeIf { it != -1L }
        } else {
            null
        }
    }
}