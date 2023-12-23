package com.estivensh4.maasapp.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.estivensh4.maasapp.data.local.dao.CardDao
import com.estivensh4.maasapp.data.local.dao.UserDao
import com.estivensh4.maasapp.data.local.entity.CardEntity
import com.estivensh4.maasapp.data.local.entity.UserEntity

@Database(entities = [UserEntity::class, CardEntity::class], version = 3)
abstract class MaasDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
    abstract fun cardDao(): CardDao
}