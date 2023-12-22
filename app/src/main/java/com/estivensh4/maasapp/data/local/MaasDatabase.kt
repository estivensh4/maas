package com.estivensh4.maasapp.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.estivensh4.maasapp.data.local.dao.UserDao
import com.estivensh4.maasapp.data.local.entity.UserEntity

@Database(entities = [UserEntity::class], version = 1)
abstract class MaasDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
}