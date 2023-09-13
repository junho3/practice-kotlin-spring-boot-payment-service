package com.example.domain.balance.model

import com.example.common.enums.TransactionType
import com.example.domain.transaction.model.TransactionId

abstract class TransactionBalance(
    open val transactionId: TransactionId,
    open val transactionType: TransactionType,
    open val paymentTransactionAmount: Long,
    open val paymentVatAmount: Long,
    open val cancelTransactionAmount: Long = 0,
    open val cancelVatAmount: Long = 0
)
