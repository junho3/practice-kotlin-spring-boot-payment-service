package com.example.clients.feign.card.repository

import com.example.clients.feign.card.entity.CardEntity
import com.example.clients.feign.card.entity.CardStatus
import org.springframework.data.jpa.repository.JpaRepository

interface CardRepository : JpaRepository<CardEntity, String> {
    fun findByPartnerTransactionIdAndStatus(
        partnerTransactionId: String,
        status: CardStatus
    ): CardEntity?
}
