package com.example.domain.balance.model

import com.example.common.enums.TransactionType
import com.example.domain.transaction.model.TransactionId

data class SaveTransactionBalance(
    override val transactionId: TransactionId,
    override val transactionType: TransactionType,
    override val paymentTransactionAmount: Long,
    override val paymentVatAmount: Long,
    override val cancelTransactionAmount: Long = 0,
    override val cancelVatAmount: Long = 0,
    val paymentBalance: PaymentBalance
) : TransactionBalance(transactionId, transactionType, paymentTransactionAmount, paymentVatAmount, cancelTransactionAmount, cancelVatAmount)
