package com.h.haoyangmaov2

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.hjq.toast.ToastUtils

/**
 * ws分送消息处理类
 */
class SocketMessageUtils {
    companion object {
        val instance: SocketMessageUtils by lazy(mode = LazyThreadSafetyMode.SYNCHRONIZED) {
            SocketMessageUtils()
        }

        const val controller_sub_exp = "Exp"

        //取消订阅
        const val action_unsubscribe = "unsubscribe"

        //消息订阅
        const val action_sub_contract_price = "subscribe"
    }

    private val idfa_exp = 0x0005


    /**
     * pair订阅成功的列表
     */
    private var pairAddressSub = ArrayList<String>()

    /**
     * contract订阅成功的列表
     */
    private var contractAddressSub = ArrayList<String>()


    /**
     * pair订阅成功插入数据
     */
    fun setPairAddressSub(item: String) {
        pairAddressSub.add(item)
    }

    var map = HashMap<String, ConnectWalletBean>()
    private fun loadWalletInfo() {
        val decodeMap = SpUtils.decodeString("map")
//        Logs.e("decodeMap=$decodeMap")
        if (!decodeMap.equals("") and decodeMap.isNotBlank() and decodeMap.isNotEmpty()) {
            map = Gson().fromJson(
                decodeMap,
                object : TypeToken<HashMap<String, ConnectWalletBean>>() {}.type
            )
        }
    }
    /**
     * 订阅经验值
     */
    fun subscribeExp() {
        if (map.isEmpty()) {
            loadWalletInfo()
        }
        map.forEach {
            it.key
            it.value
            val requestExp = SocketRequestBase<String>()
            requestExp.setController(controller_sub_exp)
            requestExp.setAction(action_sub_contract_price)
            requestExp.setParams(it.key)
            requestExp.setTotp(TOTP.generateMyTOTP())
            requestExp.setIdfa(idfa_exp)
            WebSocketUtils.instance.sendSocketMessage(Gson().toJson(requestExp).toString())
        }
    }

    /**
     * 取消订阅经验值
     */
    fun unSubscribeExp(map: HashMap<String, ConnectWalletBean>) {
        if (map.isEmpty()) {
            loadWalletInfo()
        }
        map.forEach {
            it.key
            it.value
            val requestExp = SocketRequestBase<String>()
            requestExp.setController(controller_sub_exp)
            requestExp.setAction(action_unsubscribe)
            requestExp.setParams(it.key)
            requestExp.setTotp(TOTP.generateMyTOTP())
            requestExp.setIdfa(idfa_exp)
            WebSocketUtils.instance.sendSocketMessage(Gson().toJson(requestExp).toString())
        }

    }

    /**
     * 清除所有订阅成功的数据
     */
    fun clearAllSubData() {
        pairAddressSub.clear()
        contractAddressSub.clear()
    }

}
