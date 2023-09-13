package com.example.domain.transaction.model

import com.example.common.enums.TransactionFailCode
import java.time.LocalDateTime

data class TransactionFailLog(
    val id: Long? = null,
    val transactionId: String,
    val paymentId: String,
    val code: TransactionFailCode,
    val reason: String?,
    val createdAt: LocalDateTime? = null
)
