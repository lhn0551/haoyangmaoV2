package com.h.haoyangmaov2

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.alibaba.fastjson.JSON
import rxhttp.toList
import rxhttp.wrapper.param.RxHttp
import rxhttp.wrapper.param.toResponse

class MainViewModel : ViewModel (){
    val addressLiveData = MutableLiveData<ConnectWalletBean>()
    fun inputAddress(address:String) {
        val map = HashMap<String, String>()
        map["user_addr"] = address
        map["totp"] = TOTP.generateMyTOTP()
        val jsonString = JSON.toJSONString(map)

        RxHttp.postForm("https://http-rpc.avedexapp.com/connect")
            .add("data", simpleAesEncrypt(jsonString))
            .asResponse(ConnectWalletBean::class.java)
            .subscribe({ s ->
                s.walletAddress=address
                addressLiveData.postValue(s)
            }, { e ->
            })
    }

    val getAirDropWithDrawLiveData = MutableLiveData<BlindBoxTakeBean>()

    /**
     * 盲盒领取
     */
    fun getLevelBox(activity_id: String,address:String) {
        val map = HashMap<String, String>()
        map["mem_address"] = address
        map["activity_id"] = activity_id
        map["lan"] = "Cn"
        map["totp"] = TOTP.generateMyTOTP()
        val jsonString = JSON.toJSONString(map)
        Logs.e("参数$jsonString")
        RxHttp.postForm("https://http-rpc.avedexapp.com/activity/blind_box")
            .add("data", simpleAesEncrypt(jsonString))
            .asResponse(BlindBoxTakeBean::class.java)
            .subscribe({ s ->
                s.walletAddress=address
                getAirDropWithDrawLiveData.postValue(s)
            }, { e ->
            })
    }



    val getBoxStatusLiveData = MutableLiveData<BoxStatusBean>()
    /**
     * 获取当前开启的活动
     */
    fun getBoxStatus(address:String) {
        val map = HashMap<String, String>()
        map["mem_address"] = address
        map["lan"] ="Cn"
        map["totp"] = TOTP.generateMyTOTP()
        val jsonString = JSON.toJSONString(map)
        RxHttp.postForm("https://http-rpc.avedexapp.com/activity/box_status")
            .add("data", simpleAesEncrypt(jsonString))
            .asResponse(BoxStatusBean::class.java)
            .subscribe({ s ->
                getBoxStatusLiveData.postValue(s)
            }, { e ->
            })
    }
}