package com.example.domain.transaction.port.command

import com.example.common.enums.TransactionFailCode
import com.example.domain.transaction.model.PaymentId
import com.example.domain.transaction.model.TransactionId

data class FailOverCommand(
    val transactionId: TransactionId,
    val paymentId: PaymentId,
    val code: TransactionFailCode,
    val reason: String?
)
