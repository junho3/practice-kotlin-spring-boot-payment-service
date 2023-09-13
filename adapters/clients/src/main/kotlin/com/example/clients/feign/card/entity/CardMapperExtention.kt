package com.example.clients.feign.card.entity

import com.example.domain.card.model.Card
import com.example.domain.card.model.CardTransactionData
import com.example.domain.card.model.CardTransactionId

fun CardEntity.toDomain(): Card = Card(
    cardTransactionId = CardTransactionId(this.transactionId),
    cardTransactionData = CardTransactionData(this.transactionData)
)

fun Card.toEntity(
    transactionId: CardTransactionId,
    status: CardStatus = CardStatus.COMPLETE
): CardEntity = CardEntity(
    transactionId = transactionId.value,
    transactionData = this.cardTransactionData.value,
    partnerTransactionId = this.cardTransactionData.paymentId,
    status = status
)
