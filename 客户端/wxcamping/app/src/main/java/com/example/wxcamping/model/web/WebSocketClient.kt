package com.example.wxcamping.model.web

import android.os.Handler
import android.os.Looper
import android.util.Log
import android.widget.Toast
import com.example.wxcamping.MyApplication
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import okhttp3.WebSocket
import okhttp3.WebSocketListener

class WebSocketClient(
    private val url: String,
    private val username: String,
) {
    private val TAG = "WebSocketClient"
    private val client = OkHttpClient()
    private var webSocket: WebSocket? = null
    var onMessageReceived: (String) -> Unit = {}

    // 启动 WebSocket 连接
    fun start() {
        val request = Request.Builder().url("$url?username=$username").build()
        webSocket = client.newWebSocket(request, object : WebSocketListener() {
            override fun onOpen(webSocket: WebSocket, response: Response) {
                super.onOpen(webSocket, response)
                Log.d(TAG, "onOpen: 连接成功")

            }

            override fun onMessage(webSocket: WebSocket, text: String) {
                super.onMessage(webSocket, text)
                Log.d(TAG, "onMessage: 收到消息: $text")
                // 处理接收到的消息
                CoroutineScope(Dispatchers.Main).launch {
                    onMessageReceived(text)
                }
            }

            override fun onFailure(webSocket: WebSocket, t: Throwable, response: Response?) {
                super.onFailure(webSocket, t, response)
                Log.d(TAG, "onFailure: 连接失败: ${t.message}")
                // 处理失败情况
                Handler(Looper.getMainLooper()).post {
                    Toast.makeText(MyApplication.context, "服务器没开", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onClosed(webSocket: WebSocket, code: Int, reason: String) {
                super.onClosed(webSocket, code, reason)
                Log.d(TAG, "onClosed: 连接关闭: $reason")
                // 处理连接关闭
            }
        })
    }

    // 发送消息
    suspend fun sendMessage(message: String) {
        webSocket?.send(message)
    }

    // 关闭 WebSocket 连接
    fun close() {
        webSocket?.close(1000, "Client closing")
    }

}