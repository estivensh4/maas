package com.estivensh4.maasapp.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.estivensh4.maasapp.data.local.entity.UserEntity

@Dao
interface UserDao {

    @Query("SELECT * FROM user " +
            "WHERE document_type == :documentType " +
            "AND document_number == :documentNumber " +
            "AND password == :password"
    )
    fun findUser(documentType: String, documentNumber: String, password: String): UserEntity?

    @Insert
    fun insertUser(user: UserEntity)
}