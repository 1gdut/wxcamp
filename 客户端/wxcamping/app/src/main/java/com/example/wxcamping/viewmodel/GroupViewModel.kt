package com.example.wxcamping.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.wxcamping.model.Model
import com.example.wxcamping.model.web.WebSocketClient

class GroupViewModel : ViewModel() {
    var text = MutableLiveData<String>()
    var result = MutableLiveData<Boolean>()
    var message = MutableLiveData<String>()
    val webSocketClient = WebSocketClient("ws://39.98.41.126:31132/chat", Model.userNickName!!)

    init {
        webSocketClient.onMessageReceived = { message ->
            this.message.value = message
        }
        webSocketClient.start()
    }
    suspend fun  sendMessage(message: String){
        webSocketClient.sendMessage(message)
    }

    override fun onCleared() {
        super.onCleared()
        webSocketClient.close()
    }
}