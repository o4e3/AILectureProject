package com.rtl.petkinfe

import android.app.Application
import android.util.Log
import com.kakao.sdk.common.KakaoSdk

class MyApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        val kakaoAppKey = BuildConfig.KAKAO_API_KEY
        Log.d("KakaoSDK", "App Key: $kakaoAppKey")
        KakaoSdk.init(this, kakaoAppKey)
    }
}
