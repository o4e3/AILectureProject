package com.rtl.petkinfe

import android.app.Application
import android.util.Log
import com.kakao.sdk.common.KakaoSdk
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class MyApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        val kakaoAppKey = BuildConfig.KAKAO_APP_KEY
        Log.d("KakaoSDK", "App Key: $kakaoAppKey")
        KakaoSdk.init(this, kakaoAppKey)
    }
}
