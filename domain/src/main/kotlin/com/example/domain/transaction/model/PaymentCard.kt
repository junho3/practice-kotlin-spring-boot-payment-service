package com.example.domain.transaction.model

import com.example.domain.card.model.CardInstallmentMonth
import com.example.domain.card.model.CardTransactionData
import com.example.domain.card.model.CardTransactionId
import com.example.domain.card.model.EncryptCardData

data class PaymentCard(
    val cardTransactionId: CardTransactionId,
    val cardTransactionData: CardTransactionData,
    val encryptCardData: EncryptCardData,
    val cardInstallmentMonth: CardInstallmentMonth
)
