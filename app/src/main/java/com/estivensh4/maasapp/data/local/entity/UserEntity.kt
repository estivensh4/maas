package com.estivensh4.maasapp.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user")
data class UserEntity(
    @PrimaryKey val id: Int = 0,
    @ColumnInfo(name = "document_type") var documentType: String,
    @ColumnInfo(name = "document_number") var documentNumber: String,
    @ColumnInfo(name = "full_name") var fullName: String,
    @ColumnInfo(name = "password") var password: String,
    @ColumnInfo(name = "address") var address: String,
    @ColumnInfo(name = "email") var email: String,
)
