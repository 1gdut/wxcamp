package com.example.wxcamping.model.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user")
data class User(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val nickName: String,
    val phoneNumber: String,
    val password: String
) {
    constructor(nickName: String, phoneNumber: String, password: String) : this(
        0,
        nickName,
        phoneNumber,
        password
    )
}