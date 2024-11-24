package com.example.wxcamping.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.room.Room

import com.example.wxcamping.MyApplication
import com.example.wxcamping.model.Model
import com.example.wxcamping.model.database.UserDatabase
import com.example.wxcamping.model.entity.User
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

class RegisterViewModel : ViewModel() {
    var userDatabase = UserDatabase.getDatabase(MyApplication.context)
    var nickName = MutableLiveData<String>()
    var phoneNumber = MutableLiveData<String>()
    var password = MutableLiveData<String>()
    var result = MutableLiveData<Boolean>()
    var model = Model.getInstance()
    fun register() {
        viewModelScope.launch {
            result.value = model.register(nickName.value, phoneNumber.value, password.value)
        }

    }


}
