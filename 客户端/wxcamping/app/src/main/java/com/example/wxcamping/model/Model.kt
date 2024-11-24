package com.example.wxcamping.model

import android.widget.Toast
import com.example.wxcamping.MyApplication
import com.example.wxcamping.model.dao.UserDao
import com.example.wxcamping.model.database.UserDatabase
import com.example.wxcamping.model.entity.User


class Model {
    lateinit var userDatabase: UserDatabase
    lateinit var userDao: UserDao
    companion object {
        var userNickName: String? = null
            private set

        // 单例实例，确保Model类只被初始化一次
        private var instance: Model? = null

        fun getInstance(): Model {
            if (instance == null) {
                instance = Model()
            }
            return instance!!
        }
    }

    init {
        userDatabase = UserDatabase.getDatabase(MyApplication.context)
        userDao = userDatabase.userDao()
    }

    fun register(nickName: String?, phone: String?, password: String?): Boolean {
        if (nickName.isNullOrEmpty() || phone.isNullOrEmpty() || password.isNullOrEmpty()) {
            Toast.makeText(MyApplication.context, "请输入完整信息", Toast.LENGTH_SHORT).show()
            return false
        }
        var isExist: Boolean
        if (userDao.findUserRegister(phone, nickName) == null) {
            isExist = false
        } else {
            isExist = true
        }
        if (!isExist) {
            userDao.insert(User(nickName, phone, password))
            Toast.makeText(MyApplication.context, "注册成功", Toast.LENGTH_SHORT).show()
            return true
        }
        Toast.makeText(MyApplication.context, "注册失败，该用户已存在", Toast.LENGTH_SHORT).show()
        return false
    }

    fun login(phoneNumber: String?, password: String?): Boolean {
        if (phoneNumber.isNullOrEmpty() || password.isNullOrEmpty()) {
            Toast.makeText(MyApplication.context, "请输入完整信息", Toast.LENGTH_SHORT).show()
            return false
        }
        if (userDao.findUserLogin(phoneNumber, password) != null) {
            Toast.makeText(MyApplication.context, "登录成功", Toast.LENGTH_SHORT).show()
            userNickName = userDao.getUserNickName(phoneNumber)
            return true
        }
        Toast.makeText(MyApplication.context, "登录失败，请检查输入", Toast.LENGTH_SHORT).show()
        return false
    }
}