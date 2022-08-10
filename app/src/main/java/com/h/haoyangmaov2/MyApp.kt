package com.h.haoyangmaov2

import android.app.Application
import com.hjq.toast.ToastUtils
import com.tencent.mmkv.MMKV

class MyApp:Application() {
    override fun onCreate() {
        super.onCreate()
        RxHttpManager.init(this)
        // 初始化 Toast 框架
        ToastUtils.init(this)
        //存储
        MMKV.initialize(this)
        SpUtils.getInstance()
    }
}