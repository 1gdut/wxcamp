package com.example.wxcamping.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.wxcamping.MyApplication
import com.example.wxcamping.model.Model
import com.example.wxcamping.model.database.UserDatabase
import kotlinx.coroutines.launch

class LoginViewModel : ViewModel() {
    var userDatabase = UserDatabase.getDatabase(MyApplication.context)
    var phoneNumber = MutableLiveData<String>()
    var password = MutableLiveData<String>()
    var result = MutableLiveData<Boolean>()
    var model = Model.getInstance()
    fun login() {
        viewModelScope.launch {
            result.value = model.login(phoneNumber.value, password.value)
        }

    }
}