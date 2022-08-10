package com.h.haoyangmaov2

import android.content.Context
import android.os.Handler
import android.os.Looper
import android.os.Message
import android.text.TextUtils
import androidx.annotation.NonNull
import androidx.annotation.Nullable
import okhttp3.*
import okhttp3.Response
import okio.ByteString
import rxhttp.wrapper.ssl.HttpsUtils
import java.net.Proxy
import java.util.concurrent.TimeUnit

class WebSocketUtils {

    companion object {
        val instance: WebSocketUtils by lazy(mode = LazyThreadSafetyMode.SYNCHRONIZED) {
            WebSocketUtils()
        }
    }

    private val mSocketUrl = "wss://ws-rpc.avedexapp.com"
    private var mOkHttpClient: OkHttpClient? = null
    private var mWebSocket: WebSocket? = null
    private var mContext: Context? = null
    private val CLOSE_CODE = 1000 //关闭
    private val HEART = 1 //心跳
    private val RESTART_CONNECT = 2 //重连
    private val CONNECT_TIME_OUT: Long = 5 * 1000 //重连时间
    private val HEART_TIME: Long = 5 * 1000 //心跳时间
    private var isConnect = false //是否链接成功
    private lateinit var messageListener: SocketMessageListener

    private val SEND_TIME = 4 //发送超时时间
    private val SEND_TIME_OUT: Long = 15 * 1000 //请求超时---之后发送消息


    fun setMessageListener(messageListener: SocketMessageListener) {
        this.messageListener = messageListener
    }

    @Synchronized
    fun startRequest(context: Context?) {
        mContext = context
        isConnect = false
        if (mOkHttpClient == null) {
            val sslParams = HttpsUtils.getSslSocketFactory()
            mOkHttpClient = OkHttpClient().newBuilder()
                .sslSocketFactory(sslParams.sSLSocketFactory, sslParams.trustManager)
                .pingInterval(20, TimeUnit.SECONDS)
                .proxy(Proxy.NO_PROXY)
                .build()
        }
        if (mWebSocket == null) {
            val request: Request = Request.Builder().url(mSocketUrl).build()
            val listener = MyWebSocketListener()
            mWebSocket = mOkHttpClient!!.newWebSocket(request, listener)
        }
    }

    private inner class MyWebSocketListener : WebSocketListener() {
        override fun onOpen(webSocket: WebSocket,response: Response) {
            Logs.e("onOpen")
            isConnect = true
            //先清除订阅成功的数据

            SocketMessageUtils.instance.subscribeExp()
            mHandler.sendEmptyMessageDelayed(HEART, HEART_TIME)
            mHandler.removeMessages(RESTART_CONNECT)
        }

        override fun onClosed(webSocket: WebSocket, code: Int, reason: String) {
            Logs.e("onClosed")
            isConnect = false
//            mHandler.removeMessages(HEART)
            mHandler.removeMessages(RESTART_CONNECT)
        }

        override fun onMessage(webSocket: WebSocket, bytes: ByteString) {
            super.onMessage(webSocket, bytes)
            isConnect = true
            messageListener.setMessage(simpleAesDecrypt(bytes.utf8()))
        }

        override fun onMessage(webSocket: WebSocket, message: String) {
            isConnect = true
            if ((message.startsWith("[") && message.endsWith("]")) || (message.startsWith("{") && message.endsWith(
                    "}"
                ))
            ) {
                Logs.e("onMessage==${message}")
                messageListener.setMessage(message)
            }
        }

        override fun onFailure(webSocket: WebSocket, t: Throwable,   @Nullable response: okhttp3.Response?) {
            Logs.e("onFailure" + t.message)
            closeWebSocketReconnect()
            mHandler.sendEmptyMessageDelayed(
                RESTART_CONNECT,
                CONNECT_TIME_OUT
            )
        }

    }

    private val mHandler: Handler = object : Handler(Looper.getMainLooper()) {
        override fun handleMessage(@NonNull msg: Message) {
            when (msg.what) {
                RESTART_CONNECT -> startRequest(mContext)
                HEART -> {
                    sendEmptyMessageDelayed(HEART, HEART_TIME)
                }
            }
        }
    }


    /**
     * 发送消息
     *
     * @param message
     */

    fun sendSocketMessage(message: String) {
        if (TextUtils.isEmpty(message)) {
            return
        }
        Logs.e("sendSocketMessage=$message")
        if (NetStatusUtils.getNetType(mContext) != NetStatusUtils.NET_STATUS_NO_NET) {
            if (mWebSocket != null && isConnect) {
                mWebSocket!!.send(message)
            }
        } else {
            closeWebSocketReconnect()
        }
    }

    /**
     * 重连
     */
    private fun closeWebSocketReconnect() {
        isConnect = false
        closeWebSocket()
        mHandler.removeMessages(HEART)
    }

    /**
     * 关闭webSocket
     */
    fun closeWebSocket() {
        if (mWebSocket != null) {
            if (!mWebSocket!!.close(CLOSE_CODE, "")) {
                mWebSocket!!.close(CLOSE_CODE, "")
                mWebSocket = null
            }
        }
        closeWebSocketClient()
    }

    /**
     * 关闭OkHttpClient
     */
    fun closeWebSocketClient() {
        if (mOkHttpClient != null) {
            mOkHttpClient!!.dispatcher.executorService.shutdown()
            mOkHttpClient = null
        }
        mHandler.removeMessages(HEART)
        mHandler.removeMessages(RESTART_CONNECT)
    }


    /**
     * 获取webSocket
     * @return
     */
    val webSocket: WebSocket?
        get() = if (mWebSocket != null) {
            mWebSocket
        } else {
            null
        }

}

interface SocketMessageListener {
    fun setMessage(message: String?)
}
