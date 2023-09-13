package com.example.domain.balance.model

import com.example.common.enums.TransactionType
import com.example.domain.transaction.model.TransactionId
import java.time.LocalDateTime

data class FindTransactionBalance(
    override val transactionId: TransactionId,
    override val transactionType: TransactionType,
    override val paymentTransactionAmount: Long,
    override val paymentVatAmount: Long,
    override val cancelTransactionAmount: Long = 0,
    override val cancelVatAmount: Long = 0,
    val paymentBalances: List<PaymentBalance>,
    val createdAt: LocalDateTime,
    val updatedAt: LocalDateTime
) : TransactionBalance(transactionId, transactionType, paymentTransactionAmount, paymentVatAmount, cancelTransactionAmount, cancelVatAmount)
