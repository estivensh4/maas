package com.estivensh4.maasapp.data.local.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.estivensh4.maasapp.data.local.entity.CardEntity

@Dao
interface CardDao {

    @Query("SELECT * FROM card")
    fun getAll(): List<CardEntity>

    @Query("SELECT * FROM card WHERE card_number=:cardNumber")
    fun getCard(cardNumber: String): CardEntity?

    @Upsert
    fun insertCard(card: CardEntity)

    @Query("DELETE FROM card WHERE card_number = :cardNumber")
    fun deleteCard(cardNumber: String)
}