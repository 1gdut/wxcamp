package com.example.wxcamping.model.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.wxcamping.model.entity.User

@Dao
interface UserDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(user: User)

    @Query("SELECT * FROM user")
    fun getAll(): List<User>

    @Query("SELECT * FROM user WHERE phoneNumber=:phoneNumber")
    fun getUserByPhoneNumber(phoneNumber: String): User

    @Query("SELECT * FROM user WHERE nickName=:nickName")
    fun getUserByNickName(nickName: String): User

    @Query("SELECT * FROM user WHERE phoneNumber=:phoneNumber AND nickName=:nickName")
    fun findUserRegister(phoneNumber: String, nickName: String): User?

    @Query("SELECT * FROM user WHERE phoneNumber=:phoneNumber AND password=:password")
    fun findUserLogin(phoneNumber: String, password: String): User?

    @Query("SELECT nickName FROM user WHERE phoneNumber=:phoneNumber")
    fun getUserNickName(phoneNumber: String): String
}