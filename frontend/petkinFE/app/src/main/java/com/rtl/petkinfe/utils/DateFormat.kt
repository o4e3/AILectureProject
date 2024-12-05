package com.rtl.petkinfe.utils

import java.text.SimpleDateFormat
import java.util.Locale

fun formatDate(dateString: String): String {
    return try {
        val parser = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.getDefault()) // 입력 형식
        val formatter = SimpleDateFormat("yyyy년 MM월 dd일", Locale.getDefault()) // 출력 형식
        val date = parser.parse(dateString)
        formatter.format(date!!)
    } catch (e: Exception) {
        dateString // 포매팅 실패 시 원래 문자열 반환
    }
}