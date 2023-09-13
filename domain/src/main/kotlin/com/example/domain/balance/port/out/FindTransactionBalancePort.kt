package com.example.domain.balance.port.out

import com.example.domain.balance.model.FindTransactionBalance
import com.example.domain.transaction.model.PaymentId
import com.example.domain.transaction.model.TransactionId

interface FindTransactionBalancePort {
    fun findByTransactionId(transactionId: TransactionId): FindTransactionBalance

    fun findByTransactionIdAndPaymentId(transactionId: TransactionId, paymentId: PaymentId): FindTransactionBalance
}
