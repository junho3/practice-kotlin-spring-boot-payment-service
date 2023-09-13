package com.example.domain.transaction.model

data class SaveTransaction(
    override val transactionId: TransactionId,
    val payment: Payment
) : Transaction(transactionId)
