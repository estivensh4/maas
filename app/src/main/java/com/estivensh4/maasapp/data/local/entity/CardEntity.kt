package com.estivensh4.maasapp.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "card")
data class CardEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    @ColumnInfo(name = "card_number") var cardNumber: String,
    @ColumnInfo(name = "profile_code") var profileCode: String,
    @ColumnInfo(name = "profile") var profile: String,
    @ColumnInfo(name = "profile_es") var profileEs: String,
    @ColumnInfo(name = "bank_code") var bankCode: String,
    @ColumnInfo(name = "bank_name") var bankName: String,
    @ColumnInfo(name = "user_name") var userName: String,
    @ColumnInfo(name = "user_last_name") var userLastName: String
)
