package com.example.domain.transaction.port.result

import com.example.domain.card.model.CardTransactionData
import com.example.domain.transaction.model.PaymentId
import com.example.domain.transaction.model.TransactionId

data class PaymentResult(
    val transactionId: TransactionId,
    val paymentId: PaymentId,
    val cardTransactionData: CardTransactionData
)
